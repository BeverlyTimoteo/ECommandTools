package abvtools.componentes.botao;

import java.awt.Image;
import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BotaoBeanInfo extends SimpleBeanInfo {

    private BeanDescriptor beanDescriptor = null;

    @Override
    public BeanDescriptor getBeanDescriptor() {
        beanDescriptor = new BeanDescriptor(Botao.class);
        beanDescriptor.setName("ABV Botao");
        return beanDescriptor;
    }

    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            PropertyDescriptor pd = new PropertyDescriptor("highlightForeground", Botao.class, "getNovaForeground", "setNovaForeground");

            return new PropertyDescriptor[]{pd};

        } catch (IntrospectionException ex) {
            Logger.getLogger(BotaoBeanInfo.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public BeanInfo[] getAdditionalBeanInfo() {
        try {
            BeanInfo bi = Introspector.getBeanInfo(Botao.class.getSuperclass());

            return new BeanInfo[]{bi};

        } catch (IntrospectionException ex) {
            Logger.getLogger(BotaoBeanInfo.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public MethodDescriptor[] getMethodDescriptors() {
        try {
            BeanInfo bi = Introspector.getBeanInfo(beanDescriptor.getBeanClass().getSuperclass());

            return bi.getMethodDescriptors();
        } catch (IntrospectionException ex) {
            Logger.getLogger(BotaoBeanInfo.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public Image getIcon(int iconKind) {
        Image img = null;

        if (iconKind == ICON_COLOR_16x16 || iconKind == ICON_MONO_16x16) {
            img = loadImage("button16.png");
        } else if (iconKind == ICON_COLOR_32x32 || iconKind == ICON_MONO_32x32) {
            img = loadImage("button32.png");
        }

        return img;
    }

}
