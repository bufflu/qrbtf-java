package cc.wo66.qrbtf.renderer;

import cc.wo66.qrbtf.BackgroundEnhance;
import cc.wo66.qrbtf.Parameters;
import cc.wo66.qrbtf.Shape;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.*;
import java.util.Arrays;


/**
 * ClassName: RendererFillImage (C3)
 *
 * @author guoxinlu
 * @since 2022-08-02 11:40
 */
public class RendererFillImage extends Renderer{

    public RendererFillImage() {
        style = RendererStyle.FILL_IMAGE;

        parameters = new Parameters();
        parameters.setErrorCorrectionLevel(ErrorCorrectionLevel.M);
        parameters.setAnchorPointShape(Shape.RECTANGLE);
        parameters.setAnchorPointColor(null);      // 默认透明
        parameters.setAnchorPointColor2(Color.WHITE);
        parameters.setDataPointShape(Shape.RECTANGLE);
        parameters.setDataPointColor(null);        // 默认透明
        parameters.setDataPointColor2(Color.WHITE);// 浅色
        parameters.setBackgroundImageBase64("");
        parameters.setCoverOpacity(60);
        parameters.setBeautifulLine(false);
    }

    @Override
    public RendererFillImageAdjuster adjust() {
        return new RendererFillImageAdjuster(this);
    }

}
