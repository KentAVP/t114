package t113;


import java.sql.*;

public class Executor {
    private final Connection connection;

    public Executor(Connection connection) {
        this.connection = connection;
    }

    public <T> T execQuery(String query, ResultHandler<T> handler) throws SQLException {
        Statement stm;
        stm = connection.createStatement();
        connection.setAutoCommit(false);
        stm.execute(query);
        ResultSet result = stm.getResultSet();
        T value = handler.handle(result);
        connection.commit();
        result.close();
        stm.close();
        return value;
    }
    public void execUpdate(String update) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute(update);
        stmt.close();
    }

}
