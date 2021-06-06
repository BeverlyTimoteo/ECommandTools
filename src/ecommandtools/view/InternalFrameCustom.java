package ecommandtools.view;

import ecommandtools.exception.ExceptionConfirm;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.KeyStroke;
import javax.swing.RootPaneContainer;
import ecommandtools.utils.Fonte;
import ecommandtools.utils.Mensagens;
import org.openide.util.Exceptions;

public class InternalFrameCustom extends JInternalFrame {

    private final JFrameCustom mainForm;
    private RootPaneContainer root = null;

    public InternalFrameCustom(JFrameCustom formMain) throws Exception {
        initComponents();

        this.mainForm = formMain;

        formMain.getDesktopPane().add(this);

        obterEsc();

        setFont(Fonte.getFont());
    }

    public JFrameCustom getMainForm() {
        return mainForm;
    }

    public void setVisible() {
        try {
            this.setVisible(true);
            this.setSelected(true);

        } catch (PropertyVetoException ex) {
            Logger.getLogger(InternalFrameCustom.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sair() {
        this.dispose();
    }

    public void startWaitCursor() {
        root = (RootPaneContainer) this.getTopLevelAncestor();
        root.getGlassPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        root.getGlassPane().setVisible(true);
    }

    public void stopWaitCursor() {
        if (root != null) {
            root.getGlassPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            root.getGlassPane().setVisible(false);
        }
    }

    public void setCenterForm() throws Exception {
        Dimension desktopSize = mainForm.getDesktopPane().getSize();
        Dimension jInternalFrameSize = this.getSize();
        this.setLocation((desktopSize.width - jInternalFrameSize.width) / 2, (desktopSize.height - jInternalFrameSize.height) / 2);
    }

    private void obterEsc() {
        KeyStroke ks = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true);
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(ks, "esc");
        getRootPane().getActionMap().put("esc", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    Mensagens.confirmacao("Deseja sair desta página?", getTitle());
                    dispose();
                } catch (ExceptionConfirm ec) {
                } catch (Exception ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setMaximizable(true);
        setResizable(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 459, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 308, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
