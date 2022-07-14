package cc.wo66.qrbtf.renderer;

import cc.wo66.qrbtf.Parameters;
import cc.wo66.qrbtf.Shape;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.*;

/**
 * ClassName: RendererRound (A2)
 *
 * @author guoxinlu
 * @since 2022-07-10 15:53
 */
public class RendererRound extends Renderer{

    public RendererRound() {
        style = RendererStyle.ROUND;

        parameters = new Parameters();
        parameters.setErrorCorrectionLevel(ErrorCorrectionLevel.M);
        parameters.setAnchorPointShape(Shape.CIRCLE);
        parameters.setAnchorPointColor(Color.BLACK);
        parameters.setDataPointShape(Shape.CIRCLE);
        parameters.setDataPointScale(50);
        parameters.setDataPointOpacity(30);
        parameters.setDataPointColor(Color.BLACK);
    }


    @Override
    public RendererBaseAdjuster adjust() {
        return new RendererBaseAdjuster(this);
    }

}
