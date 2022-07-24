package cc.wo66.qrbtf.filter;

/**
 * ClassName: BinaryFilter
 * 二值化
 *
 * @author guoxinlu
 * @since 2022-07-24 12:28
 */
public class BinaryFilter implements Filter{

    public void doFilter(ImageData imageData) {
        // 先计算灰度值
        generateGray(imageData);
        byte[] GRAY = imageData.getGray();

        float graySum = 0;
        int total = imageData.getWidth() * imageData.getHeight();
        for(int i=0; i<total; i++){
            graySum += GRAY[i]&0xff;
        }
        int means = (int)(graySum / total);

        int c = 0;
        for(int i=0; i<total; i++) {
            c = ((GRAY[i]&0xff) >= means) ? 255 : 0;
            GRAY[i] = (byte)c;
        }
    }
}
