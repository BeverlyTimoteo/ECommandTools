package ecommandtools.componentes.radiobotao;

import java.awt.Image;
import java.beans.BeanDescriptor;
import java.beans.SimpleBeanInfo;

public class RadioBotaoBeanInfo extends SimpleBeanInfo {

    private BeanDescriptor beanDescriptor = null;

    @Override
    public BeanDescriptor getBeanDescriptor() {
        beanDescriptor = new BeanDescriptor(RadioBotao.class);
        beanDescriptor.setName("Radio Botao");
        return beanDescriptor;
    }

    @Override
    public Image getIcon(int iconKind) {
        Image img = null;

        if (iconKind == ICON_COLOR_16x16 || iconKind == ICON_MONO_16x16) {
            img = loadImage("radiobotao16.png");
            
        } else if (iconKind == ICON_COLOR_32x32 || iconKind == ICON_MONO_32x32) {
            img = loadImage("radiobotao32.png");
        }

        return img;
    }

}
