package abvtools.componentes.tabela;

import abvtools.componentes.campotexto.CampoTexto;
import abvtools.model.ColunaTabelaVO;
import abvtools.utils.Fonte;
import java.awt.FontMetrics;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

public class Tabela extends JTable {

    private final TabelaModel model;

    public Tabela() {
        initComponents();
        model = new TabelaModel();
        setAutoCreateColumnsFromModel(false);
        setAutoResizeMode(AUTO_RESIZE_OFF);

        setFont(Fonte.getFont());
        
        setFocusable(false);
    }

    public void setColunas(List<ColunaTabelaVO> colunas) {
        model.setColunas(colunas);
    }

    public List<ColunaTabelaVO> getColunas() {
        return model.getColunas();
    }

    public void setModel(Object[][] dados) throws Exception {
        preencherTabela(dados);
    }

    private void preencherTabela(Object[][] dados) throws Exception {
        boolean existeCampoTexto = false;
        model.setLinhas(dados);

        while (this.getColumnModel().getColumns().hasMoreElements()) {
            this.removeColumn(this.getColumnModel().getColumns().nextElement());
        }

        FontMetrics metrica = this.getFontMetrics(getFont());

        List<ColunaTabelaVO> colunasTabela = getColunas();

        for (ColunaTabelaVO coluna : colunasTabela) {
            TableColumn col = new TableColumn();
            col.setHeaderValue(coluna.getTitulo());
            col.setPreferredWidth(metrica.stringWidth(coluna.getTitulo()) + 20);
            col.setResizable(coluna.isRedimensionar());

            int index = getColunas().indexOf(coluna);
            col.setModelIndex(index);

            col.setMinWidth(0);

            int tamanhoColuna = col.getPreferredWidth();

            for (int i = 0; i < dados.length; i++) {
                Object dado = dados[i][index];

                if (dado instanceof String) {
                    int novoTamanho = metrica.stringWidth((String) dado) + 20;

                    if (novoTamanho > tamanhoColuna) {
                        tamanhoColuna = novoTamanho;
                    }
                } else if (dado instanceof CampoTexto) {
                    tamanhoColuna = 150;
                    existeCampoTexto = true;
                }
            }

            if (tamanhoColuna > col.getPreferredWidth()) {
                col.setPreferredWidth(tamanhoColuna);
            }

            addColumn(col);
        }

        setModel(model);

        if (existeCampoTexto) {
            setRowHeight(20);
        }

        setDefaultRenderer(Object.class, new TabelaRenderer());
        setDefaultRenderer(String.class, new TabelaRenderer());
        setDefaultRenderer(CampoTexto.class, new TabelaRenderer());
        setDefaultRenderer(Boolean.class, new TabelaRenderer());
    }

    public int getLinhaSelecionada() throws Exception {
        return this.convertRowIndexToModel(this.getSelectedRow());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
