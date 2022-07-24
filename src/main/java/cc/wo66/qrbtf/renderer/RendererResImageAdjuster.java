package cc.wo66.qrbtf.renderer;

import cc.wo66.qrbtf.BackgroundEnhance;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.*;

public class RendererResImageAdjuster {

    private final Renderer renderer;

    public RendererResImageAdjuster(Renderer renderer) {
        if (renderer == null || renderer.getParameters() == null) {
            throw new IllegalArgumentException("Renderer or Parameters is null");
        }
        this.renderer = renderer;
    }

    public Renderer end() {
        return this.renderer;
    }

    public RendererResImageAdjuster errorCorrectionLevel(ErrorCorrectionLevel level) {
        renderer.getParameters().setErrorCorrectionLevel(level);
        return this;
    }

    public RendererResImageAdjuster anchorPointColor(Color anchorPointColor) {
        renderer.getParameters().setAnchorPointColor(anchorPointColor);
        return this;
    }

    public RendererResImageAdjuster dataPointColor(Color dataPointColor) {
        if (dataPointColor == null) {
            dataPointColor = Color.BLACK;
        }
        renderer.getParameters().setDataPointColor(dataPointColor);
        return this;
    }

    public RendererResImageAdjuster iconScale(int iconScale) {
        if (iconScale > 100) {
            iconScale = 100;
        }
        if (iconScale < 5) {
            iconScale = 0;
        }
        renderer.getParameters().setIconScale(iconScale);
        return this;
    }

    public RendererResImageAdjuster iconBase64(String iconBase64) {
        if (iconBase64 == null) {
            iconBase64 = "";
        }
        renderer.getParameters().setIconBase64(iconBase64);
        return this;
    }

    public RendererResImageAdjuster backgroundImageBase64(String backgroundImageBase64) {
        if (backgroundImageBase64 == null) {
            backgroundImageBase64 = "";
        }
        renderer.getParameters().setBgEnhance(BackgroundEnhance.BINARIZATION);
        renderer.getParameters().setBackgroundImageBase64(backgroundImageBase64);
        return this;
    }

    /**
     * 亮度 建议 -3.0f ~ 3.0f
     * 默认 1.0f
     */
    public RendererResImageAdjuster brightness(float brightness) {
        renderer.getParameters().setBrightness(brightness);
        return this;
    }

    /**
     * 对比度  -100.0f ~ 100.0f
     * 默认 1.0f
     */
    public RendererResImageAdjuster contrast(float contrast) {
        renderer.getParameters().setContrast(contrast);
        return this;
    }


}
