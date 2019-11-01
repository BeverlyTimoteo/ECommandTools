package ecommandtools.componentes.busca;

import ecommandtools.componentes.botao.Botao;
import ecommandtools.componentes.campotexto.CampoTexto;
import ecommandtools.componentes.campotexto.TipoFormato;
import ecommandtools.connection.Conexao;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.event.EventListenerList;

public class BuscaDados extends Container {

    private EventListenerList listenerList;
    private BuscaDadosEvento eventoBusca;
    private CampoTexto txtCodigo;
    private CampoTexto txtDescricao;
    private Botao btnBusca;
    public int columns;
    private String tabela;
    private int id;
    private String fieldId;
    private String fieldCodigoExibicao;
    private String fieldDescricao;
    private String filtro;

    public BuscaDados() {
        iniciarComponentes();
    }

    private void iniciarComponentes() {
        listenerList = new EventListenerList();
        eventoBusca = null;
        fieldId = "id";
        fieldDescricao = "descricao";
        filtro = "";
        fieldCodigoExibicao = "";

        this.setLayout(new FlowLayout(FlowLayout.LEFT, 1, 1));

        txtCodigo = new CampoTexto();
        txtCodigo.setTipoformato(TipoFormato.NUMERO.getDescricao());
        txtCodigo.setPreferredSize(new Dimension(70, 20));

        btnBusca = new Botao();
        btnBusca.setToolTipText("Abrir Busca");
        btnBusca.setFocusable(false);
        btnBusca.setOpaque(false);
        btnBusca.setPreferredSize(new Dimension(22, 20));
        btnBusca.setIcon(new ImageIcon(getClass().getResource("/componentes/busca/buscar16.png")));

        txtDescricao = new CampoTexto();
        txtDescricao.setPreferredSize(new Dimension(250, 20));
        txtDescricao.setEditable(false);
        txtDescricao.setFocusable(false);
        txtDescricao.setBackground(UIManager.getColor("TextField.disabledBackground"));

        this.add(txtCodigo);
        this.add(btnBusca);
        this.add(txtDescricao);

        setColumns(6);

        this.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                txtCodigo.requestFocus();
            }

            @Override
            public void focusLost(FocusEvent e) {
                try {
                    buscar(true);

                } catch (Exception ex) {
                    Logger.getLogger(BuscaDados.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        txtCodigo.addFocusListener(new FocusAdapter() {

            @Override
            public void focusLost(FocusEvent e) {
                try {
                    if (txtCodigo.getText().trim().isEmpty()) {
                        limpar();

                    } else {
                        setId(txtCodigo.getInt());
                        buscar(true);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(BuscaDados.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        btnBusca.addActionListener((ActionEvent e) -> {
            fireEventoAbrirConsulta();
        });
    }

    public void addBuscaDadosListener(BuscaDadosListener listener) {
        this.listenerList.add(BuscaDadosListener.class, listener);
    }

    public void removeBuscaDadosListener(BuscaDadosListener listener) {
        this.listenerList.remove(BuscaDadosListener.class, listener);
    }

    private void fireEventoAbrirConsulta() {
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == BuscaDadosListener.class) {
                if (eventoBusca == null) {
                    eventoBusca = new BuscaDadosEvento(this);
                }
                ((BuscaDadosListener) listeners[i + 1]).abrirConsulta(eventoBusca);
            }
        }
    }

    private void fireEventoFinalizarConsulta() {
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == BuscaDadosListener.class) {
                if (eventoBusca == null) {
                    eventoBusca = new BuscaDadosEvento(this);
                }

                ((BuscaDadosListener) listeners[i + 1]).finalizarConsulta(eventoBusca);
            }
        }
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
        txtCodigo.setColumns(columns);
    }

    public void setTabela(String tabela) {
        this.tabela = tabela;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void buscar() throws Exception {
        buscar(false);
    }

    private void buscar(boolean executarEventoFinalizar) throws Exception {
        Statement stm = Conexao.createStatement();

        StringBuilder sql = new StringBuilder("SELECT " + fieldId + ", " + fieldDescricao);

        if (!fieldCodigoExibicao.isEmpty()) {
            sql.append(", ").append(fieldCodigoExibicao);
        }

        sql.append(" FROM ").append(tabela);
        sql.append(" WHERE ").append(fieldId).append(" = ").append(id);

        if (!filtro.isEmpty()) {
            sql.append(" ").append(filtro);
        }

        ResultSet rst = stm.executeQuery(sql.toString());

        if (rst.next()) {
            if (!fieldCodigoExibicao.isEmpty()) {
                txtCodigo.setInt(rst.getInt(fieldCodigoExibicao));
            } else {
                txtCodigo.setInt(rst.getInt(fieldId));
            }

            txtDescricao.setText(rst.getString(fieldDescricao));
        } else {
            limpar();
        }

        if (executarEventoFinalizar) {
            fireEventoFinalizarConsulta();
        }
    }

    public void limpar() {
        txtCodigo.setText("");
        txtDescricao.setText("");
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public void setFieldDescricao(String fieldDescricao) {
        this.fieldDescricao = fieldDescricao;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    @Override
    public void setEnabled(boolean b) {
        super.setEnabled(b);
        txtCodigo.setEnabled(b);
        btnBusca.setEnabled(b);
        txtDescricao.setEnabled(b);
    }

    public void setFieldCodigoExibicao(String fieldCodigoExibicao) {
        this.fieldCodigoExibicao = fieldCodigoExibicao;
    }

}
