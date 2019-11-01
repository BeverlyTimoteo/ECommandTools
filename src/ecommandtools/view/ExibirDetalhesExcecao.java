package ecommandtools.view;

import javax.swing.JDialog;

public class ExibirDetalhesExcecao extends JDialog {

    public ExibirDetalhesExcecao(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        posicionarDialogCenter();
    }

    private void posicionarDialogCenter() {
        this.setLocationRelativeTo(null);
    }

    public void setMensagem(String mensagem) {
        txtMsg.setText(mensagem);
        txtMsg.requestFocus();
        txtMsg.setCaretPosition(0);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnFundo = new javax.swing.JPanel();
        btnSair = new javax.swing.JButton();
        jScrollPaneTexto = new javax.swing.JScrollPane();
        txtMsg = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/abvtools/image/exit.png"))); // NOI18N
        btnSair.setText("Sair");
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

        txtMsg.setEditable(false);
        txtMsg.setColumns(20);
        txtMsg.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        txtMsg.setRows(5);
        jScrollPaneTexto.setViewportView(txtMsg);

        javax.swing.GroupLayout pnFundoLayout = new javax.swing.GroupLayout(pnFundo);
        pnFundo.setLayout(pnFundoLayout);
        pnFundoLayout.setHorizontalGroup(
            pnFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnFundoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnFundoLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSair))
                    .addComponent(jScrollPaneTexto, javax.swing.GroupLayout.DEFAULT_SIZE, 542, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnFundoLayout.setVerticalGroup(
            pnFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnFundoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPaneTexto, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSair)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnFundo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnFundo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnSairActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSair;
    private javax.swing.JScrollPane jScrollPaneTexto;
    private javax.swing.JPanel pnFundo;
    private javax.swing.JTextArea txtMsg;
    // End of variables declaration//GEN-END:variables

}
