package cc.wo66.qrbtf.filter;


public class ColorFilter implements Filter {

    private int style;

    public ColorFilter() {}

    public ColorFilter(int style) {
        this.style = style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    @Override
    public void doFilter(ImageData imageData) {

        int tr=0;
        int tg=0;
        int tb=0;
        int[][] srgb = ColorStyle.getRGB(style);
        int size = imageData.getR().length;
        for(int i=0; i<size; i++) {
            tr = imageData.getR()[i] & 0xff;
            tg = imageData.getG()[i] & 0xff;
            tb = imageData.getB()[i] & 0xff;

            imageData.getR()[i] = (byte)srgb[tr][0];
            imageData.getG()[i] = (byte)srgb[tg][1];
            imageData.getB()[i] = (byte)srgb[tb][2];
        }
    }

}
