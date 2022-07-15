package cc.wo66.qrbtf.draw;

import cc.wo66.qrbtf.QRBtfUtil;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * ClassName: IconPoint
 *
 * @author guoxinlu
 * @since 2022-07-12 22:04
 */
@Data
public class IconDraw {

    // 放大倍数
    private int multiple;
    // icon 边长(通过原始QrCode边长算出)
    private int iconAreaSide;

    // 圆角矩形边长与圆角的比例 100:52
    private final int outSideDiameterRatio = 52;
    private final int intSideDiameterRatio = 48;
    // 圆角矩形边长与圆角矩形环宽度比例
    private final float sideThicknessRatio = 5.5f;


    private IconDraw(){}

    public static IconDraw create(ErrorCorrectionLevel level, int side, int multiple) {
        double correct = 0;
        switch (level) {
            case M: correct = 15; break;
            case Q: correct = 25; break;
            case H: correct = 30; break;
            case L:
            default: correct = 7;
        }
        int iconAreaSide = (int)Math.sqrt(side*side*correct/100);
        iconAreaSide = iconAreaSide%2==0 ? iconAreaSide-1 : iconAreaSide;
        int o = (side - iconAreaSide) / 2;

        IconDraw iconDraw = new IconDraw();
        iconDraw.setMultiple(multiple);
        iconDraw.setIconAreaSide(iconAreaSide);
        return iconDraw;
    }


    private int computeScaleSide(int scale) {
        if (scale > 100) {
            scale = 100;
        }
        int s = iconAreaSide*scale/100;
        return s == 0 ? multiple : s*multiple;
    }

    public void draw(BufferedImage image, String iconBase64, int scale) {
        if (StringUtils.isBlank(iconBase64) || scale < 5) {
            return;
        }
        // 加载图片
        BufferedImage iconImage = QRBtfUtil.createEquilateralImage(iconBase64);
        if (iconImage == null) {
            return;
        }
        // icon 区域大小
        int iconAreaSide = computeScaleSide(scale);



        // 缩放icon
        Image scaleImage = iconImage.getScaledInstance(iconAreaSide, iconAreaSide, Image.SCALE_SMOOTH);
        iconImage = new BufferedImage(iconAreaSide, iconAreaSide, iconImage.getType());
        Graphics2D graphics = iconImage.createGraphics();
        graphics.drawImage(scaleImage, 0, 0, null);
        graphics.dispose();

        // 圆角矩形环(加个白边)  2022-07-14 边缘过度不够顺滑，待优化
        BufferedImage roundRectImage = new BufferedImage(iconAreaSide, iconAreaSide, BufferedImage.TYPE_INT_ARGB);
        Graphics2D rriGraphics = roundRectImage.createGraphics();
        rriGraphics.setColor(Color.WHITE);
        rriGraphics.fillRoundRect(0,0, iconAreaSide, iconAreaSide,
                iconAreaSide*outSideDiameterRatio/100, iconAreaSide*outSideDiameterRatio/100);
        rriGraphics.setColor(Color.BLACK);
        int thickness = (int)(iconAreaSide*sideThicknessRatio/100); // 环厚度
        rriGraphics.fillRoundRect(thickness, thickness, iconAreaSide-thickness*2, iconAreaSide-thickness*2,
                (iconAreaSide-thickness*2)*intSideDiameterRatio/100, (iconAreaSide-thickness*2)*intSideDiameterRatio/100);
        rriGraphics.dispose();

        // 绘制 icon (也可以使用 Graphics2D.drawImage)
        int startX = (image.getWidth()-iconAreaSide)/2;
        for (int y = 0; y < roundRectImage.getHeight(); y++) {
            for (int x = 0; x < roundRectImage.getWidth(); x++) {
                if (roundRectImage.getRGB(x, y) == Color.WHITE.getRGB()) {
                    image.setRGB(startX+x, startX +y, Color.WHITE.getRGB());
                }
                if (roundRectImage.getRGB(x, y) == Color.BLACK.getRGB()) {
                    image.setRGB(startX+x, startX +y, iconImage.getRGB(x,y));
                }
            }
        }

    }


}
