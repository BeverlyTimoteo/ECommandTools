package abvtools.model;

public class ItemListaComboVO {

    private int id = -1;
    private String descricao = "";
    private boolean selecionado = false;

    public ItemListaComboVO() {
    }

    public ItemListaComboVO(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public ItemListaComboVO(int id, String descricao, boolean selecionado) {
        this.id = id;
        this.descricao = descricao;
        this.selecionado = selecionado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isSelecionado() {
        return selecionado;
    }

    public void setSelecionado(boolean selecionado) {
        this.selecionado = selecionado;
    }

    @Override
    public String toString() {
        return descricao;
    }

}
