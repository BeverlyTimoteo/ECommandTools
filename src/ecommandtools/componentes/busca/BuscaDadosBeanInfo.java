package ecommandtools.componentes.busca;

import java.awt.Image;
import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import static java.beans.BeanInfo.ICON_COLOR_16x16;
import static java.beans.BeanInfo.ICON_COLOR_32x32;
import static java.beans.BeanInfo.ICON_MONO_16x16;
import static java.beans.BeanInfo.ICON_MONO_32x32;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.SimpleBeanInfo;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BuscaDadosBeanInfo extends SimpleBeanInfo {

    private static BeanDescriptor bean;

    @Override
    public BeanDescriptor getBeanDescriptor() {
        bean = new BeanDescriptor(BuscaDados.class);
        bean.setDisplayName("Busca");
        return bean;
    }

    @Override
    public BeanInfo[] getAdditionalBeanInfo() {
        try {
            BeanInfo bi = Introspector.getBeanInfo(BuscaDados.class.getSuperclass());

            return new BeanInfo[]{bi};

        } catch (IntrospectionException ex) {
            Logger.getLogger(BuscaDadosBeanInfo.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public Image getIcon(int iconKind) {
        Image img = null;

        if (iconKind == ICON_COLOR_16x16 || iconKind == ICON_MONO_16x16) {
            img = loadImage("buscar16.png");
        } else if (iconKind == ICON_COLOR_32x32 || iconKind == ICON_MONO_32x32) {
            img = loadImage("button32.png");
        }

        return img;
    }

}
