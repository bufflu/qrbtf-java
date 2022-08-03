package cc.wo66.qrbtf.filter;

import java.awt.image.BufferedImage;

/**
 * ClassName: ImageFilter
 *
 * @author guoxinlu
 * @since 2022-07-24 11:13
 */
public class ImageFilter {

    private ImageData imageData;

    private ImageFilter(ImageData imageData){
        this.imageData = imageData;
    }

    public ImageData getImageData() {
        return imageData;
    }

    public static ImageFilter create(BufferedImage image) {
        return new ImageFilter(initImageData(image));
    }

    private static ImageData initImageData(BufferedImage image) {
        int height = image.getHeight();
        int width = image.getWidth();
        int[] pixels = new int[width * height];
        image.getRGB(0, 0, width, height, pixels, 0, width );
        int size = width * height;
        byte[] R = new byte[size];
        byte[] G = new byte[size];
        byte[] B = new byte[size];
        int c=0, r=0, g=0, b=0;
        for(int i=0; i<pixels.length; i++) {
            c = pixels[i];
            r = (c&0xff0000)>>16;
            g = (c&0xff00)>>8;
            b = c&0xff;
            R[i] = (byte)r;
            G[i] = (byte)g;
            B[i] = (byte)b;
        }
        ImageData data = new ImageData();
        data.setR(R);
        data.setG(G);
        data.setB(B);
        data.setWidth(width);
        data.setHeight(height);
        return data;
    }


    public ImageFilter filter(Filter filter) {
        filter.doFilter(this.getImageData());
        return this;
    }

    public void outColor(BufferedImage image, int alpha) {
        out(image, true, alpha);
    }

    public BufferedImage outColor(int alpha) {
        return out(null, true, alpha);
    }

    public void outGray(BufferedImage image) {
        out(image, false, 255);
    }

    public BufferedImage outGray() {
        return out(null, false, 255);
    }

    public BufferedImage out(BufferedImage image, boolean isColor, int alpha) {
        if (image == null) {
            image = new BufferedImage(imageData.getWidth(), imageData.getHeight(), BufferedImage.TYPE_INT_ARGB);
        }
        int[] pixels = new int[imageData.getWidth() * imageData.getHeight()];
        for (int i = 0; i < pixels.length; i++) {
            if (isColor) {
                pixels[i] = ((alpha & 0xff) << 24) | ((imageData.getR()[i] & 0xff) << 16) | ((imageData.getG()[i] & 0xff) << 8) | imageData.getB()[i] & 0xff;
            } else {
                pixels[i] = ((alpha & 0xff) << 24) | ((imageData.getGray()[i]&0xff)<<16) | ((imageData.getGray()[i]&0xff)<<8) | imageData.getGray()[i]&0xff;
            }
        }
        image.setRGB(0, 0, imageData.getWidth(), imageData.getHeight(), pixels, 0, imageData.getWidth());
        return image;
    }

}
