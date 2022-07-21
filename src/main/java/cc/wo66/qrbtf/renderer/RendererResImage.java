package cc.wo66.qrbtf.renderer;

import cc.wo66.qrbtf.Parameters;
import cc.wo66.qrbtf.Shape;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.*;


/**
 * ClassName: RendererResImage (C2)
 *
 * @author guoxinlu
 * @since 2022-07-21 22:48
 */
public class RendererResImage extends Renderer{

    public RendererResImage() {
        style = RendererStyle.RES_IMAGE;

        parameters = new Parameters();
        parameters.setErrorCorrectionLevel(ErrorCorrectionLevel.M);
        parameters.setAnchorPointShape(Shape.RECTANGLE);
        parameters.setAnchorPointColor(Color.BLACK);
        parameters.setDataPointShape(Shape.RECTANGLE);
        parameters.setDataPointScale(25);          // 实际缩放 25%
        parameters.setDataPointOpacity(100);
        parameters.setDataPointColor(Color.BLACK);
        parameters.setBackgroundImageBase64("");
        parameters.setBinary(true); // 二值化
        parameters.setBackgroundColor(Color.WHITE);
        parameters.setExposure(0);  // 曝光
        parameters.setContrast(0);  // 对比度
    }

    @Override
    public RendererResImageAdjuster adjust() {
        return new RendererResImageAdjuster(this);
    }

}
