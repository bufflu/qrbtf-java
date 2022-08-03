package cc.wo66.qrbtf.renderer;

import cc.wo66.qrbtf.BackgroundEnhance;
import cc.wo66.qrbtf.filter.ColorStyle;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.*;
import java.util.Arrays;

public class RendererFillImageAdjuster {

    private final Renderer renderer;

    public RendererFillImageAdjuster(Renderer renderer) {
        if (renderer == null || renderer.getParameters() == null) {
            throw new IllegalArgumentException("Renderer or Parameters is null");
        }
        this.renderer = renderer;
    }

    public Renderer end() {
        return this.renderer;
    }

    public RendererFillImageAdjuster errorCorrectionLevel(ErrorCorrectionLevel level) {
        renderer.getParameters().setErrorCorrectionLevel(level);
        return this;
    }

    /**
     * 滤镜风格
     * @see ColorStyle
     * 这里和 qrbtf.com 的实现不同，由覆盖颜色转为滤镜风格
     */
    public RendererFillImageAdjuster coverColorStyle(Integer colorStyle) {
        if (colorStyle != null) {
            renderer.getParameters().setCoverColorStyle(colorStyle);
            renderer.getParameters().setBgEnhance(Arrays.asList(BackgroundEnhance.COLOR_FILTER));
        }
        return this;
    }

    /**
     * 覆盖不透明度
     */
    public RendererFillImageAdjuster coverOpacity(int coverOpacity) {
        if (coverOpacity > 100) {
            coverOpacity = 100;
        }
        if (coverOpacity < 0) {
            coverOpacity = 0;
        }
        renderer.getParameters().setCoverOpacity(coverOpacity);
        return this;
    }

    public RendererFillImageAdjuster iconScale(int iconScale) {
        if (iconScale > 100) {
            iconScale = 100;
        }
        if (iconScale < 5) {
            iconScale = 0;
        }
        renderer.getParameters().setIconScale(iconScale);
        return this;
    }

    public RendererFillImageAdjuster iconBase64(String iconBase64) {
        if (iconBase64 == null) {
            iconBase64 = "";
        }
        renderer.getParameters().setIconBase64(iconBase64);
        return this;
    }

    public RendererFillImageAdjuster backgroundImageBase64(String backgroundImageBase64) {
        if (backgroundImageBase64 == null) {
            backgroundImageBase64 = "";
        }
        renderer.getParameters().setBackgroundImageBase64(backgroundImageBase64);
        return this;
    }

    /**
     * 美观线
     */
    public RendererFillImageAdjuster beautifulLine(boolean beautifulLine) {
        renderer.getParameters().setBeautifulLine(beautifulLine);
        return this;
    }


}
