package cc.wo66.qrbtf.filter;


/**
 * ClassName: 调整对比度和亮度
 *
 * @author guoxinlu
 * @since 2022-07-24 10:59
 */
public class ConBriFilter implements Filter{

    private float contrast = 1.2f;
    private float brightness = 0.7f;

    public ConBriFilter(){}

    public ConBriFilter(float contrast, float brightness) {
        this.contrast = contrast;
        this.brightness = brightness;
    }

    public void doFilter(ImageData imageData) {
        int total = imageData.getHeight() * imageData.getWidth();
        byte[] R = imageData.getR();
        byte[] G = imageData.getG();
        byte[] B = imageData.getB();

        int[] rgbmeans = new int[3];
        double redSum = 0, greenSum = 0, blueSum = 0;
        int r=0, g=0, b=0;
        for(int i=0; i<total; i++) {
            r = R[i] & 0xff;
            g = G[i] & 0xff;
            b = B[i] & 0xff;
            redSum += r;
            greenSum += g;
            blueSum +=b;
        }

        rgbmeans[0] = (int)(redSum / total);
        rgbmeans[1] = (int)(greenSum / total);
        rgbmeans[2] = (int)(blueSum / total);

        for(int i=0; i<total; i++) {
            r = R[i] & 0xff;
            g = G[i] & 0xff;
            b = B[i] & 0xff;

            r -=rgbmeans[0];
            g -=rgbmeans[1];
            b -=rgbmeans[2];

            r = (int)(r * contrast);
            g = (int)(g * contrast);
            b = (int)(b * contrast);

            r += (int)(rgbmeans[0] * brightness);
            g += (int)(rgbmeans[1] * brightness);
            b += (int)(rgbmeans[2] * brightness);

            R[i] = (byte) (r > 255 ? 255 : (Math.max(r, 0)));
            G[i] = (byte) (g > 255 ? 255 : (Math.max(g, 0)));
            B[i] = (byte) (b > 255 ? 255 : (Math.max(b, 0)));
        }
    }
}
