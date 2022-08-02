package cc.wo66.qrbtf.draw;

import cc.wo66.qrbtf.LineDirection;
import cc.wo66.qrbtf.Parameters;
import cc.wo66.qrbtf.QRBtfUtil;
import cc.wo66.qrbtf.Shape;
import com.google.zxing.qrcode.decoder.Version;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import lombok.Data;
import org.apache.commons.lang3.RandomUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

@Data
public class DataDraw {

    // 放大倍数
    private int multiple;
    // 定位点矩形边长(默认是 7)
    private static int anchorAreaSide = 7;

    // 校正图形中心坐标
    private int[][] alignmentCenters;

    private DataDraw(){
    }

    public static DataDraw create(Version version, int multiple) {
        DataDraw dataDraw = new DataDraw();
        dataDraw.setMultiple(multiple);

        // 校正图形的中心线
        int[] alignmentPatternCenters = version.getAlignmentPatternCenters();
        int apcLength = alignmentPatternCenters.length;
        if (apcLength == 0) {
            return dataDraw;
        }

        // 校正图形的中心点
        int[][] alignmentCenters = new int[apcLength*apcLength-3][2];
        int index = 0;
        for (int i = 0; i < apcLength; i++) {
            for (int j = 0; j < apcLength; j++) {
                if ((i == 0 && j == 0)
                    || (i == 0 && j == apcLength-1)
                    || (i == apcLength-1 && j == 0)) {
                    continue;
                }
                alignmentCenters[index++] = new int[]{alignmentPatternCenters[i],alignmentPatternCenters[j]};
            }
        }
        dataDraw.setAlignmentCenters(alignmentCenters);

        return dataDraw;
    }

    private boolean isAlignmentPoint(int x, int y) {
        for (int i = 0; i < alignmentCenters.length; i++) {
            if (x >= alignmentCenters[i][0]-2 && x <= alignmentCenters[i][0]+2
                && y >= alignmentCenters[i][1]-2 && y <= alignmentCenters[i][1]+2) {
                return true;
            }
        }
        return false;
    }

    public static boolean isDataPoint(int x, int y, int side) {
        if (x >= anchorAreaSide && x < side-anchorAreaSide && y >= 0 && y < anchorAreaSide) {
            return true;
        }
        if (x >= 0 && x < side && y >= anchorAreaSide && y < side-anchorAreaSide) {
            return true;
        }
        if (x >= anchorAreaSide && x < side && y >= side-anchorAreaSide && y < side) {
            return true;
        }
        return false;
    }

    private int computeScaleMargin(int scale) {
        int scaleMargin = 0;
        if (scale < 100) {
            scaleMargin = multiple*(100-scale)/200;
            if (scaleMargin >= (multiple-1)/2) {
                scaleMargin = (multiple-1)/2;
            }
        }
        return scaleMargin;
    }

    // 当倍数超过 20 增加美观线
    private int btfLine(int margin, boolean b) {
        return b && multiple > 20 && margin == 0 ? 1 : 0;
    }

    public void draw(ByteMatrix matrix, BufferedImage image, Parameters parameters) {
        Shape shape = parameters.getDataPointShape();
        Graphics2D graphics = null;

        if (Shape.LINE == shape) {
            graphics = image.createGraphics();
            Color color = parameters.getLineColor();
            int lineStroke = parameters.getLineStroke();
            int lineOpacity = parameters.getLineOpacity();
            graphics.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 255*lineOpacity/100));
            graphics.setStroke(
                    new BasicStroke((float)multiple*lineStroke/100, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)
            );

