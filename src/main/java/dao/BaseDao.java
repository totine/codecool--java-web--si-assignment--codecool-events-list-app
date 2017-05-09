package dao;

import java.sql.Connection;

abstract class BaseDao {
    private Connection connection;

    public BaseDao() {
        Connection connection = SqliteJDBCConnector.connection();
        this.setConnection(connection);
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }
}
