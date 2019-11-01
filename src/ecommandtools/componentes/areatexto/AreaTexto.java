package ecommandtools.componentes.areatexto;

import ecommandtools.utils.Fonte;
import javax.swing.JTextArea;

public class AreaTexto extends JTextArea {

    private boolean obrigatorio;

    public AreaTexto() {
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
