package cc.wo66.qrbtf.filter;

/**
 * ClassName: Filter
 *
 * @author guoxinlu
 * @since 2022-07-24 12:19
 */
public interface Filter {

    void doFilter(ImageData imageData);

    default void generateGray(ImageData imageData) {
        byte[] gray = new byte[imageData.getWidth() * imageData.getHeight()];
        int tr=0, tg=0, tb=0, c=0;
        byte[] R = imageData.getR();
        byte[] G = imageData.getG();
        byte[] B = imageData.getB();
        for (int i=0; i<gray.length; i++) {
            tr = R[i] & 0xff;
            tg = G[i] & 0xff;
            tb = B[i] & 0xff;
            c = (int) (0.299 * tr + 0.587 * tg + 0.114 * tb);
            gray[i] = (byte) c;
        }
        imageData.setGray(gray);
    }
}
