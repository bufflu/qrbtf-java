package cc.wo66.qrbtf;

import cc.wo66.qrbtf.draw.AnchorDraw;
import cc.wo66.qrbtf.draw.DataDraw;
import cc.wo66.qrbtf.draw.IconDraw;
import cc.wo66.qrbtf.renderer.Renderer;
import cc.wo66.qrbtf.renderer.RendererRect;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Version;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.encoder.QRCode;

import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * ClassName: QRBtf
 *
 * @author guoxinlu
 * @since 2022-07-10 9:18
 */
public class QRBtf {

    /**
     * 渲染器
     */
    private final Renderer renderer;

    public QRBtf(){
        this.renderer = new RendererRect();
    }

    public QRBtf(Renderer renderer) {
        this.renderer = renderer;
    }


    public BufferedImage encode(String contents, Map<EncodeHintType, Object> hints) throws WriterException {
        QRCode qrCode = encode(contents, renderer.getParameters().getErrorCorrectionLevel(), hints);
        return beautify(qrCode, renderer.getParameters());
    }

    private BufferedImage beautify(QRCode qrCode, Parameters parameters) {
        Version version = qrCode.getVersion();

        ByteMatrix matrix = qrCode.getMatrix();
        int width = matrix.getWidth();
        int multiple = width; //todo 放大倍数有待考虑
        // 图像大小   如原始矩阵为 25*25 那么图像像素点应为 (25*25) * (25*25), 这么做是为了清晰度以及更好进行缩放
        int imageWidth = width * multiple;

        // 0 绘画板
        BufferedImage drawImage = createBufferedImage(imageWidth);
        QRBtfUtil.fillBackGroundColor(drawImage, parameters.getBackgroundColor());

        // 1 定位点 AnchorPoint
        AnchorDraw.create(multiple).draw(matrix, drawImage, parameters);
        // 2 数据点 DataPoint
        DataDraw.create(version, multiple).draw(matrix, drawImage, parameters);
        // 3 icon IconPoint
        IconDraw.create(parameters.getErrorCorrectionLevel(), width, multiple)
                .draw(drawImage, parameters.getIconBase64(), parameters.getIconScale());


        return drawImage;
    }



    public QRCode encode(String contents, ErrorCorrectionLevel ecLevel, Map<EncodeHintType, Object> hints) throws WriterException {
        ErrorCorrectionLevel errorCorrectionLevel = ErrorCorrectionLevel.L;
        if (ecLevel == null && hints != null && hints.containsKey(EncodeHintType.ERROR_CORRECTION)) {
            errorCorrectionLevel = ErrorCorrectionLevel.valueOf(
                    hints.get(EncodeHintType.ERROR_CORRECTION).toString());
        }
        if (ecLevel != null) {
            errorCorrectionLevel = ecLevel;
        }
        return Encoder.encode(contents, errorCorrectionLevel, hints);
    }

    private BufferedImage createBufferedImage(int side) {
        return new BufferedImage(side, side, BufferedImage.TYPE_INT_ARGB);
    }


}
