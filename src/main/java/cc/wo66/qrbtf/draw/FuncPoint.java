package cc.wo66.qrbtf.draw;

import cc.wo66.qrbtf.QRBtfUtil;
import com.google.zxing.qrcode.encoder.ByteMatrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ClassName: FuncPoint
 * 找不到合适的函数拟合，暂时用固定点定位吧
 *
 * @author guoxinlu
 * @since 2022-07-17 16:05
 */
public class FuncPoint {

    //new int[]{startX, startY, length}
    private static List<int[]> funcPoint21 = Arrays.asList(
            new int[]{7,5,7},
            new int[]{7,6,7},
            new int[]{5,7,3}, new int[]{13,7,3},
            new int[]{5,8,2}, new int[]{14,8,2},
            new int[]{5,9,2}, new int[]{14,9,2},
            new int[]{5,10,2}, new int[]{14,10,2},
            new int[]{5,11,2}, new int[]{14,11,2},
            new int[]{5,12,2}, new int[]{14,12,2},
            new int[]{5,13,3}, new int[]{13,13,3},
            new int[]{7,14,8},
            new int[]{7,15,7}
    );

    private static List<int[]> funcPoint25 = Arrays.asList(
            new int[]{12,5,1},
            new int[]{9,6,7},
            new int[]{8,7,9},
            new int[]{7,8,4}, new int[]{14,8,4},
            new int[]{6,9,3}, new int[]{16,9,3},
            new int[]{6,10,3}, new int[]{16,10,3},
            new int[]{6,11,2}, new int[]{17,11,2},
            new int[]{5,12,3}, new int[]{17,12,3},
            new int[]{6,13,2}, new int[]{17,13,2},
            new int[]{6,14,3}, new int[]{16,14,3},
            new int[]{6,15,3}, new int[]{16,15,3},
            new int[]{7,16,4}, new int[]{14,16,4},
            new int[]{8,17,9},
            new int[]{9,18,7},
            new int[]{12,19,1}
    );

    private static List<int[]> funcPoint29 = Arrays.asList(
            new int[]{13,6,3},
            new int[]{10,7,9},
            new int[]{9,8,11},
            new int[]{8,9,5}, new int[]{16,9,5},
            new int[]{7,10,4}, new int[]{18,10,4},
            new int[]{7,11,3}, new int[]{19,11,3},
            new int[]{7,12,3}, new int[]{19,12,3},
            new int[]{6,13,3}, new int[]{20,13,3},
            new int[]{6,14,3}, new int[]{20,14,3},
            new int[]{6,15,3}, new int[]{20,15,3},
            new int[]{7,16,3}, new int[]{19,16,3},
            new int[]{7,17,3}, new int[]{19,17,3},
            new int[]{7,18,4}, new int[]{18,18,4},
            new int[]{8,19,5}, new int[]{16,19,5},
            new int[]{9,20,11},
            new int[]{10,21,9},
            new int[]{13,22,3}
    );

    private static List<int[]> funcPoint33 = Arrays.asList(
            new int[]{14,7,5},
            new int[]{12,8,9},
            new int[]{10,9,13},
            new int[]{9,10,15},
            new int[]{9,11,4}, new int[]{20,11,4},
            new int[]{8,12,4}, new int[]{21,12,4},
            new int[]{8,13,3}, new int[]{22,13,3},
            new int[]{7,14,4}, new int[]{22,14,4},
            new int[]{7,15,4}, new int[]{22,15,4},
            new int[]{7,16,4}, new int[]{22,16,4},
            new int[]{7,17,4}, new int[]{22,17,4},
            new int[]{7,18,4}, new int[]{22,18,4},
            new int[]{8,19,3}, new int[]{22,19,3},
            new int[]{8,20,4}, new int[]{21,20,4},
            new int[]{9,21,4}, new int[]{20,21,4},
            new int[]{9,22,15},
            new int[]{10,23,13},
            new int[]{12,24,9},
            new int[]{14,25,5}
    );

    private static List<int[]> funcPoint37 = Arrays.asList(
            new int[]{15,8,7},
            new int[]{13,9,11},
            new int[]{12,10,13},
            new int[]{11,11,15},
            new int[]{10,12,6}, new int[]{21,12,6},
            new int[]{9,13,5}, new int[]{23,13,5},
            new int[]{9,14,4}, new int[]{24,14,4},
            new int[]{8,15,5}, new int[]{24,15,5},
            new int[]{8,16,4}, new int[]{25,16,4},
            new int[]{8,17,4}, new int[]{25,17,4},
            new int[]{8,18,4}, new int[]{25,18,4},
            new int[]{8,19,4}, new int[]{25,19,4},
            new int[]{8,20,4}, new int[]{25,20,4},
            new int[]{8,21,5}, new int[]{24,21,5},
            new int[]{9,22,4}, new int[]{24,22,4},
            new int[]{9,23,5}, new int[]{23,23,5},
            new int[]{10,24,6}, new int[]{21,24,6},
            new int[]{11,25,15},
            new int[]{12,26,13},
            new int[]{13,27,11},
            new int[]{15,28,7}
    );

    public static List<int[]> getFuncPoint(int side) {
        switch (side) {
            case 21: return funcPoint21;
            case 25: return funcPoint25;
            case 29: return funcPoint29;
            case 33: return funcPoint33;
            case 37: return funcPoint37;
            default: return getFuncPointOther(side);
        }
    }


    public static List<int[]> getFuncPointOther(int side){
        int centerX = side/2;
        int centerY = side/2;
        int z;
        int d = (side - side/3*2)/2;
        int t = side/9;

        // 画个圆环
        ByteMatrix byteMatrix = new ByteMatrix(side, side);
        int [] sp = null;
        for (int y = 0; y < side; y++) {
            for (int x = 0; x < side; x++){
                z = (int)Math.sqrt(Math.pow(Math.abs(x-centerX),2) + Math.pow(Math.abs(y-centerY),2));
                if (z >= d && z <= d+t) {
                    byteMatrix.set(x,y,1);
                    if (sp == null){
                        sp = new int[]{x,y};
                    }
                }
            }
        }
        if (sp != null) { // 去掉突出的点，视觉上更圆
            byteMatrix.set(sp[0], sp[1], 0);
            byteMatrix.set(side-sp[0]-1, sp[1], 0);
            byteMatrix.set(sp[0], side-sp[1]-1, 0);
            byteMatrix.set(side-sp[0]-1, side-sp[1]-1, 0);
            byteMatrix.set(sp[1], sp[0], 0);
            byteMatrix.set(side-sp[1]-1, sp[0], 0);
            byteMatrix.set(sp[1], side-sp[0]-1, 0);
            byteMatrix.set(side-sp[1]-1, side-sp[0]-1, 0);
        }
        QRBtfUtil.print(byteMatrix);

        List<int[]> pList = new ArrayList<>(side*2);
        for (int y = 0; y < side; y++){
            int l = 0;
            for (int x = 0; x < side; x++) {
                if (byteMatrix.get(x, y) == 1) {
                    l++;
                    if (x+1 == side-1 || byteMatrix.get(x+1, y) != 1) {
                        pList.add(new int[]{x-l+1, y, l});
                        l = 0;
                    }
                }
            }
        }
        return pList;
    }
}
