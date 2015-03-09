package database;
import java.sql.Connection;
import java.sql.DriverManager;
import logger.Logger;

import java.sql.*;
import java.util.HashMap;

public class DatabaseConnection {

    private Connection conn;
    private Statement stmt;
    private DatabaseConnection self = this;
    private String database = "fellesprosjekt";
    private String username = "root";
    private String password = "";
    private String hostname = "jdbc:mysql://127.0.0.1:3306/" + database;

    public DatabaseConnection () {

        try {
            this.connect();
        } catch (SQLException error) {
            Logger.console(error.getMessage());
        }

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                try {
                    self.disconnect();
                } catch (SQLException e) {
                    Logger.console("Klarte ikke å skru av databasen ved sigint.");
                    Logger.console(e.getMessage());
                }
            }
        }));
    }

    public Connection getConn () {
        return this.conn;
    }

    private void connect () throws SQLException {
        conn = DriverManager.getConnection(hostname, username, password);
        stmt = conn.createStatement();
    }

    public void disconnect () throws SQLException {
        conn.close();
    }
}
