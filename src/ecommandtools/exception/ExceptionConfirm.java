package ecommandtools.exception;

public class ExceptionConfirm extends RuntimeException {

    private String titulo;

    public ExceptionConfirm() {
        super("Operação cancelada!");
    }

    public ExceptionConfirm(String message, String titulo) {
        super(message);
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }

}
