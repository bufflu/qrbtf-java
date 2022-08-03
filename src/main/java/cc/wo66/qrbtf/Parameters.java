package cc.wo66.qrbtf;

import cc.wo66.qrbtf.filter.ColorStyle;
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
     * 定位点颜色(深色) 主色
     */
    private Color anchorPointColor;

    /**
     * 定位点颜色(浅色)
     */
    private Color anchorPointColor2;

    /**
     * 美观线(矩形块的分割线)
     */
    private boolean beautifulLine = true;

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
     * 信息点颜色（主色 或 深色）
     */
    private Color dataPointColor;

    /**
     * 信息点颜色 2 (浅色 或 第二种信息点颜色)
     */
    private Color dataPointColor2;

    /**
     * 图标 Base64 编码后的字符串
     */
    private String iconBase64 = "";

    /**
     * 图标缩放
     */
    private int iconScale = 100;

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
     * 干扰点颜色
     */
    private Color funcColor;

    /**
     * 背景图片 base64
     */
    private String backgroundImageBase64 = "";

    /**
     * 定位区域留白 C1 样式独有
     */
    private boolean anchorArea;

    /**
     * 背景增强
     */
    private java.util.List<BackgroundEnhance> bgEnhance;

    /**
     * 亮度
     */
    private float brightness = 0;

    /**
     * 对比度
     */
    private float contrast = 0;

    /**
     * 覆盖颜色
     * @see ColorStyle
     */
    private int coverColorStyle;

    /**
     * 覆盖不透明度
     */
    private int coverOpacity = 10;

}
