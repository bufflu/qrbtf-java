package cc.wo66.qrbtf.draw;

import cc.wo66.qrbtf.Parameters;
import cc.wo66.qrbtf.QRBtfUtil;
import org.apache.commons.lang3.StringUtils;

import java.awt.image.BufferedImage;

public class BackgroundDraw {

    public static void draw(BufferedImage image, Parameters parameters){
        if (StringUtils.isNotBlank(parameters.getBackgroundImageBase64())) {


        } else if (parameters.getBackgroundColor() != null) {
            QRBtfUtil.fillBackGroundColor(image, parameters.getBackgroundColor());
        }
    }
}
