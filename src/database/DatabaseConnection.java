package database;
import java.sql.Connection;
import java.sql.DriverManager;
import logger.Logger;

import javax.xml.crypto.Data;
import java.sql.*;

public class DatabaseConnection {

    private Connection conn;
    private Statement stmt;
    private DatabaseConnection self = this;
    private String database = "fellesprosjekt";
    private String username = "fellesprosjekt";
    private String password = "zK8!iQ9!";
    private String hostname = "jdbc:mysql://littlist.no:3306/" + database;
    private static DatabaseConnection instance = null;

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
    public static DatabaseConnection getInstance(){
        if (instance == null)
            instance = new DatabaseConnection();

        return instance;
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
