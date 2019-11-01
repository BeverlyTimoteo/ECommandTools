package ecommandtools.exception;

import java.util.Arrays;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import ecommandtools.view.ExibirDetalhesExcecao;

public class ExceptionMessage {

    public static void exibirMensagemException(Exception ex, String title) {
        if (!(ex instanceof ExceptionConfirm)) {
            if (ex instanceof ExceptionCustom) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), title, ((ExceptionCustom) ex).getIdType());

            } else {
                Object[] opcoes = {"Sair", "Detalhes >>"};

                JOptionPane optionPane = new JOptionPane("Exibir detalhes?", JOptionPane.ERROR_MESSAGE, JOptionPane.YES_NO_OPTION, null, opcoes, opcoes[1]);

                JDialog confirm = optionPane.createDialog(title);
                confirm.setVisible(true);

                Object valorselecionado = optionPane.getValue();

                if (valorselecionado != null && (Arrays.asList(opcoes).indexOf(valorselecionado)) == JOptionPane.NO_OPTION) {
                    String mensagem = ex.getMessage() + "\n\n";

                    for (StackTraceElement elemento : ex.getStackTrace()) {
                        mensagem += elemento.toString() + "\n";
                    }

                    if (ex.getStackTrace() != null) {
                        ExibirDetalhesExcecao dialog = new ExibirDetalhesExcecao(null, true);
                        dialog.setMensagem(mensagem);
                        dialog.setTitle(title);
                        dialog.setVisible(true);
                    }
                }
            }
        }
    }
}
