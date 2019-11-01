package ecommandtools.componentes.campotexto;

import ecommandtools.exception.ExceptionCustom;
import ecommandtools.exception.ExceptionMessage;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.DecimalFormat;
import java.util.HashSet;
import javax.swing.JFormattedTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.text.MaskFormatter;
import ecommandtools.utils.Fonte;
import ecommandtools.utils.Numeros;

public class CampoTexto extends JFormattedTextField {

    private String tipoformato;
    private boolean uppercase;
    private final CampoTextoPlainDocument plainDocument;
    private boolean enterIsTab;
    private final MaskFormatter formato;
    private String placeHolder;
    private boolean selectTextToEnter;
    private boolean permiteNegativo;
    private int columns;

    public CampoTexto() {
        this.tipoformato = "Texto";
        this.uppercase = true;
        this.formato = new MaskFormatter();
        this.placeHolder = "";
        this.selectTextToEnter = true;
        this.plainDocument = new CampoTextoPlainDocument();
        this.setDocument(plainDocument);

        setEnterIsTab(true);

        setPermiteNegativo(false);

        setDisabledTextColor(UIManager.getColor("TextField.foreground"));

        setFont(Fonte.getFont());

        setPreferredSize(new Dimension(getSize().width, 20));

        this.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent evt) {
                CampoTexto.this.editFocusGained(evt);
            }

