package cc.wo66.qrbtf.renderer;

import cc.wo66.qrbtf.Shape;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.*;

public class RendererImageAdjuster {

    private final Renderer renderer;

    public RendererImageAdjuster(Renderer renderer) {
        if (renderer == null || renderer.getParameters() == null) {
            throw new IllegalArgumentException("Renderer or Parameters is null");
        }
        this.renderer = renderer;
    }

    public Renderer end() {
        return this.renderer;
    }

    public RendererImageAdjuster errorCorrectionLevel(ErrorCorrectionLevel level) {
        renderer.getParameters().setErrorCorrectionLevel(level);
        return this;
    }

    public RendererImageAdjuster anchorPointShape(Shape anchorPointShape) {
        if (Shape.RECTANGLE == anchorPointShape
                || Shape.CIRCLE == anchorPointShape
                || Shape.PLANET == anchorPointShape) {
            renderer.getParameters().setAnchorPointShape(anchorPointShape);
        }
        return this;
    }

    public RendererImageAdjuster anchorPointColor(Color anchorPointColor) {
        renderer.getParameters().setAnchorPointColor(anchorPointColor);
        return this;
    }

    public RendererImageAdjuster dataPointShape(Shape dataPointShape) {
        if (Shape.RECTANGLE == dataPointShape
                || Shape.CIRCLE == dataPointShape) {
            renderer.getParameters().setDataPointShape(dataPointShape);
        }
        return this;
    }

    /**
     * 深色 #000000  ~  #ffffff 浅色，如果深浅色相差不大则扫不出来
     */
    public RendererImageAdjuster dataPointDarkColor(Color dataPointDarkColor) {
        renderer.getParameters().setDataPointColor(dataPointDarkColor);
        return this;
    }

    public RendererImageAdjuster dataPointLightColor(Color dataPointDarkColor) {
        renderer.getParameters().setDataPointColor2(dataPointDarkColor);
        return this;
    }

    public RendererImageAdjuster dataPointScale(int dataPointScale) {
        if (dataPointScale > 300) {
            dataPointScale = 300;
        }
        if (dataPointScale < 3) {
            dataPointScale = 3;
        }
        renderer.getParameters().setDataPointScale(dataPointScale/3);
        return this;
    }

    public RendererImageAdjuster dataPointOpacity(int dataPointOpacity) {
        if (dataPointOpacity > 100) {
            dataPointOpacity = 100;
        }
        if (dataPointOpacity < 5) {
            dataPointOpacity = 0;
        }
        renderer.getParameters().setDataPointOpacity(dataPointOpacity);
        return this;
    }

    public RendererImageAdjuster iconScale(int iconScale) {
        if (iconScale > 100) {
            iconScale = 100;
        }
        if (iconScale < 5) {
            iconScale = 0;
        }
        renderer.getParameters().setIconScale(iconScale);
        return this;
    }

    public RendererImageAdjuster iconBase64(String iconBase64) {
        if (iconBase64 == null) {
            iconBase64 = "";
        }
        renderer.getParameters().setIconBase64(iconBase64);
        return this;
    }

    public RendererImageAdjuster backgroundImageBase64(String backgroundImageBase64) {
        if (backgroundImageBase64 == null) {
            backgroundImageBase64 = "";
        }
        renderer.getParameters().setBackgroundImageBase64(backgroundImageBase64);
        return this;
    }


}
