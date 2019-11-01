package abvtools.utils;

import abvtools.exception.ExceptionConfirm;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class Mensagens {

    public static String MSG_SALVO_COM_SUCESSO = "Informações salvas com sucesso!";
    public static String MSG_REMOVIDO_COM_SUCESSO = "Informações removidas com sucesso!";
    public static String MSG_SELECIONAR_ITENS_TABELA = "Selecione um registro!";
    public static String MSG_REGISTRO_NAO_ENCONTRADO = "Registro não encontrado!";

    public static void MensagemInfo(String msg) {
        JOptionPane.showMessageDialog(null, msg, "Atenção", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void MensagemErro(String msg) {
        JOptionPane.showMessageDialog(null, msg, "Atenção", JOptionPane.ERROR_MESSAGE);
    }

    public static void Confirmacao(String msg, String titulo) throws Exception {
        JButton botaosim = new JButton("Sim");
        JButton botaonao = new JButton("Não");
        Object[] botoes = {botaosim, botaonao};

        JOptionPane opcao = new JOptionPane(msg, JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION, null, botoes, botoes[1]);

        botaosim.addActionListener((ActionEvent e) -> {
            opcao.setValue(JOptionPane.YES_OPTION);
        });

        botaosim.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT) {
                    botaosim.transferFocus();
                }
            }
        });

        botaonao.addActionListener((ActionEvent e) -> {
            opcao.setValue(JOptionPane.NO_OPTION);
        });

        botaonao.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT) {
                    botaonao.transferFocus();
                }
            }
        });

        JDialog dialogo = opcao.createDialog(titulo);

        dialogo.setVisible(true);

        Object valorselecionado = opcao.getValue();

        if (valorselecionado == null || ((Integer) (valorselecionado)) == JOptionPane.NO_OPTION) {
            throw new ExceptionConfirm();
        }
    }

}
