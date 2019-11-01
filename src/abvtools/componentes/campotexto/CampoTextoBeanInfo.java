package abvtools.componentes.campotexto;

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

public class CampoTextoBeanInfo extends SimpleBeanInfo {

    private BeanDescriptor beanDescriptor = null;

    @Override
    public BeanDescriptor getBeanDescriptor() {
        beanDescriptor = new BeanDescriptor(CampoTexto.class);
        beanDescriptor.setName("ABV TextField");
        return beanDescriptor;
    }

    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            PropertyDescriptor pd = new PropertyDescriptor("tipoformato", CampoTexto.class);
            PropertyDescriptor pdUpper = new PropertyDescriptor("uppercase", CampoTexto.class);
            PropertyDescriptor pbEnterIsTab = new PropertyDescriptor("enterIsTab", CampoTexto.class);
            PropertyDescriptor pdPlaceHolder = new PropertyDescriptor("placeHolder", CampoTexto.class);
            PropertyDescriptor pdSelecionarTextoEnter = new PropertyDescriptor("selectTextToEnter", CampoTexto.class);
            PropertyDescriptor pdPermiteNegativo = new PropertyDescriptor("permiteNegativo", CampoTexto.class);
            PropertyDescriptor pdColumns = new PropertyDescriptor("columns", CampoTexto.class);

            pd.setPropertyEditorClass(CampoTextoPropertyEditorClass.class);

            return new PropertyDescriptor[]{pd, pdUpper, pbEnterIsTab, pdPlaceHolder, pdSelecionarTextoEnter, pdPermiteNegativo, pdColumns};

        } catch (IntrospectionException ex) {
            Logger.getLogger(CampoTextoBeanInfo.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public BeanInfo[] getAdditionalBeanInfo() {
        try {
            BeanInfo bi = Introspector.getBeanInfo(CampoTexto.class.getSuperclass());

            return new BeanInfo[]{bi};

        } catch (IntrospectionException ex) {
            Logger.getLogger(CampoTextoBeanInfo.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public MethodDescriptor[] getMethodDescriptors() {
        try {
            BeanInfo bi = Introspector.getBeanInfo(beanDescriptor.getBeanClass().getSuperclass());

            return bi.getMethodDescriptors();

        } catch (IntrospectionException ex) {
            Logger.getLogger(CampoTextoBeanInfo.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public Image getIcon(int iconKind) {
        Image img = null;

        if (iconKind == ICON_COLOR_16x16 || iconKind == ICON_MONO_16x16) {
            img = loadImage("textfield16.png");

        } else if (iconKind == ICON_COLOR_32x32 || iconKind == ICON_MONO_32x32) {
            img = loadImage("textfield32.png");

        }

        return img;
    }

}
