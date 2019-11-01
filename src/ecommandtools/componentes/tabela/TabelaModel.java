package ecommandtools.componentes.tabela;

import ecommandtools.model.ColunaTabelaVO;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class TabelaModel extends AbstractTableModel {

    private Object[][] linhas;
    private List<ColunaTabelaVO> colunas;

    public void setLinhas(Object[][] linhas) {
        this.linhas = linhas;
    }

    public void setColunas(List<ColunaTabelaVO> colunas) {
        this.colunas = colunas;
    }

    public List<ColunaTabelaVO> getColunas() {
        return colunas;
    }

    @Override
    public int getRowCount() {
        return linhas.length;
    }

    @Override
    public int getColumnCount() {
        return colunas.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return linhas[rowIndex][columnIndex];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        linhas[rowIndex][columnIndex] = aValue;
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        for (ColunaTabelaVO coluna : colunas) {
            if (colunas.indexOf(coluna) == columnIndex) {
                return coluna.isEditavel();
            }
        }

        return false;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }

}
