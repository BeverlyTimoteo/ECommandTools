package abvtools.componentes.tabela;

import abvtools.componentes.campotexto.CampoTexto;
import abvtools.componentes.checkbox.CheckBox;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;

public class TabelaRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (value instanceof CampoTexto) {
            CampoTexto edit = (CampoTexto) value;
            edit.setOpaque(true);

            comp = edit;

        } else if (value instanceof Boolean) {
            CheckBox checkbox = new CheckBox();
            checkbox.setSelected((Boolean) value);
            checkbox.setHorizontalAlignment(((Tabela) table).getColunas().get(column).getAlinhamento());

            checkbox.setOpaque(true);

            comp = checkbox;

        } else if (table instanceof Tabela) {
            setHorizontalAlignment(((Tabela) table).getColunas().get(column).getAlinhamento());
        }

        if (isSelected) {
            comp.setBackground(UIManager.getColor("Table.selectionBackground"));
            comp.setForeground(UIManager.getColor("Table.selectionForeground"));

        } else if (row % 2 == 0) {
            comp.setBackground(UIManager.getColor("Table.background"));
            comp.setForeground(UIManager.getColor("Table.foreground"));

        } else {
            comp.setBackground(new Color(192, 192, 192));
            comp.setForeground(UIManager.getColor("Table.foreground"));
        }

        return comp;
    }

}
