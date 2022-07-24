package cc.wo66.qrbtf.draw;

import cc.wo66.qrbtf.BackgroundEnhance;
import cc.wo66.qrbtf.Parameters;
import cc.wo66.qrbtf.QRBtfUtil;
import cc.wo66.qrbtf.Shape;
import cc.wo66.qrbtf.filter.BinaryFilter;
import cc.wo66.qrbtf.filter.ConBriFilter;
import cc.wo66.qrbtf.filter.ImageFilter;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BackgroundDraw {
    
    private int multiple;
    private final int anchorAreaBackGroundSide = 8;

    private BackgroundDraw(int multiple){
        this.multiple = multiple;
    }

    public static BackgroundDraw create(int multiple) {
        return new BackgroundDraw(multiple);
    }

    public void draw(BufferedImage image, Parameters parameters){
        // 1 填充背景图
        if (StringUtils.isNotBlank(parameters.getBackgroundImageBase64())) {
            BufferedImage backgroundImage = QRBtfUtil.createEquilateralImage(parameters.getBackgroundImageBase64());
            if (backgroundImage == null) {
                return;
            }
            // 缩放
            Image scaleImage = backgroundImage.getScaledInstance(image.getWidth(), image.getHeight(), Image.SCALE_FAST);
            Graphics2D graphics = image.createGraphics();
            graphics.drawImage(scaleImage, 0, 0, null);

            // 特殊处理
            pixelEnhance(image, parameters);

            // 预留定位区域
            reserveAnchorArea(graphics, image, parameters);
            graphics.dispose();

            // 优先使用背景图
            parameters.setBackgroundColor(null);

        }

        // 2 填充背景色
        if (parameters.getBackgroundColor() != null) {
            QRBtfUtil.fillBackGroundColor(image, parameters.getBackgroundColor());
        }
    }


    private void reserveAnchorArea(Graphics2D graphics, BufferedImage image, Parameters parameters) {
        graphics.setColor(parameters.getBackgroundColor());
        if (Shape.RECTANGLE == parameters.getAnchorPointShape()) {
            int side = anchorAreaBackGroundSide*multiple;
            graphics.fillRect(0, 0, side, side);
            graphics.fillRect(image.getWidth()-side, 0, side, side);
            graphics.fillRect(0, image.getWidth()-side, side, side);
        }
        if (Shape.CIRCLE == parameters.getAnchorPointShape()
                || Shape.PLANET == parameters.getAnchorPointShape()) {
            int offset = multiple/6+multiple;
            int side = 7*multiple+offset*2;
            graphics.fillOval(-offset, -offset, side, side);
            graphics.fillOval(image.getWidth()-side+offset, -offset, side, side);
            graphics.fillOval(-offset, image.getWidth()-side+offset, side, side);
            // 填充3个角
            graphics.fillRect(0,0, multiple, multiple);
            graphics.fillRect(image.getWidth()-multiple,0, multiple, multiple);
            graphics.fillRect(0,image.getWidth()-multiple, multiple, multiple);
        }
    }

    // 增强处理
    private void pixelEnhance(BufferedImage image, Parameters parameters) {
        if (parameters.getBgEnhance() == null) {
            return;
        }
        // 调整对比度与亮度，然后二值化输出  todo
        if (BackgroundEnhance.BINARIZATION == parameters.getBgEnhance()) {
            ImageFilter.create(image)
                    .filter(new ConBriFilter(parameters.getContrast(), parameters.getBrightness()))
                    .filter(new BinaryFilter())
                    .outGray(image);
        }

    }
}
