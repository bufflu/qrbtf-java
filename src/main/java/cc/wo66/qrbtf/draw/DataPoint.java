package cc.wo66.qrbtf.draw;

import cc.wo66.qrbtf.Shape;
import com.google.zxing.qrcode.decoder.Version;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import lombok.Data;
import org.apache.commons.lang3.RandomUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

@Data
public class DataPoint {

    // 放大倍数
    private int multiple;
    // 定位点矩形边长(默认是 7)
    private final int anchorAreaSide = 7;

    // 校正图形中心坐标
    private int[][] alignmentCenters;

    private DataPoint(){
    }

    public static DataPoint create(Version version, int multiple) {
        DataPoint dataPoint = new DataPoint();
        dataPoint.setMultiple(multiple);

        // 校正图形的中心线
        int[] alignmentPatternCenters = version.getAlignmentPatternCenters();
        int apcLength = alignmentPatternCenters.length;
        if (apcLength == 0) {
            return dataPoint;
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
        dataPoint.setAlignmentCenters(alignmentCenters);

        return dataPoint;
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

    private boolean isDataPoint(int x, int y, int imageSide) {
        if (x >= anchorAreaSide && x < imageSide-anchorAreaSide && y >= 0 && y < anchorAreaSide) {
            return true;
        }
        if (x >= 0 && x < imageSide && y >= anchorAreaSide && y < imageSide-anchorAreaSide) {
            return true;
        }
        if (x >= anchorAreaSide && x < imageSide && y >= imageSide-anchorAreaSide && y < imageSide) {
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

    public void draw(ByteMatrix matrix, BufferedImage image, Shape shape, Color color, int scale, int opacity) {
        // 原始 QRCode 矩阵宽
        int inputSide = matrix.getWidth();
        // 缩放后的边距
        int scaleMargin = computeScaleMargin(scale);
        // 重新设置不透明度
        color = new Color(color.getRed(), color.getGreen(), color.getBlue(), 255*opacity/100);

        Graphics2D graphics = null;
        if (Shape.CIRCLE == shape || Shape.RANDOM == shape) {
            graphics = image.createGraphics();
        }

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
                        //Graphics2D graphics = image.createGraphics();
                        graphics.setColor(color);
                        graphics.fillOval(outputX+margin, outputY+margin, multiple-margin*2, multiple-margin*2);
                    }

                }
            }
        }

        if (graphics != null) {
            graphics.dispose();
        }
    }
}
