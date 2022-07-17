package cc.wo66.qrbtf.draw;

import cc.wo66.qrbtf.Parameters;
import cc.wo66.qrbtf.QRBtfUtil;
import cc.wo66.qrbtf.Shape;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import lombok.Data;

import java.awt.*;
import java.awt.image.BufferedImage;

@Data
public class AnchorDraw {

    // 起始点(左上)坐标
    private int[] start;
    // 结束点(右下)坐标
    private int[] end;

    // 倍数
    private int multiple;
    // 定位点矩形边长(默认是 7)
    private final int anchorAreaSide = 7;


    private AnchorDraw(int multiple){
        this.multiple = multiple;
        start = new int[]{0,0};
        end = new int[]{anchorAreaSide*multiple-1, anchorAreaSide*multiple-1};
    }

    public static AnchorDraw create(int multiple) {
        return new AnchorDraw(multiple);
    }

    // 当倍数超过 20 增加美观线
    private int btfLine() {
        return multiple > 20 ? 1 : 0;
    }

    private ByteMatrix drawShape(ByteMatrix matrix, Shape shape, int scale) {
        int inputSide = anchorAreaSide;
        int outputSide = anchorAreaSide*multiple;
        ByteMatrix outMatrix = null;

        if (Shape.RECTANGLE == shape) {
            outMatrix = new ByteMatrix(outputSide, outputSide);
            for (int inputY = 0, outputY = 0; inputY < inputSide; inputY++, outputY += multiple) {
                for (int inputX = 0, outputX = 0; inputX < inputSide; inputX++, outputX += multiple) {
                    if (matrix.get(inputX, inputY) == 1) {
                        for (int y = outputY; y < outputY+multiple-btfLine(); y++) {
                            for (int x = outputX; x < outputX+multiple-btfLine(); x++) {
                                outMatrix.set(x, y, 1);
                            }
                        }
                    }
                }
            }
        }

        if (Shape.CIRCLE == shape) {
            // 2022-07-12 这里先用 Graphics2D 绘制后转为 ByteMatrix，后面再优化
            //            Graphics2D 绘制的圆会向右向下偏移 1 像素，multiple 的值太小时绘制的圆有误
            BufferedImage image = new BufferedImage(outputSide, outputSide, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics2D = image.createGraphics();

            // side 默认为 7, 所以中心圆直径为 3, 完成圆环的宽度为 1, 参照二维码定位点描述
            // Graphics2D 绘图以左上角坐标为起点
            // 外层圆环
            graphics2D.setColor(Color.BLACK);
            graphics2D.fillOval(getStart()[0], getStart()[1], outputSide, outputSide);
            graphics2D.setColor(Color.WHITE);
            graphics2D.fillOval(getStart()[0]+multiple, getStart()[1]+multiple, multiple*5, multiple*5);
            // 中心圆
            graphics2D.setColor(Color.BLACK);
            graphics2D.fillOval(getStart()[0]+multiple*2, getStart()[1]+multiple*2, multiple*3, multiple*3);
            graphics2D.dispose();

            outMatrix = QRBtfUtil.image2ByteMatrix(image);
        }

        if (Shape.PLANET == shape) {
            BufferedImage image = new BufferedImage(outputSide, outputSide, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics2D = image.createGraphics();
            // 中心圆
            graphics2D.setColor(Color.BLACK);
            graphics2D.fillOval(multiple*2, multiple*2, multiple*3, multiple*3);
            // 环绕圆
            graphics2D.fillOval(multiple*3, 0, multiple, multiple);
            graphics2D.fillOval(0, multiple*3, multiple, multiple);
            graphics2D.fillOval(multiple*6, multiple*3, multiple, multiple);
            graphics2D.fillOval(multiple*3, multiple*6, multiple, multiple);
            // 环绕线
            graphics2D.setStroke(new BasicStroke((float)multiple/6, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            for (int a = 10; a < 360; a+=20) { // 共绘制 18 条黑色线段
                graphics2D.drawArc(multiple/2, multiple/2, multiple*6, multiple*6, a, 8);
            }
            graphics2D.dispose();

            outMatrix = QRBtfUtil.image2ByteMatrix(image);
        }

        if (Shape.ROUNDED_RECTANGLE == shape) {
            BufferedImage image = new BufferedImage(outputSide, outputSide, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics2D = image.createGraphics();
            // 圆角矩形环
            graphics2D.setColor(Color.BLACK);
            graphics2D.fillRoundRect(0,0, outputSide, outputSide,
                    outputSide*52/100, outputSide*52/100);
            graphics2D.setColor(Color.WHITE);
            int thickness = multiple/4 + multiple*3*scale/(400); // 环厚度 其中 1/4 固定值， 3/4 跟随数据点的缩放值调整
            graphics2D.fillRoundRect(thickness, thickness, outputSide-thickness*2, outputSide-thickness*2,
                    (outputSide-thickness*2)*48/100, (outputSide-thickness*2)*48/100);
            // 中心圆
            graphics2D.setColor(Color.BLACK);
            graphics2D.fillOval(multiple*2, multiple*2, multiple*3, multiple*3);

            outMatrix = QRBtfUtil.image2ByteMatrix(image);
        }

        return outMatrix;
    }

    private int[] drawColor(ByteMatrix matrix, Color color, Color bgColor) {
        int[] rgbArray = new int[matrix.getHeight()*matrix.getWidth()];
        int index = 0;
        for (int y = 0; y < matrix.getHeight(); y++) {
            for (int x = 0; x < matrix.getWidth(); x++) {
                if (matrix.get(x,y) == 1) {
                    rgbArray[index] = color.getRGB();
                } else if (bgColor != null) {
                    rgbArray[index] = bgColor.getRGB();
                }
                index++;
            }
        }
        return rgbArray;
    }

    public void draw(ByteMatrix matrix, BufferedImage image, Parameters parameters) {
        ByteMatrix byteMatrix = drawShape(matrix, parameters.getAnchorPointShape(), parameters.getDataPointScale());
        int[] rgbArray = drawColor(byteMatrix, parameters.getAnchorPointColor(), parameters.getBackgroundColor());
        // 分别绘制左上 - 右上 - 左下
        image.setRGB(0, 0,
                byteMatrix.getWidth(), byteMatrix.getHeight(), rgbArray, 0, byteMatrix.getWidth());
        image.setRGB(image.getWidth()-byteMatrix.getWidth(), 0,
                byteMatrix.getWidth(), byteMatrix.getHeight(), rgbArray, 0, byteMatrix.getWidth());
        image.setRGB(0, image.getWidth()-byteMatrix.getWidth(),
                byteMatrix.getWidth(), byteMatrix.getHeight(), rgbArray, 0, byteMatrix.getWidth());
    }

}
