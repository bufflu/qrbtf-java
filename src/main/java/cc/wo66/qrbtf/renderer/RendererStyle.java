package cc.wo66.qrbtf.renderer;

/**
 * ClassName: RendererStyle
 * 渲染器样式
 * 参考：https://www.yuque.com/qrbtf/docs/api
 *
 * @author guoxinlu
 * @since 2022-07-10 10:07
 */
public enum RendererStyle {

    NONE("NONE"),       // 无
    CUSTOM("CUSTOM"),   // 自定义
    RECT("A1"),
    ROUND("A2"),
    ROUND3("A3"),
    LINE("A-a1"),
    LINE2("A-a2"),
    FUNC_A("A-b1"),
    FUNC_B("A-b2"),
    DSJ("SP-1"),
    RAND_RECT("SP-2"),
    CIRCLE("SP-3"),
    N_25D("B1"),
    IMAGE("C1"),
    RES_IMAGE("C2");

    private final String showName;

    RendererStyle(String showName) {
        this.showName = showName;
    }

    public String getShowName() {
        return showName;
    }
}
