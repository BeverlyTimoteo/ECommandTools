package ecommandtools.connection;

public class Sql {

    private StringBuilder sql = null;

    public Sql() {
        sql = new StringBuilder();
    }

    public Sql(String sql) {
        this.sql = new StringBuilder(sql);
    }

    public Sql add(String sql) throws Exception {
        this.sql.append(" ").append(sql);

        return this;
    }

    public String getSql() throws Exception {
        return sql.toString();
    }

}
