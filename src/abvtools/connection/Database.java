package abvtools.connection;

import java.sql.PreparedStatement;

public class Database {

    public static void execute(Sql sql) throws Exception {
        execute(sql.getSql());
    }

    public static void execute(String sql, Object... parametros) throws Exception {
        try (PreparedStatement stm = Conexao.getConexao().prepareStatement(sql)) {
            applyParameters(parametros, stm);

            stm.execute();
        }
    }

    private static void applyParameters(Object[] parametros, final PreparedStatement stm) throws Exception {
        if (parametros != null) {
            for (int i = 0; i < parametros.length; i++) {
                stm.setObject(i + 1, parametros[i]);
            }
        }
    }

    public static PreparedStatement getStatement(String sql, Object... parametros) throws Exception {
        PreparedStatement stm = Conexao.getConexao().prepareStatement(sql);

        applyParameters(parametros, stm);

        return stm;
    }

    public static boolean isCampo(String campo, String tabela) throws Exception {
        return isCampo(campo, tabela, "public");
    }

    public static boolean isCampo(String campo, String tabela, String schema) throws Exception {
        Sql sql = new Sql("select * from information_schema.columns");
        sql.add("where upper(table_schema) = ?");
        sql.add("and upper(table_name = ?");
        sql.add("and upper(column_name) = ?");

        boolean achou;
        try (PreparedStatement ps = getStatement(sql.getSql(), schema, tabela, campo)) {
            achou = false;
            if (ps.executeQuery().next()) {
                achou = true;
            }
        }
        return achou;
    }

    public static boolean tabelaExiste(String tabela) throws Exception {
        return tabelaExiste(tabela, "public");
    }

    public static boolean tabelaExiste(String tabela, String schema) throws Exception {
        Sql sql = new Sql("SELECT * FROM information_schema.tables");
        sql.add("WHERE table_schema = ?");
        sql.add("and table_name = ?");

        try (PreparedStatement ps = getStatement(sql.getSql(), schema, tabela)) {
            return ps.executeQuery().next();
        }
    }

    public static boolean isId(int valor, String tabela) throws Exception {
        return isId("id", valor, tabela, "public");
    }

    public static boolean isId(String campoId, int valor, String tabela, String schema) throws Exception {
        Sql sql = new Sql("SELECT * FROM").add(schema).add(".").add(tabela);
        sql.add("WHERE").add(campoId).add("= ?");

        boolean achou;
        try (PreparedStatement ps = getStatement(sql.getSql(), valor)) {
            achou = false;
            if (ps.executeQuery().next()) {
                achou = true;
            }
        }
        return achou;
    }

}
