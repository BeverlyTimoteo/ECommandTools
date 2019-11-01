package ecommandtools.model;

public class ColunaTabelaVO {

    private final String titulo;
    private final int alinhamento;
    private final boolean redimensionar;
    private final boolean editavel;

    public ColunaTabelaVO(String titulo, int alinhamento, boolean redimensionar, boolean editavel) {
        this.titulo = titulo;
        this.redimensionar = redimensionar;
        this.alinhamento = alinhamento;
        this.editavel = editavel;
    }

    public String getTitulo() {
        return titulo;
    }

    public boolean isRedimensionar() {
        return redimensionar;
    }

    public int getAlinhamento() {
        return alinhamento;
    }

    public boolean isEditavel() {
        return editavel;
    }

}
