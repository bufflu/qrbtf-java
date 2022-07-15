package cc.wo66.qrbtf;

/**
 * ClassName: LineDirection
 * 线条方向
 *
 * @author guoxinlu
 * @since 2022-07-14 23:03
 */
public enum LineDirection {

    LEFT_RIGHT,             // 左右
    UP_DOWN,                // 上下
    VERTICAL_HORIZONTAL,    // 纵横
    LOOPBACK,               // 回环
    TOP_LEFT_BOTTOM_RIGHT,  // 左上-右下
    TOP_RIGHT_BOTTOM_LEFT,  // 右上-左下
    CROSS;                  // 交叉
}
