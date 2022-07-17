package cc.wo66.qrbtf.renderer;

import cc.wo66.qrbtf.Shape;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.*;

public class RendererFuncAdjuster {

    private final Renderer renderer;

    public RendererFuncAdjuster(Renderer renderer) {
        if (renderer == null || renderer.getParameters() == null) {
            throw new IllegalArgumentException("Renderer or Parameters is null");
        }
        this.renderer = renderer;
    }

    public Renderer end() {
        return this.renderer;
    }

    public RendererFuncAdjuster errorCorrectionLevel(ErrorCorrectionLevel level) {
        renderer.getParameters().setErrorCorrectionLevel(level);
        return this;
    }

    public RendererFuncAdjuster anchorPointShape(Shape anchorPointShape) {
        if (Shape.RECTANGLE == anchorPointShape
                || Shape.CIRCLE == anchorPointShape
                || Shape.PLANET == anchorPointShape
                || Shape.ROUNDED_RECTANGLE == anchorPointShape) {
            renderer.getParameters().setAnchorPointShape(anchorPointShape);
        }
        return this;
    }

    public RendererFuncAdjuster anchorPointColor(Color anchorPointColor) {
        renderer.getParameters().setAnchorPointColor(anchorPointColor);
        return this;
    }

    public RendererFuncAdjuster dataPointShape(Shape dataPointShape) {
        if (Shape.RECTANGLE == dataPointShape
                || Shape.CIRCLE == dataPointShape) {
            renderer.getParameters().setDataPointShape(dataPointShape);
        }
        return this;
    }

    public RendererFuncAdjuster dataPointColor(Color dataPointColor) {
        renderer.getParameters().setDataPointColor(dataPointColor);
        return this;
    }

    public RendererFuncAdjuster dataPointColor2(Color dataPointColor2) {
        renderer.getParameters().setDataPointColor2(dataPointColor2);
        return this;
    }

    public RendererFuncAdjuster iconScale(int iconScale) {
        if (iconScale > 100) {
            iconScale = 100;
        }
        if (iconScale < 5) {
            iconScale = 0;
        }
        renderer.getParameters().setIconScale(iconScale);
        return this;
    }

    public RendererFuncAdjuster iconBase64(String iconBase64) {
        if (iconBase64 == null) {
            iconBase64 = "";
        }
        renderer.getParameters().setIconBase64(iconBase64);
        return this;
    }

    public RendererFuncAdjuster backgroundColor(Color backgroundColor) {
        renderer.getParameters().setBackgroundColor(backgroundColor);
        return this;
    }

    public RendererFuncAdjuster func(boolean func) {
        renderer.getParameters().setFunc(func);
        return this;
    }
}
