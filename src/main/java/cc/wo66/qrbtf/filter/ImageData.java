package cc.wo66.qrbtf.filter;

import lombok.Data;

/**
 * ClassName: ImageData
 *
 * @author guoxinlu
 * @since 2022-07-24 11:59
 */
@Data
public class ImageData {

    private byte[] R;
    private byte[] G;
    private byte[] B;
    private byte[] gray;

    private int width;
    private int height;

}
