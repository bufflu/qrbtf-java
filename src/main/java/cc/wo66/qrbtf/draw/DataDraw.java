package cc.wo66.qrbtf.draw;

import cc.wo66.qrbtf.LineDirection;
import cc.wo66.qrbtf.Parameters;
import cc.wo66.qrbtf.Shape;
import com.google.zxing.qrcode.decoder.Version;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import lombok.Data;
import org.apache.commons.lang3.RandomUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

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
    private int btfLine(int margin) {
        return multiple > 20 && margin == 0 ? 1 : 0;
    }

    public void draw(ByteMatrix matrix, BufferedImage image, Parameters parameters) {
        Shape shape = parameters.getDataPointShape();
        Graphics2D graphics = null;
        Color color = null;

        if (Shape.LINE == shape) {
            graphics = image.createGraphics();
            color = parameters.getLineColor();
            int lineStroke = parameters.getLineStroke();
            int lineOpacity = parameters.getLineOpacity();
            graphics.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 255*lineOpacity/100));
            graphics.setStroke(new BasicStroke((float)multiple*lineStroke/100, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            drawLine(matrix, graphics, parameters.getLineDirection());
        }

        if (Shape.CIRCLE == shape || Shape.RANDOM == shape || Shape.RECTANGLE == shape) {
            color = parameters.getDataPointColor();
            int opacity = parameters.getDataPointOpacity();
            int scale = parameters.getDataPointScale();
            color = new Color(color.getRed(), color.getGreen(), color.getBlue(), 255*opacity/100);
            if (Shape.RECTANGLE != shape) {
                graphics = image.createGraphics();
                graphics.setColor(color);
            }

            drawNotLine(matrix, image, graphics, shape, color, scale);
        }

        if (graphics != null) {
            graphics.dispose();
        }
    }


    public void drawNotLine(ByteMatrix matrix, BufferedImage image, Graphics2D graphics, Shape shape, Color color, int scale) {

        // 原始 QRCode 矩阵宽
        int inputSide = matrix.getWidth();
        // 缩放后的边距
        int scaleMargin = computeScaleMargin(scale);

        for (int inputY = 0, outputY = 0; inputY < inputSide; inputY++, outputY += multiple) {
            for (int inputX = 0, outputX = 0; inputX < inputSide; inputX++, outputX += multiple) {
                if (matrix.get(inputX, inputY) == 1 && isDataPoint(inputX, inputY, inputSide)) {

                    int margin = scaleMargin;
                    // 校正点只进行缩放，不进行随机
                    if (Shape.RANDOM == shape && !isAlignmentPoint(inputX, inputY)) {
                        int randomScale = RandomUtils.nextInt(2, 10)*10;
                        margin = computeScaleMargin(randomScale);
                    }

                    if (Shape.RECTANGLE == shape) {
                        for (int y = outputY+margin; y < outputY+multiple-margin-btfLine(margin); y++) {
                            for (int x = outputX+margin; x < outputX+multiple-margin-btfLine(margin); x++) {
                                image.setRGB(x, y, color.getRGB());
                            }
                        }

                    } else if (Shape.CIRCLE == shape || Shape.RANDOM == shape) {
                        graphics.fillOval(outputX+margin, outputY+margin, multiple-margin*2, multiple-margin*2);
                    }

                }
            }
        }
    }



    private void drawLine(ByteMatrix matrix, Graphics2D graphics, LineDirection lineDirection) {
        LineDraw.create(multiple).draw(matrix, graphics, lineDirection);
    }
}
