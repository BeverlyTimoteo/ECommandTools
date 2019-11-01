package abvtools.componentes.campotexto;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import static abvtools.utils.Texto.removerCaracteresInvalidos;

public class CampoTextoPlainDocument extends PlainDocument {

    private int maxCharacters;
    private boolean uppercase;
    private boolean permiteNegativo;
    private TipoFormato tipo;
    private int quantidadeCasasDecimais;
    private final String NUMERIC = "0123456789";
    private final String FLOAT = "0123456789,.";
    private String caracteresUtilizados;

    public CampoTextoPlainDocument() {
        super();
        this.maxCharacters = 0;
        this.uppercase = true;
        this.permiteNegativo = false;
        this.tipo = TipoFormato.TEXTO;
        this.caracteresUtilizados = NUMERIC;
    }

    public void setMaxCharacters(int maxCharacters) {
        this.maxCharacters = maxCharacters;
    }

    public void setUpperCase(boolean uppercase) {
        this.uppercase = uppercase;
    }

    public void setNegativo(boolean negativo) {
        this.permiteNegativo = negativo;
    }

    public void setTipoFormato(TipoFormato tipo) {
        this.tipo = tipo;

        verificaTipoFormato();
    }

    private void verificaTipoFormato() {
        switch (TipoFormato.buscarIdPorTipo(this.tipo)) {
            case 2:
                quantidadeCasasDecimais = 0;
                break;
            case 3:
                quantidadeCasasDecimais = 1;
                break;
            case 4:
                quantidadeCasasDecimais = 2;
                break;

            case 5:
                quantidadeCasasDecimais = 3;
                break;

            default:
                quantidadeCasasDecimais = -1;
        }

        if (quantidadeCasasDecimais > 0) {
            caracteresUtilizados = FLOAT;
        }
    }

    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        if ((str == null) || (str.equals(""))) {
            return;
        }

        verificaTipoFormato();

        String valor = (uppercase ? str.toUpperCase() : str);

        try {
            valor = removerCaracteresInvalidos(valor);

        } catch (Exception ex) {
            Logger.getLogger(CampoTextoPlainDocument.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (quantidadeCasasDecimais >= 0) {
            for (int i = 0; i < valor.length(); i++) {
                if ((!this.caracteresUtilizados.contains(String.valueOf(valor.charAt(i)))) && (!this.permiteNegativo || (valor.charAt(i) != '-'))) {
                    return;
                }
            }

            if ((getText(0, getLength()).contains(".")) || (getText(0, getLength()).contains(","))) {
                if ((valor.contains(".")) || (valor.contains(","))) {
                    return;
                }

                if ((getText(0, getLength()).contains(".") && (getLength() - (quantidadeCasasDecimais + 1) == getText(0, getLength()).indexOf(".")))
                        || (getText(0, getLength()).contains(",") && (getLength() - (quantidadeCasasDecimais + 1) == getText(0, getLength()).indexOf(",")))) {
                    return;
                }
            }
        }

        if (this.permiteNegativo && (valor.contains("-")) && ((valor.indexOf("-") != 0) || (offs != 0))) {
            return;
        }

        if ((maxCharacters == 0) || ((getLength() + str.length()) <= maxCharacters)) {
            super.insertString(offs, valor, a);

        } else {
            valor = valor.substring(0, maxCharacters - getLength());
            super.insertString(offs, valor, a);
        }
    }

}
