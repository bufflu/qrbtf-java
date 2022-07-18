package cc.wo66.qrbtf.draw;

import cc.wo66.qrbtf.Parameters;
import cc.wo66.qrbtf.QRBtfUtil;
import cc.wo66.qrbtf.Shape;
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
        // 填充背景
        if (StringUtils.isNotBlank(parameters.getBackgroundImageBase64())) {
            BufferedImage backgroundImage = QRBtfUtil.createEquilateralImage(parameters.getBackgroundImageBase64());
            if (backgroundImage == null) {
                return;
            }
            // 缩放
            Image scaleImage = backgroundImage.getScaledInstance(image.getWidth(), image.getHeight(), Image.SCALE_SMOOTH);
            Graphics2D graphics = image.createGraphics();
            graphics.drawImage(scaleImage, 0, 0, null);
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
            graphics.dispose();

            // 优先使用背景图
            parameters.setBackgroundColor(null);

        }
        // 填充背景色
        if (parameters.getBackgroundColor() != null) {
            QRBtfUtil.fillBackGroundColor(image, parameters.getBackgroundColor());
        }
    }
}
