package cc.wo66.qrbtf;

import com.google.zxing.qrcode.encoder.ByteMatrix;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

/**
 * ClassName: QRBtfUtil
 * 工具类
 *
 * @author guoxinlu
 * @since 2022-07-10 22:00
 */
public class QRBtfUtil {

    /**
     * 找到 BufferedImage 中 Color.Black 的坐标点，然后将 ByteMatrix 的坐标点值设置为 1
     * @param image BufferedImage
     * @return ByteMatrix
     */
    public static ByteMatrix image2ByteMatrix(BufferedImage image) {
        ByteMatrix matrix = new ByteMatrix(image.getWidth(), image.getHeight());
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                if (image.getRGB(x, y) == Color.BLACK.getRGB()) {
                    matrix.set(x, y, 1);
                }
            }
        }
        return matrix;
    }

    /**
     * 根据 base64 生成图片，并且调整为等边的
     * @param base64String imageBase64
     * @return BufferedImage
     */
    public static BufferedImage createEquilateralImage(String base64String) {
        try {
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(Base64.getDecoder().decode(base64String)));
            if (image.getWidth() == image.getHeight()) {
                return image;
            }

            int side = Math.min(image.getWidth(), image.getHeight());
            int x = 0,y = 0;
            int s = image.getWidth() - image.getHeight();
            if (s > 0) {
                x = s/2;
            } else {
                y = -s/2;
            }
            BufferedImage eImage = new BufferedImage(side, side, image.getType());
            eImage.setData(image.getData(new Rectangle(x, y, side, side)));
            eImage.createGraphics().draw(new RoundRectangle2D.Double(0, 0, side, side, (double)side/10*2, (double)side/10*2));
            return eImage;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 填充背景色
     * @param image BufferedImage
     * @param backgroundColor 背景颜色
     */
    public static void fillBackGroundColor(BufferedImage image, Color backgroundColor){
        if (backgroundColor == null) {
            return;
        }
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(backgroundColor);
        graphics.fillRect(0,0,image.getWidth(), image.getHeight());
        graphics.dispose();
    }








    public static void print(ByteMatrix bm){
        try {
            int width = bm.getWidth();
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < width ; j++) {
                    System.out.print(bm.get(i,j) == 1? "*":" ");
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void print(BufferedImage bm){
        try {
            int width = bm.getWidth();
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < width ; j++) {
                    System.out.print(bm.getRGB(i,j)==Color.BLACK.getRGB()? "*":" ");
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void write(BufferedImage image) {
        try {
            //C:\\Users\\Administrator\\Desktop\\
            //ImageIO.write(image, "png", new File("/Users/guoxinlu/Desktop/" + System.currentTimeMillis() + ".png"));
            ImageIO.write(image, "png", new File("C:\\Users\\Administrator\\Desktop\\" + System.currentTimeMillis() + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void fill(BufferedImage image){
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(Color.blue);
        graphics.fillRect(0,0,image.getWidth(), image.getHeight());
        graphics.dispose();
    }
}
