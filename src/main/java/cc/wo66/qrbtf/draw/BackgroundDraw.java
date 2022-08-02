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
import java.util.List;

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
            //parameters.setBackgroundColor(null);

        }
        //QRBtfUtil.write(image);

    }


    private void reserveAnchorArea(Graphics2D graphics, BufferedImage image, Parameters parameters) {
        if (!parameters.isAnchorArea()) {
            return;
        }
        graphics.setColor(Color.WHITE);
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
        List<BackgroundEnhance> bgEnhance = parameters.getBgEnhance();
        if (parameters.getBgEnhance() == null || parameters.getBgEnhance().isEmpty()) {
            return;
        }
        BufferedImage binaryImage = image;

        // 调整对比度与亮度，然后二值化输出
        if (bgEnhance.contains(BackgroundEnhance.BINARY)) {
            binaryImage = ImageFilter.create(image)
                    .filter(new ConBriFilter(parameters.getContrast(), parameters.getBrightness()))
                    .filter(new BinaryFilter())
                    .outGray(null);
        }
        // 像素风格 默认 33% 大小
        if (bgEnhance.contains(BackgroundEnhance.PIXEL)) {
            int[] avgW = average(); // 寻找等分点
            for (int y = 0; y < binaryImage.getHeight(); y+=multiple) {
                for (int x = 0; x < binaryImage.getWidth(); x+=multiple) {
                    for (int j = y, aj = 0; aj < 3; j+=avgW[aj++]) {
                        for (int i = x, ai = 0; ai < 3; i+=avgW[ai++]) {
                            int rgb = binaryImage.getRGB(i, j);
                            for (int k = j; k < j+avgW[aj] && k<y+multiple && j<binaryImage.getHeight(); k++) {
                                for (int l = i; l < i+avgW[ai] && l<x+multiple && i<binaryImage.getWidth(); l++) {
                                    image.setRGB(l,k,rgb);
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    private int[] average() {
        int w = multiple/3;
        switch (multiple%3) {
            case 1: return new int[]{w+1,w,w};
            case 2: return new int[]{w+1,w+1,w};
            default: return new int[]{w,w,w};
        }
    }
}
