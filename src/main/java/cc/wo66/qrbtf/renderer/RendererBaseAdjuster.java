package cc.wo66.qrbtf.renderer;

import cc.wo66.qrbtf.Shape;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.*;

public class RendererBaseAdjuster {

    private final Renderer renderer;

    public RendererBaseAdjuster(Renderer renderer) {
        if (renderer == null || renderer.getParameters() == null) {
            throw new IllegalArgumentException("Renderer or Parameters is null");
        }
        this.renderer = renderer;
    }

    public Renderer end() {
        return this.renderer;
    }

    public RendererBaseAdjuster errorCorrectionLevel(ErrorCorrectionLevel level) {
        renderer.getParameters().setErrorCorrectionLevel(level);
        return this;
    }

    public RendererBaseAdjuster anchorPointShape(Shape anchorPointShape) {
        if (Shape.RECTANGLE == anchorPointShape
                || Shape.CIRCLE == anchorPointShape
                || Shape.PLANET == anchorPointShape
                || Shape.ROUNDED_RECTANGLE == anchorPointShape) {
            renderer.getParameters().setAnchorPointShape(anchorPointShape);
        }
        return this;
    }

    public RendererBaseAdjuster anchorPointColor(Color anchorPointColor) {
        renderer.getParameters().setAnchorPointColor(anchorPointColor);
        return this;
    }

    public RendererBaseAdjuster dataPointShape(Shape dataPointShape) {
        if (Shape.RECTANGLE == dataPointShape
                || Shape.CIRCLE == dataPointShape
                || Shape.RANDOM == dataPointShape) {
            renderer.getParameters().setDataPointShape(dataPointShape);
        }
        return this;
    }

    public RendererBaseAdjuster dataPointScale(int dataPointScale) {
        if (dataPointScale > 100) {
            dataPointScale = 100;
        }
        if (dataPointScale < 5) {
            dataPointScale = 5;
        }
        renderer.getParameters().setDataPointScale(dataPointScale);
        return this;
    }

    public RendererBaseAdjuster dataPointOpacity(int dataPointOpacity) {
        if (dataPointOpacity > 100) {
            dataPointOpacity = 100;
        }
        if (dataPointOpacity < 5) {
            dataPointOpacity = 5;
        }
        renderer.getParameters().setDataPointOpacity(dataPointOpacity);
        return this;
    }

    public RendererBaseAdjuster dataPointColor(Color dataPointColor) {
        renderer.getParameters().setDataPointColor(dataPointColor);
        return this;
    }

    public RendererBaseAdjuster iconScale(int iconScale) {
        if (iconScale > 100) {
            iconScale = 100;
        }
        if (iconScale < 5) {
            iconScale = 0;
        }
        renderer.getParameters().setIconScale(iconScale);
        return this;
    }

    public RendererBaseAdjuster iconBase64(String iconBase64) {
        if (iconBase64 == null) {
            iconBase64 = "";
        }
        renderer.getParameters().setIconBase64(iconBase64);
        return this;
    }

    public RendererBaseAdjuster backgroundColor(Color backgroundColor) {
        renderer.getParameters().setAnchorPointColor2(backgroundColor);
        renderer.getParameters().setDataPointColor2(backgroundColor);
        return this;
    }
}
