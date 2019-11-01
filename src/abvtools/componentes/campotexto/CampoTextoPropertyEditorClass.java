package abvtools.componentes.campotexto;

import java.beans.PropertyEditorSupport;

public class CampoTextoPropertyEditorClass extends PropertyEditorSupport {

    @Override
    public String[] getTags() {
        String[] str = {
            TipoFormato.CNPJ.getDescricao(),
            TipoFormato.CPF.getDescricao(),
            TipoFormato.CEP.getDescricao(),
            TipoFormato.DECIMAL1.getDescricao(),
            TipoFormato.DECIMAL2.getDescricao(),
            TipoFormato.DECIMAL3.getDescricao(),
            TipoFormato.NUMERO.getDescricao(),
            TipoFormato.TEXTO.getDescricao(),
            TipoFormato.TELEFONE.getDescricao(),
            TipoFormato.CELULAR.getDescricao()
        };

        return str;
    }

    @Override
    public String getJavaInitializationString() {
        return "\"" + getValue() + "\"";
    }

}
