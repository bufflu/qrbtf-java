package cc.wo66.qrbtf.renderer;

import cc.wo66.qrbtf.Parameters;
import cc.wo66.qrbtf.Shape;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.*;


/**
 * ClassName: RendererImage (C1)
 *
 * @author guoxinlu
 * @since 2022-07-18 14:20
 */
public class RendererImage extends Renderer{

    public RendererImage() {
        style = RendererStyle.IMAGE;

        parameters = new Parameters();
        parameters.setErrorCorrectionLevel(ErrorCorrectionLevel.M);
        parameters.setAnchorPointShape(Shape.RECTANGLE);
        parameters.setAnchorPointColor(Color.BLACK);
        parameters.setDataPointShape(Shape.RECTANGLE);
        parameters.setDataPointScale(25);          // 实际缩放 25%
        parameters.setDataPointOpacity(100);
        parameters.setDataPointColor(Color.BLACK); // 深色
        parameters.setDataPointColor2(Color.WHITE);// 浅色
        parameters.setBackgroundImageBase64("");
        parameters.setAnchorArea(true);
    }

    @Override
    public RendererImageAdjuster adjust() {
        return new RendererImageAdjuster(this);
    }

}