            drawLine(matrix, graphics, parameters.getLineDirection(), lineStroke);
        }

        if (Shape.CIRCLE == shape || Shape.RANDOM == shape || Shape.RECTANGLE == shape) {
            Color darkColor = parameters.getDataPointColor();
            Color lightColor = parameters.getDataPointColor2();
            int opacity = parameters.getDataPointOpacity();
            int scale = parameters.getDataPointScale();
            if (darkColor != null) {
                darkColor = new Color(darkColor.getRed(), darkColor.getGreen(), darkColor.getBlue(), 255 * opacity / 100);
            }
            if (lightColor != null) {
                lightColor = new Color(lightColor.getRed(), lightColor.getGreen(), lightColor.getBlue(), 255*opacity/100);
            }

            if (Shape.RECTANGLE != shape) {
                graphics = image.createGraphics();
                graphics.setColor(darkColor);
            }

            // 无干扰函数
            if (parameters.getFunc() == null) {
                boolean bLine = parameters.isBeautifulLine();
                drawNotLine(matrix, image, graphics, shape, darkColor, lightColor, scale, bLine);

            } else { // 有干扰函数
                Color funcColor = parameters.getFuncColor();
                boolean func = parameters.getFunc();
                drawFunc(matrix, image, graphics, func, shape, darkColor, funcColor);
            }
        }

        if (graphics != null) {
            graphics.dispose();
        }
    }


    private void drawNotLine(ByteMatrix matrix, BufferedImage image, Graphics2D graphics,
                             Shape shape, Color darkColor, Color lightColor, int scale, boolean bLine) {

        // 原始 QRCode 矩阵宽
        int inputSide = matrix.getWidth();
        // 缩放后的边距
        int scaleMargin = computeScaleMargin(scale);
        int rgb;

        for (int inputY = 0, outputY = 0; inputY < inputSide; inputY++, outputY += multiple) {
            for (int inputX = 0, outputX = 0; inputX < inputSide; inputX++, outputX += multiple) {
                if (isDataPoint(inputX, inputY, inputSide)) {

                    if (matrix.get(inputX, inputY) == 1) {
                        if (darkColor == null) {
                            continue;
                        }
                        rgb = darkColor.getRGB();
                        if (lightColor != null && graphics != null) {
                            graphics.setColor(darkColor);
                        }
                    } else {
                        if (lightColor == null) {
                            continue;
                        }
                        rgb = lightColor.getRGB();
                        if (graphics != null) {
                            graphics.setColor(lightColor);
                        }
                    }

                    int margin = scaleMargin;
                    // 校正点只进行缩放，不进行随机
                    if (Shape.RANDOM == shape && !isAlignmentPoint(inputX, inputY)) {
                        int randomScale = RandomUtils.nextInt(2, 10)*10;
                        margin = computeScaleMargin(randomScale);
                    }

                    if (Shape.RECTANGLE == shape) {
                        for (int y = outputY+margin; y < outputY+multiple-margin-btfLine(margin, bLine); y++) {
                            for (int x = outputX+margin; x < outputX+multiple-margin-btfLine(margin, bLine); x++) {
                                image.setRGB(x, y, rgb);
                            }
                        }

                    } else if (Shape.CIRCLE == shape || Shape.RANDOM == shape) {
                        graphics.fillOval(outputX+margin, outputY+margin, multiple-margin*2, multiple-margin*2);
                    }

                }
            }
        }
    }


    private void drawLine(ByteMatrix matrix, Graphics2D graphics, LineDirection lineDirection, int lineStroke) {
        LineDraw.create(multiple).draw(matrix, graphics, lineDirection, lineStroke);
    }

    private void drawFunc(ByteMatrix matrix, BufferedImage image, Graphics2D graphics,
                          boolean func, Shape shape, Color color, Color funcColor) {
        if (func) { // B 函数
            drawNotLine(matrix, image, graphics, shape, color, null, 40, false);

            java.util.List<int[]> funcPoint = FuncPoint.getFuncPoint(matrix.getWidth());
            if (funcPoint == null) {
                return;
            }
            int startX, y, l;
            for (int[] ints : funcPoint) {
                startX = ints[0];
                y = ints[1];
                l = ints[2];

                if (Shape.RECTANGLE == shape) {
                    for (int x = startX; x < startX + l; x++) {
                        if (matrix.get(x, y) == 1) {
                            for (int outputY = y * multiple; outputY < (y + 1) * multiple; outputY++) {
                                for (int outputX = x * multiple; outputX < (x + 1) * multiple; outputX++) {
                                    image.setRGB(outputX, outputY, funcColor.getRGB());
                                }
                            }
                        } else {
                            int margin = multiple / 10;
                            for (int outputY = y * multiple; outputY < (y + 1) * multiple; outputY++) {
                                for (int outputX = x * multiple; outputX < (x + 1) * multiple; outputX++) {
                                    if (outputY >= y * multiple + margin && outputY < (y + 1) * multiple - margin
                                            && outputX >= x * multiple + margin && outputX < (x + 1) * multiple - margin) {
                                        continue;
                                    }
                                    image.setRGB(outputX, outputY, funcColor.getRGB());
                                }
                            }
                        }
                    }
                }
                if (Shape.CIRCLE == shape) {
                    graphics.setColor(funcColor);
                    for (int x = startX; x < startX + l; x++) {
                        if (matrix.get(x, y) == 1) {
                            graphics.fillOval(x * multiple, y * multiple, multiple, multiple);
                        } else {
                            int margin = multiple / 13;
                            if (margin > 1) {
                                graphics.setStroke(new BasicStroke(margin));
                            } else {
                                margin = 0;
                            }
                            graphics.drawOval(x * multiple + margin, y * multiple + margin, multiple - margin, multiple - margin);
                        }
                    }
                }
            }

        } else { // A 函数
            int inputSide = matrix.getWidth();
            float m = 0.3f/inputSide; // QrCode 中1个单位缩小 30%
            int centerX = inputSide/2;
            int centerY = inputSide/2;
            int margin = 0;

            for (int inputY = 0, outputY = 0; inputY < inputSide; inputY++, outputY += multiple) {
                for (int inputX = 0, outputX = 0; inputX < inputSide; inputX++, outputX += multiple) {
                    if (matrix.get(inputX, inputY) == 1 && isDataPoint(inputX, inputY, inputSide)) {
                        // 靠近中心的点更小
                        margin = (int)(multiple * m * (inputSide - Math.abs(inputX-centerX) - Math.abs(inputY-centerY)));
                        if (Shape.RECTANGLE == shape) {
                            for (int y = outputY+margin; y < outputY+multiple-margin; y++) {
                                for (int x = outputX+margin; x < outputX+multiple-margin; x++) {
                                    image.setRGB(x, y, color.getRGB());
                                }
                            }

                        }
                        if (Shape.CIRCLE == shape) {
                            graphics.fillOval(outputX+margin, outputY+margin, multiple-margin*2, multiple-margin*2);
                        }

                    }
                }
            }
        }
    }


}
