package ecommandtools.componentes.tabela;

import java.awt.Image;
import java.beans.BeanDescriptor;
import static java.beans.BeanInfo.ICON_COLOR_16x16;
import static java.beans.BeanInfo.ICON_COLOR_32x32;
import static java.beans.BeanInfo.ICON_MONO_16x16;
import static java.beans.BeanInfo.ICON_MONO_32x32;
import java.beans.SimpleBeanInfo;

public class TabelaBeanInfo extends SimpleBeanInfo {

    private BeanDescriptor beanDescriptor = null;

    @Override
    public BeanDescriptor getBeanDescriptor() {
        beanDescriptor = new BeanDescriptor(Tabela.class);
        beanDescriptor.setName("ABV Tabela");

        return beanDescriptor;
    }

    @Override
    public Image getIcon(int iconKind) {
        Image img = null;

        if (iconKind == ICON_COLOR_16x16 || iconKind == ICON_MONO_16x16) {
            img = loadImage("tabela16.png");

        } else if (iconKind == ICON_COLOR_32x32 || iconKind == ICON_MONO_32x32) {
            img = loadImage("tabela32.png");
        }

        return img;
    }

}