            @Override
            public void focusLost(FocusEvent evt) {
                CampoTexto.this.editFocusLost(evt);
            }
        });
    }

    public boolean isSelectTextToEnter() {
        return this.selectTextToEnter;
    }

    public void setSelectTextToEnter(boolean selectTextEnter) {
        this.selectTextToEnter = selectTextEnter;
    }

    public boolean isPermiteNegativo() {
        return this.permiteNegativo;
    }

    public void setPermiteNegativo(boolean permiteNegativo) {
        this.permiteNegativo = permiteNegativo;
        this.plainDocument.setNegativo(permiteNegativo);
    }

    private void editFocusGained(FocusEvent evt) {
        if ((!this.placeHolder.isEmpty()) && (getText().equals(this.placeHolder))) {
            setText("");
            setForeground(UIManager.getColor("TextField.foreground"));
        }

        if (!getText().equals("") && (this.tipoformato.equals(TipoFormato.DECIMAL1.getDescricao())
                || this.tipoformato.equals(TipoFormato.DECIMAL2.getDescricao())
                || this.tipoformato.equals(TipoFormato.DECIMAL3.getDescricao()))) {
            setText(getText().replace(".", ""));
            this.plainDocument.setMaxCharacters(this.getColumns());
        }

        if (this.isEditable() && isSelectTextToEnter()) {
            Runnable doSelectText = () -> {
                CampoTexto.this.selectAll();
            };

            SwingUtilities.invokeLater(doSelectText);
        }

        if (!isEditable()) {
            selectAll();
        }
    }

    private void editFocusLost(FocusEvent evt) {
        try {
            if ((this.isPermiteNegativo()) && (getText().equals("-"))) {
                setText("");
            }

            if (this.tipoformato.equals(TipoFormato.DECIMAL1.getDescricao())) {
                if (!getText().equals("")) {
                    double valor = 0.0D;

                    if ((!this.getText().contains(".")) && (!this.getText().contains(","))) {
                        valor = Double.parseDouble(getText()) / 10.0D;

                    } else if ((!getText().equals(".")) && (!getText().equals(","))) {
                        valor = Double.parseDouble(getText().replace(",", "."));
                    }

                    this.plainDocument.setMaxCharacters(0);
                    setDouble(valor);
                }
            } else if (this.tipoformato.equals(TipoFormato.DECIMAL2.getDescricao())) {
                if (!getText().equals("")) {
                    double valor = 0.0D;

                    if ((!getText().contains(".")) && (!getText().contains(","))) {
                        valor = Double.parseDouble(getText()) / 100.0D;

                    } else if ((!getText().equals(".")) && (!getText().equals(","))) {
                        valor = Double.parseDouble(getText().replace(".", "").replace(",", "."));
                    }

                    this.plainDocument.setMaxCharacters(0);
                    setDouble(valor);
                }
            } else if (this.tipoformato.equals(TipoFormato.DECIMAL3.getDescricao())) {
                if (!getText().equals("")) {
                    double valor = 0.0D;

                    if ((!getText().contains(".")) && (!getText().contains(","))) {
                        valor = Double.parseDouble(getText()) / 1000.0D;

                    } else if ((!getText().equals(".")) && (!getText().equals(","))) {
                        valor = Double.parseDouble(getText().replace(",", "."));
                    }

                    this.plainDocument.setMaxCharacters(0);
                    setDouble(valor);
                }
            } else if (this.tipoformato.equals(TipoFormato.CEP.getDescricao())
                    && (!getTexto().equals("")) && (getTexto().length() != 8)) {
                throw new ExceptionCustom("CEP inválido!");

            } else if (this.tipoformato.equals(TipoFormato.TELEFONE.getDescricao())
                    && (!getTexto().equals("")) && (getTexto().length() != 10)) {
                throw new ExceptionCustom("Telefone inválido!");

            } else if (this.tipoformato.equals(TipoFormato.CELULAR.getDescricao())
                    && (!getTexto().equals("")) && (getTexto().length() != 11)) {
                throw new ExceptionCustom("Celular inválido!");
            }

            if ((!this.placeHolder.isEmpty()) && (this.getText().isEmpty())) {
                setText(this.placeHolder);
                setForeground(Color.gray);
            }

        } catch (Exception ex) {
            ExceptionMessage.exibirMensagemException(ex, "Atenção");

            Runnable doFocus = () -> {
                CampoTexto.this.requestFocus();
            };

            SwingUtilities.invokeLater(doFocus);
        }
    }

    public String getTipoformato() {
        return tipoformato;
    }

    public void setTipoformato(String tipoformato) {
        this.tipoformato = tipoformato;

        inserirFormato();
    }

    public boolean isUppercase() {
        return uppercase;
    }

    public void setUppercase(boolean upperCase) {
        this.uppercase = upperCase;
        this.plainDocument.setUpperCase(this.uppercase);
    }

    @Override
    public void setColumns(int columns) {
        this.columns = columns;
        plainDocument.setMaxCharacters(this.getColumns());
    }

    @Override
    public int getColumns() {
        return this.columns;
    }

    public void setInt(int valor) {
        setText(String.valueOf(valor));
    }

    public void setLong(long valor) {
        setText(String.valueOf(valor));
    }

    public String getTexto() throws Exception {
        if (getTipoformato().equals(TipoFormato.CPF.getDescricao())) {
            return getText().replace(" ", "").replace(".", "").replace("-", "");

        } else if (getTipoformato().equals(TipoFormato.CNPJ.getDescricao())) {
            return getText().replace(" ", "").replace(".", "").replace("/", "").replace("-", "");

        } else if (getTipoformato().equals(TipoFormato.CEP.getDescricao())) {
            return getText().replace(" ", "").replace("-", "");

        } else if (getTipoformato().equals(TipoFormato.TELEFONE.getDescricao())) {
            return getText().replace(" ", "").replace("(", "").replace(")", "").replace("-", "");

        } else if (getTipoformato().equals(TipoFormato.CELULAR.getDescricao())) {
            return getText().replace(" ", "").replace("(", "").replace(")", "").replace("-", "");
        }

        return getText();
    }

    public double getDouble() throws Exception {
        if (getTexto().equals("")) {
            return 0.0D;
        }

        if ((!getText().contains(".")) && (!getText().contains(","))) {
            double valor = 0.0D;

            if (getTipoformato().equals(TipoFormato.NUMERO.getDescricao())) {
                valor = Double.parseDouble(getText());

            } else if (getTipoformato().equals(TipoFormato.DECIMAL1.getDescricao())) {
                valor = Double.parseDouble(getText()) / 10.0D;

            } else if (getTipoformato().equals(TipoFormato.DECIMAL2.getDescricao())) {
                valor = Double.parseDouble(getText()) / 100.0D;

            } else if (getTipoformato().equals(TipoFormato.DECIMAL3.getDescricao())) {
                valor = Double.parseDouble(getText()) / 1000.0D;

            }

            return valor;
        }

        return Double.parseDouble(getText().replace(".", "").replace(",", "."));
    }

    public int getInt() throws Exception {
        if (getTexto().isEmpty()) {
            return -1;
        } else {
            return Integer.valueOf(getTexto());
        }
    }

    public long getLong() throws Exception {
        if (getTexto().isEmpty()) {
            return -1L;
        }
        return Long.parseLong(getTexto());
    }

    public void setDouble(double d) {
        if (getTipoformato().equals(TipoFormato.DECIMAL1.getDescricao())) {
            setText(new DecimalFormat("###,##0.0").format(Numeros.round(d, 1)));

        } else if (getTipoformato().equals(TipoFormato.DECIMAL2.getDescricao())) {
            setText(new DecimalFormat("###,##0.00").format(Numeros.round(d, 2)));

        } else if (getTipoformato().equals(TipoFormato.DECIMAL3.getDescricao())) {
            setText(new DecimalFormat("###,##0.000").format(Numeros.round(d, 3)));

        } else if (getTipoformato().equals(TipoFormato.NUMERO.getDescricao())) {
            setText(new DecimalFormat("###,##0").format(d));

        } else {
            setText(String.valueOf(d));
        }
    }

    private void inserirFormato() {
        try {
            this.formato.install(null);

            if (this.tipoformato.equals(TipoFormato.NUMERO.getDescricao())) {
                this.setHorizontalAlignment(LEADING);
                this.plainDocument.setTipoFormato(TipoFormato.NUMERO);

            } else if (this.tipoformato.equals(TipoFormato.DECIMAL1.getDescricao())) {
                this.setHorizontalAlignment(RIGHT);
                this.plainDocument.setTipoFormato(TipoFormato.DECIMAL1);

            } else if (this.tipoformato.equals(TipoFormato.DECIMAL2.getDescricao())) {
                this.setHorizontalAlignment(RIGHT);
                this.plainDocument.setTipoFormato(TipoFormato.DECIMAL2);

            } else if (this.tipoformato.equals(TipoFormato.DECIMAL3.getDescricao())) {
                this.setHorizontalAlignment(RIGHT);
                this.plainDocument.setTipoFormato(TipoFormato.DECIMAL3);

            } else if (getTipoformato().equals(TipoFormato.TEXTO.getDescricao())) {
                this.setHorizontalAlignment(LEADING);
                this.plainDocument.setTipoFormato(TipoFormato.TEXTO);

            } else if (getTipoformato().equals(TipoFormato.CNPJ.getDescricao())) {
                setHorizontalAlignment(LEADING);
                this.formato.setMask("##.###.###/####-##");
                this.formato.install(this);
                this.plainDocument.setTipoFormato(TipoFormato.CNPJ);

            } else if (getTipoformato().equals(TipoFormato.CPF.getDescricao())) {
                setHorizontalAlignment(LEADING);
                this.formato.setMask("###.###.###-##");
                this.formato.install(this);
                this.plainDocument.setTipoFormato(TipoFormato.CPF);

            } else if (getTipoformato().equals(TipoFormato.CEP.getDescricao())) {
                setHorizontalAlignment(LEADING);
                this.formato.setMask("#####-###");
                this.formato.install(this);
                this.plainDocument.setTipoFormato(TipoFormato.CEP);

            } else if (getTipoformato().equals(TipoFormato.TELEFONE.getDescricao())) {
                setHorizontalAlignment(LEADING);
                this.formato.setMask("(##) ####-####");
                this.formato.install(this);
                this.plainDocument.setTipoFormato(TipoFormato.TELEFONE);

            } else if (getTipoformato().equals(TipoFormato.CELULAR.getDescricao())) {
                setHorizontalAlignment(LEADING);
                this.formato.setMask("(##) #####-####");
                this.formato.install(this);
                this.plainDocument.setTipoFormato(TipoFormato.CELULAR);
            }

        } catch (Exception ex) {
            ExceptionMessage.exibirMensagemException(ex, "Atenção");
        }
    }

    public boolean isEnterIsTab() {
        return enterIsTab;
    }

    public void setEnterIsTab(boolean teclaEnter) {
        this.enterIsTab = teclaEnter;

        HashSet kTab = new HashSet(1);

        kTab.add(KeyStroke.getKeyStroke("TAB"));

        if (teclaEnter) {
            kTab.add(KeyStroke.getKeyStroke("ENTER"));
        }

        setFocusTraversalKeys(0, kTab);
    }

    public String getPlaceHolder() {
        return this.placeHolder;
    }

    public void setPlaceHolder(String i_placeholder) {
        this.placeHolder = i_placeholder;

        setText(i_placeholder);
        setForeground(Color.gray);
    }

}
