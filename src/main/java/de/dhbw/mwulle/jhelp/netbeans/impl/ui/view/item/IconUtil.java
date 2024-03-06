package de.dhbw.mwulle.jhelp.netbeans.impl.ui.view.item;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.beans.BeanInfo;

public class IconUtil {

    public static Image convertImage(int type, BufferedImage image) {
        if (type == BeanInfo.ICON_MONO_16x16 || type == BeanInfo.ICON_MONO_32x32) {
            ColorConvertOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
            image = op.filter(image, null);
        }

        if (type == BeanInfo.ICON_MONO_16x16 || type == BeanInfo.ICON_COLOR_16x16) {
            return resizeImage(16, image);
        }

        if (type == BeanInfo.ICON_MONO_32x32 || type == BeanInfo.ICON_COLOR_32x32) {
            return resizeImage(32, image);
        }

        return image;
    }

    public static Image resizeImage(int size, Image image) {
        return image.getScaledInstance(size, size, Image.SCALE_DEFAULT);
    }
}
