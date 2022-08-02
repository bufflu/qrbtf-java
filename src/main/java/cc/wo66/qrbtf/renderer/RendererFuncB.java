package cc.wo66.qrbtf.renderer;

import cc.wo66.qrbtf.Parameters;
import cc.wo66.qrbtf.Shape;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.*;


/**
 * ClassName: RendererFuncB (A-b2)
 *
 * @author guoxinlu
 * @since 2022-07-16 22:30
 */
public class RendererFuncB extends Renderer{

    public RendererFuncB() {
        style = RendererStyle.FUNC_B;

        parameters = new Parameters();
        parameters.setErrorCorrectionLevel(ErrorCorrectionLevel.M);
        parameters.setAnchorPointShape(Shape.CIRCLE);
        parameters.setAnchorPointColor(Color.BLACK);
        parameters.setDataPointShape(Shape.CIRCLE);
        parameters.setDataPointScale(50);
        parameters.setDataPointOpacity(100);
        parameters.setDataPointColor(new Color(60,60,60)); // 如果颜色太浅则扫不出来
        parameters.setFunc(true);
        parameters.setFuncColor(Color.BLACK);
    }

    @Override
    public RendererFuncAdjuster adjust() {
        return new RendererFuncAdjuster(this);
    }

}
