package database;

import java.sql.*;
import specs.DatabaseSpecs;

public abstract class Database implements DatabaseSpecs {
    private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private final String DB_URL = "jdbc:mysql://localhost/peternakan";
    private final String USER = "root";
    private final String PASS = "";

    private Connection conn;
    private Statement stmt;
    protected ResultSet value;
    private String query;

    public Database() throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        stmt = conn.createStatement();
    }

    @Override
    public void setQuery(String sql) {
        this.query = sql;
    }

    @Override
    public String getQuery() {
        return this.query;
    }

    public void setClose() throws SQLException {
        this.stmt.close();
        conn.close();
    }

    public void execute() throws SQLException {
        stmt.execute(this.query);
    }

    public void fetch() throws SQLException {
        this.value = stmt.executeQuery(this.query);
    }
}