package cc.wo66.qrbtf.draw;

import cc.wo66.qrbtf.LineDirection;
import cc.wo66.qrbtf.QRBtfUtil;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import lombok.Data;
import org.apache.commons.lang3.RandomUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class LineDraw {

    private int multiple;

    private LineDraw(){}

    public static LineDraw create(int multiple) {
        LineDraw lineDraw = new LineDraw();
        lineDraw.setMultiple(multiple);
        return lineDraw;
    }

    // !!! 该方法会导致 matrix 中数据发生修改
    public void draw(ByteMatrix matrix, Graphics2D graphics, LineDirection lineDirection) {
        java.util.List<int[]> pointList = new ArrayList<>();

        if (LineDirection.LEFT_RIGHT == lineDirection) {
            pointList = getPointLeftRight(matrix);

        } else if (LineDirection.UP_DOWN == lineDirection) {
            pointList = getPointUpDown(matrix);

        } else if (LineDirection.TOP_LEFT_BOTTOM_RIGHT == lineDirection) {
            pointList = getPointTopLeftBottomRight(matrix);

        } else if (LineDirection.TOP_RIGHT_BOTTOM_LEFT == lineDirection) {
            pointList = getPointTopRightBottomLeft(matrix);

        } else if (LineDirection.VERTICAL_HORIZONTAL == lineDirection) {
            pointList = getPointVerticalHorizontal(matrix);

        } else if (LineDirection.LOOPBACK == lineDirection) {
            pointList = getPointLoopback(matrix);

        } else if (LineDirection.CROSS == lineDirection) {
            pointList = getPointCross(matrix);

        }

        if (pointList.isEmpty()) {
            return;
        }

        // 针对 Cross 类型做特殊处理


        for (int[] point : pointList) {
            // (pointX + 1/2) * multiple 找到线的起点
            graphics.drawLine(point[0]*multiple+multiple/2, point[1]*multiple+multiple/2, point[2]*multiple+multiple/2, point[3]*multiple+multiple/2);
        }
    }

    // 绘制交叉线（和点）
    public void drawCross(ByteMatrix matrix, Graphics2D graphics, int lineStroke, java.util.List<int[]> pointList) {
        // 画线
        for (int[] point : pointList) {
            int r = RandomUtils.nextInt(1, 5);
            int lineWidth = multiple * lineStroke*r*50/500; // 正常线宽的 1/2 然后再随机(20\40\60\80\100)
            if (lineWidth < 2) continue;
            // 设置线宽
            graphics.setStroke(new BasicStroke((float)lineWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            graphics.drawLine(point[0]*multiple+multiple/2, point[1]*multiple+multiple/2, point[2]*multiple+multiple/2, point[3]*multiple+multiple/2);
        }

        // 画点 todo
    }





    // 左右 pass
    private java.util.List<int[]> getPointLeftRight(ByteMatrix matrix){
        int matrixSide = matrix.getWidth();
        java.util.List<int[]> list = new ArrayList<>();
        for (int y = 0; y < matrixSide; y++) {
            for (int x = 0; x < matrixSide; x++) {
                if (matrix.get(x,y) != 1 || !DataDraw.isDataPoint(x, y, matrixSide)) {
                    continue;
                }
                int d = 1;
                while (x+d < matrixSide && DataDraw.isDataPoint(x+d, y, matrixSide) && matrix.get(x+d,y) == 1) {
                    d++;
                }
                list.add(new int[]{x,y,x+d-1,y});
                x+=d;
            }
        }
        return list;
    }

    // 上下 pass
    private java.util.List<int[]> getPointUpDown(ByteMatrix matrix){
        int matrixSide = matrix.getWidth();
        java.util.List<int[]> list = new ArrayList<>();
        for (int x = 0; x < matrixSide; x++) {
            for (int y = 0; y < matrixSide; y++) {
                if (matrix.get(x,y) != 1 || !DataDraw.isDataPoint(x, y, matrixSide)) {
                    continue;
                }
                int d = 1;
                while (y+d < matrixSide && DataDraw.isDataPoint(x, y+d, matrixSide) && matrix.get(x,y+d) == 1) {
                    d++;
                }
                list.add(new int[]{x,y,x,y+d-1});
                y+=d;
            }
        }
        return list;
    }

    // 纵横 pass  !!! 修改了 matrix
    private java.util.List<int[]> getPointVerticalHorizontal(ByteMatrix matrix){
        int matrixSide = matrix.getWidth();
        java.util.List<int[]> list = new ArrayList<>();
        for (int y = 0; y < matrixSide; y++) {
            for (int x = 0; x < matrixSide; x++) {
                if (matrix.get(x,y) != 1 || !DataDraw.isDataPoint(x, y, matrixSide)) {
                    continue;
                }
                int yd = 1;
                while (y+yd < matrixSide && DataDraw.isDataPoint(x, y+yd, matrixSide) && matrix.get(x,y+yd) == 1) {
                    matrix.set(x, y+yd, 0);
                    yd++;
                }
                if (yd > 1) {
                    list.add(new int[]{x, y, x, y+yd-1});
                } else {
                    int xd = 1;
                    while (x+xd < matrixSide && DataDraw.isDataPoint(x+xd, y, matrixSide) && matrix.get(x+xd,y) == 1) {
                        matrix.set(x+xd, y, 0);
                        xd++;
                    }
                    list.add(new int[]{x, y, x+xd-1, y});
                }
            }
        }
        return list;
    }

    // 回环 pass   !!! 修改了 matrix
    private java.util.List<int[]> getPointLoopback(ByteMatrix matrix){
        int matrixSide = matrix.getWidth();
        java.util.List<int[]> list = new ArrayList<>();
        for (int y = 0; y < matrixSide; y++) {
            for (int x = 0; x < matrixSide; x++) {
                if (matrix.get(x,y) != 1 || !DataDraw.isDataPoint(x, y, matrixSide)) {
                    continue;
                }
                int d = 1;
                if ((x > y && y > matrixSide - x) || (x < y && y < matrixSide - x)) {
                    while (y + d < matrixSide && DataDraw.isDataPoint(x, y+d, matrixSide) && matrix.get(x, y+d) == 1) {
                        matrix.set(x, y+d, 0);
                        d++;
                    }
                    list.add(new int[]{x,y,x,y+d-1});
                } else {
                    while (x + d < matrixSide && DataDraw.isDataPoint(x + d, y, matrixSide) && matrix.get(x + d, y) == 1) {
                        matrix.set(x+d, y, 0);
                        d++;
                    }
                    list.add(new int[]{x,y,x+d-1,y});
                }
            }
        }
        return list;
    }

    // 左上右下 pass
    private java.util.List<int[]> getPointTopLeftBottomRight(ByteMatrix matrix){
        int matrixSide = matrix.getWidth();
        java.util.List<int[]> list = new ArrayList<>();
        for (int x = matrixSide-1; x > 0; x--) {
            for (int i = x, j = 0; i < matrixSide && j < matrixSide; i++, j++) {
                if (!DataDraw.isDataPoint(i, j, matrixSide)) {
                    continue;
                }
                if (matrix.get(i,j) == 1) {
                    int d = 1;
                    while (i+d < matrixSide && j+d < matrixSide
                            && DataDraw.isDataPoint(i+d, j+d, matrixSide)
                            && matrix.get(i+d, j+d) == 1) {
                        d++;
                    }
                    list.add(new int[]{i,j,i+d-1,j+d-1});
                    i+=d;
                    j+=d;
                }
            }
        }
        for (int y = 0; y < matrixSide; y++) {
            for (int i = 0, j = y; i < matrixSide && j < matrixSide; i++, j++) {
                if (!DataDraw.isDataPoint(i, j, matrixSide)) {
                    continue;
                }
                if (matrix.get(i,j) == 1) {
                    int d = 1;
                    while (i+d < matrixSide && j+d < matrixSide
                            && DataDraw.isDataPoint(i+d, j+d, matrixSide)
                            && matrix.get(i+d, j+d) == 1) {
                        d++;
                    }
                    list.add(new int[]{i,j,i+d-1,j+d-1});
                    i+=d;
                    j+=d;
                }
            }
        }
        return list;
    }

    // 右上左下 pass
    private java.util.List<int[]> getPointTopRightBottomLeft(ByteMatrix matrix){
        int matrixSide = matrix.getWidth();
        java.util.List<int[]> list = new ArrayList<>();
        for (int x = 0; x < matrixSide; x++) {
            for (int i = x, j = 0; i > 0 && j < matrixSide; i--, j++) {
                if (!DataDraw.isDataPoint(i, j, matrixSide)) {
                    continue;
                }
                if (matrix.get(i,j) == 1) {
                    int d = 1;
                    while (i-d < matrixSide && j+d < matrixSide
                            && DataDraw.isDataPoint(i-d, j+d, matrixSide)
                            && matrix.get(i-d, j+d) == 1) {
                        d++;
                    }
                    list.add(new int[]{i,j,i-d+1,j+d-1});
                    i-=d;
                    j+=d;
                }
            }
        }
        for (int y = 0; y < matrixSide; y++) {
            for (int i = matrixSide-1, j = y; i > 0 && j < matrixSide; i--, j++) {
                if (!DataDraw.isDataPoint(i, j, matrixSide)) {
                    continue;
                }
                if (matrix.get(i,j) == 1) {
                    int d = 1;
                    while (i-d < matrixSide && j+d < matrixSide
                            && DataDraw.isDataPoint(i-d, j+d, matrixSide)
                            && matrix.get(i-d, j+d) == 1) {
                        d++;
                    }
                    list.add(new int[]{i,j,i-d+1,j+d-1});
                    i-=d;
                    j+=d;
                }
            }
        }
        return list;
    }

    // 交叉
    private java.util.List<int[]> getPointCross(ByteMatrix matrix){
        List<int[]> pointTopLeftBottomRight = getPointTopLeftBottomRight(matrix);
        List<int[]> pointTopRightBottomLeft = getPointTopRightBottomLeft(matrix);
        pointTopLeftBottomRight.addAll(pointTopRightBottomLeft);
        return pointTopLeftBottomRight;
    }
}
