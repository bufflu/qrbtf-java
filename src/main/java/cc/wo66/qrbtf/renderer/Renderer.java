package cc.wo66.qrbtf.renderer;


import cc.wo66.qrbtf.Parameters;

/**
 * ClassName: Renderer
 * 渲染器
 *
 * 关于样式的说明(https://www.yuque.com/qrbtf/docs/how_to):
 * 每个样式名由字母 + 数字组成（如 B-2）。字母表示系列，数字表示该系列下的一个分支。字母相同的样式共用一套参数，其下不同数字表示不同的参数预设。
 *
 * @author guoxinlu
 * @since 2022-07-10 15:25
 */
public abstract class Renderer{

    /**
     * 渲染器样式
     */
    RendererStyle style = null;

    /**
     * 调整参数
     */
    Parameters parameters = null;


    /**
     * 根据 RendererStyle 枚举选择合适的渲染器
     * @param style RendererStyle
     * @return Renderer
     */
    public static <T extends Renderer> T choose(RendererStyle style) {
        switch (style) {
            case NONE:
            case RECT: return (T) new RendererRect();
            default: return null;
        }
    }

    /**
     * 参数调整
     * 该方法返回一个调节器(XxxAdjuster)，通过调节器中方法进行参数调整
     */
    abstract Object adjust();

    /**
     * 返回渲染器样式
     */
    public RendererStyle getStyle() {
        return style;
    }

    /**
     * 返回渲染器参数
     */
    public Parameters getParameters() {
        return parameters;
    }


    /**
     * 以下静态方法返回一个对应样式的渲染器，推荐使用该方法而不是 new
     */
    public static RendererRect rect(){
        return new RendererRect();
    }
    public static RendererRound round(){
        return new RendererRound();
    }
    public static RendererRound3 round3(){
        return new RendererRound3();
    }
    public static RendererLine line(){
        return new RendererLine();
    }
    public static RendererLine2 line2(){
        return new RendererLine2();
    }
    public static RendererFuncA funcA(){
        return new RendererFuncA();
    }
    public static RendererFuncB funcB(){
        return new RendererFuncB();
    }
    public static RendererImage image(){
        return new RendererImage();
    }
    public static RendererResImage resImage(){
        return new RendererResImage();
    }
    public static RendererFillImage fillImage(){
        return new RendererFillImage();
    }
}
