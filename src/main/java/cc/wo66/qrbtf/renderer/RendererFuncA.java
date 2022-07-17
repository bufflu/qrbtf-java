package cc.wo66.qrbtf.renderer;

import cc.wo66.qrbtf.Parameters;
import cc.wo66.qrbtf.Shape;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.*;


/**
 * ClassName: RendererFuncA (A-b1)
 *
 * @author guoxinlu
 * @since 2022-07-16 22:20
 */
public class RendererFuncA extends Renderer{

    public RendererFuncA() {
        style = RendererStyle.FUNC_A;

        parameters = new Parameters();
        parameters.setErrorCorrectionLevel(ErrorCorrectionLevel.M);
        parameters.setAnchorPointShape(Shape.CIRCLE);
        parameters.setAnchorPointColor(Color.BLACK);
        parameters.setDataPointShape(Shape.CIRCLE);
        parameters.setDataPointScale(100);
        parameters.setDataPointOpacity(100);
        parameters.setDataPointColor(Color.BLACK);
        parameters.setFunc(false);
    }

    @Override
    public RendererFuncAdjuster adjust() {
        return new RendererFuncAdjuster(this);
    }

}
