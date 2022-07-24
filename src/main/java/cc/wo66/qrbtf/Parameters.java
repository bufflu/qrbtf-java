package cc.wo66.qrbtf;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.Data;

import java.awt.*;


/**
 * ClassName: Parameters
 * 参数调整
 * 以下包括所有渲染器拥有的参数
 * 不同渲染器可调整的参数不同，参考 RenderXxx.Builder
 *
 * @author guoxinlu
 * @since 2022-07-10 11:56
 */
@Data
public class Parameters {

    /**
     * 容错率 L(7%) M(15%) Q(25%) H(30%)
     * @see ErrorCorrectionLevel
     */
    private ErrorCorrectionLevel errorCorrectionLevel = ErrorCorrectionLevel.L;

    /**
     * 定位点样式
     */
    private Shape anchorPointShape;

    /**
     * 定位点颜色
     */
    private Color anchorPointColor;

    /**
     * 信息点样式
     */
    private Shape dataPointShape;

    /**
     * 信息点缩放(百分比, 默认100%)
     */
    private int dataPointScale = 100;

    /**
     * 信息点不透明度
     */
    private int dataPointOpacity = 100;

    /**
     * 信息点颜色
     */
    private Color dataPointColor;

    /**
     * 图标 Base64 编码后的字符串
     */
    private String iconBase64 = "";

    /**
     * 图标缩放
     */
    private int iconScale = 100;

    /**
     * 背景色
     */
    private Color backgroundColor;

    /**
     * 连线方向
     */
    private LineDirection lineDirection;

    /**
     * 连线粗细
     */
    private int lineStroke = 100;

    /**
     * 连线不透明度
     */
    private int lineOpacity = 100;

    /**
     * 连线颜色
     */
    private Color lineColor;

    /**
     * 干扰函数
     */
    private Boolean func;

    /**
     * 数据点颜色 2
     */
    private Color dataPointColor2;

    /**
     * 背景图片 base64
     */
    private String backgroundImageBase64 = "";

    /**
     * 背景增强
     */
    private BackgroundEnhance bgEnhance;

    /**
     * 亮度
     */
    private float brightness = 0;

    /**
     * 对比度
     */
    private float contrast = 0;

}
