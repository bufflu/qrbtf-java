package cc.wo66.qrbtf.renderer;

import cc.wo66.qrbtf.LineDirection;
import cc.wo66.qrbtf.Parameters;
import cc.wo66.qrbtf.Shape;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.*;


/**
 * ClassName: RendererLine2 (A-a2)
 *
 * @author guoxinlu
 * @since 2022-07-16 20:30
 */
public class RendererLine2 extends Renderer{

    public RendererLine2() {
        style = RendererStyle.LINE2;

        parameters = new Parameters();
        parameters.setErrorCorrectionLevel(ErrorCorrectionLevel.M);
        parameters.setAnchorPointShape(Shape.RECTANGLE);
        parameters.setAnchorPointColor(Color.BLACK);
        parameters.setDataPointShape(Shape.LINE);
        parameters.setLineDirection(LineDirection.CROSS);
        parameters.setLineStroke(50);
        parameters.setLineOpacity(100);
        parameters.setLineColor(Color.BLACK);
        parameters.setDataPointScale(50);
    }

    @Override
    public RendererLineAdjuster adjust() {
        return new RendererLineAdjuster(this);
    }

}
