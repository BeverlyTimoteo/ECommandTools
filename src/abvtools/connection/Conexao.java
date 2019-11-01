package abvtools.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexao {

    private static Connection conexao = null;
    private static String usuario = "";
    private static String senha = "";
    private static String url = "";
    private static int contadorTransacao = 0;
    private static int tentativasConexao = 0;

    public static void conectar(String user, String password, String url) throws Exception {
        Conexao.setUsuario(user);
        Conexao.setSenha(password);
        Conexao.setUrl(url);

        Class.forName("org.postgresql.Driver");

        conexao = DriverManager.getConnection("jdbc:postgresql://" + getUrl(), getUsuario(), getSenha());

        conexao.setAutoCommit(false);
    }

    public static void fecharConexao() throws Exception {
        conexao.close();
    }

    public static Statement createStatement() throws Exception {
        testarConexao();

        return conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
    }

    public static void testarConexao() throws Exception {
        try {
            Statement s = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            s.setQueryTimeout(5);
            s.executeQuery("select 1 as teste;");

            tentativasConexao = 0;

        } catch (SQLException e) {
            tentativasConexao += 1;

            Conexao.conectar(Conexao.usuario, Conexao.senha, Conexao.url);

            if (tentativasConexao < 5) {
                Thread.sleep(200);

                testarConexao();
            }
        }
    }

    public static void rollback() throws Exception {
        createStatement().execute("rollback;");

        contadorTransacao = 0;
    }

    public static void begin() throws Exception {
        if (contadorTransacao == 0) {
            createStatement().execute("begin;");
        }

        contadorTransacao++;
    }

    public static void commit() throws Exception {
        contadorTransacao--;

        if (contadorTransacao == 0) {
            createStatement().execute("commit;");
        }
    }

    public static Connection getConexao() {
        return conexao;
    }

    public static String getUsuario() {
        return usuario;
    }

    public static String getSenha() {
        return senha;
    }

    public static String getUrl() {
        return url;
    }

    public static void setUsuario(String usuario) {
        Conexao.usuario = usuario;
    }

    public static void setSenha(String senha) {
        Conexao.senha = senha;
    }

    public static void setUrl(String url) {
        Conexao.url = url;
    }

}
