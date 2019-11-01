package abvtools.componentes.combo;

import com.sun.java.swing.plaf.windows.WindowsBorders;
import abvtools.connection.Conexao;
import java.awt.Component;
import java.awt.Dimension;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import abvtools.utils.Fonte;
import abvtools.model.ItemListaComboVO;

public class Combo extends JComboBox {

    private boolean enterIsTab;
    private String tabela;
    private String campoDescricao;

    public Combo() {
        iniciar();

        tabela = "";
        campoDescricao = "descricao";
        enterIsTab = true;

        setEnterIsTab();

        setRenderer(new MyRendererComboBox());
    }

    private void iniciar() {
        setPreferredSize(new Dimension(29, 20));
        setFont(Fonte.getFont());
    }

    public void carregar() throws Exception {
        try (Statement stm = Conexao.createStatement();
                ResultSet rst = stm.executeQuery("select id, " + campoDescricao + " from " + tabela + " order by id")) {

            while (rst.next()) {
                addItem(new ItemListaComboVO(rst.getInt("id"), rst.getString(campoDescricao)));
            }
        }
    }

    public void setTabela(String tabela) {
        this.tabela = tabela;
    }

    public void setDescricao(String descricao) {
        this.campoDescricao = descricao;
    }

    public void addItemLista(ItemListaComboVO item) {
        addItem(item);
    }

    public int getId() {
        if (getSelectedIndex() >= 0) {
            return ((ItemListaComboVO) getSelectedItem()).getId();
        }

        return -1;
    }

    public void setEnterIsTab(boolean enterIsTab) {
        this.enterIsTab = enterIsTab;
    }

    private void setEnterIsTab() {
        HashSet kTab = new HashSet(1);

        kTab.add(KeyStroke.getKeyStroke("TAB"));

        if (enterIsTab) {
            kTab.add(KeyStroke.getKeyStroke("ENTER"));
        }

        setFocusTraversalKeys(0, kTab);
    }

    public void setId(int id) {
        boolean encontrou = false;

        for (int i = 0; i < getItemCount(); i++) {
            if (((ItemListaComboVO) getItemAt(i)).getId() == id) {
                setSelectedIndex(i);
                encontrou = true;
                break;
            }
        }

        if (!encontrou) {
            setSelectedIndex(-1);
        }
    }

    class MyRendererComboBox extends DefaultListCellRenderer {

        private Object borda = null;
        private final Border bordaVazia = new EmptyBorder(0, 0, 0, 0);

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Component renderComponent = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if ((renderComponent instanceof JComponent)) {
                JComponent componente = (JComponent) renderComponent;

                if ((index == -1) && (isSelected)) {
                    Border border = componente.getBorder();
                    Border dashedBorder = new WindowsBorders.DashedBorder(list.getForeground());
                    componente.setBorder(dashedBorder);

                    if (this.borda == null) {
                        this.borda = (border == null ? this.bordaVazia : border);
                    }

                } else if ((componente.getBorder() instanceof WindowsBorders.DashedBorder)) {
                    if ((this.borda instanceof Border)) {
                        componente.setBorder(this.borda == this.bordaVazia ? null : (Border) this.borda);
                    }
                    this.borda = null;
                }

                if (index == -1) {
                    renderComponent.setForeground(list.getForeground());
                }
            }

            if (value == null) {
                return renderComponent;
            }

            if (isSelected) {
                renderComponent.setBackground(UIManager.getColor("ComboBox.selectionBackground"));
                renderComponent.setForeground(UIManager.getColor("ComboBox.selectionForeground"));

            } else if (isEnabled()) {
                renderComponent.setBackground(UIManager.getColor("ComboBox.background"));

            } else {
                renderComponent.setBackground(UIManager.getColor("ComboBox.disabledBackground"));
            }

            if (!isSelected) {
                renderComponent.setForeground(UIManager.getColor("ComboBox.foreground"));
            }

            return renderComponent;
        }
    }
}
