package ecommandtools.view;

import ecommandtools.utils.Fonte;
import static java.awt.Frame.MAXIMIZED_BOTH;
import javax.swing.DefaultDesktopManager;
import javax.swing.JDesktopPane;

public class JFrameCustom extends javax.swing.JFrame {

    private JDesktopPane desktopPane;

    public JFrameCustom() {
        initComponents();

        setFont(Fonte.getFont());
    }

    public JDesktopPane getDesktopPane() {
        return desktopPane;
    }

    public void setDesktopPane(JDesktopPane desktopPane) {
        this.desktopPane = desktopPane;

        desktopPane.setDesktopManager(new DefaultDesktopManager());
    }

    public void setEstadoForm() {
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setSize(800, 600);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
