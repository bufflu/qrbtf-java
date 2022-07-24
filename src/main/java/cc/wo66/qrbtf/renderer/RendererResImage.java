package cc.wo66.qrbtf.renderer;

import cc.wo66.qrbtf.BackgroundEnhance;
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
        parameters.setDataPointColor2(Color.WHITE);
        parameters.setBackgroundImageBase64("");
        parameters.setBgEnhance(BackgroundEnhance.BINARIZATION); // 二值化
        parameters.setBackgroundColor(Color.WHITE);
        parameters.setBrightness(1f);  // 亮度
        parameters.setContrast(1f);    // 对比度
    }

    @Override
    public RendererResImageAdjuster adjust() {
        return new RendererResImageAdjuster(this);
    }

}
