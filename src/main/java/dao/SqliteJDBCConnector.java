package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class SqliteJDBCConnector {
    public static Connection connection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/data/database.db");
        } catch (SQLException e) {
            System.out.println("Connection to DB failed");
        }

        return connection;
    }

    public static void createTables() throws SQLException {
        Connection connection = connection();
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS events\n" +
                "        (\n" +
                "        id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "        name VARCHAR NOT NULL,\n" +
                "        event_date TEXT,\n" +
                "        event_time TEXT,\n" +
                "        description TEXT,\n" +
                "        url VARCHAR,\n" +
                "        category_id INTEGER\n" +
                "        );");


        statement.execute("CREATE TABLE IF NOT EXISTS event_categories\n" +
                "       (\n" +
                "       id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "       name VARCHAR NOT NULL \n" +
                "       );");
    }
}
