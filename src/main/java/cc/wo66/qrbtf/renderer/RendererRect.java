package cc.wo66.qrbtf.renderer;

import cc.wo66.qrbtf.Parameters;
import cc.wo66.qrbtf.Shape;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.*;

/**
 * ClassName: RendererRect (A1)
 *
 * @author guoxinlu
 * @since 2022-07-10 15:53
 */
public class RendererRect extends Renderer {

    public RendererRect() {
        style = RendererStyle.RECT;

        parameters = new Parameters();
        parameters.setErrorCorrectionLevel(ErrorCorrectionLevel.M);
        parameters.setAnchorPointShape(Shape.RECTANGLE);
        parameters.setAnchorPointColor(Color.BLACK);
        parameters.setDataPointShape(Shape.RECTANGLE);
        parameters.setDataPointScale(100);
        parameters.setDataPointOpacity(100);
        parameters.setDataPointColor(Color.BLACK);
    }

    @Override
    public RendererBaseAdjuster adjust() {
        return new RendererBaseAdjuster(this);
    }

}
