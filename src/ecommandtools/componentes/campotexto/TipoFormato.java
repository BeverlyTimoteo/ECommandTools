package ecommandtools.componentes.campotexto;

public enum TipoFormato {

    TEXTO(1, "Texto"),
    NUMERO(2, "Numero"),
    DECIMAL1(3, "Decimal1"),
    DECIMAL2(4, "Decimal2"),
    DECIMAL3(5, "Decimal3"),
    CPF(6, "CPF"),
    CNPJ(7, "CNPJ"),
    CEP(8, "CEP"),
    TELEFONE(9, "Telefone"),
    CELULAR(10, "Celular");

    private final int id;
    private final String descricao;

    private TipoFormato(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public static int buscarIdPorTipo(TipoFormato tipo) {
        for (TipoFormato oTipo : TipoFormato.values()) {
            if (oTipo == tipo) {
                return oTipo.id;
            }
        }

        return -1;
    }

}
