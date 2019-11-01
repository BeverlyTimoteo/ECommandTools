package ecommandtools.componentes.checkbox;

import ecommandtools.utils.Fonte;
import javax.swing.JCheckBox;

public class CheckBox extends JCheckBox {

    private boolean obrigatorio;

    public CheckBox() {
        initComponents();

        obrigatorio = false;
        setFont(Fonte.getFont());
    }

    public boolean isObrigatorio() {
        return obrigatorio;
    }

    public void setObrigatorio(boolean obrigatorio) {
        this.obrigatorio = obrigatorio;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
