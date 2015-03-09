package database;

import logger.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ModelTest {

    DatabaseConnection database = new DatabaseConnection();

    public ModelTest () {

        String select = "SELECT * FROM Person where id = ?;";

        try {
            PreparedStatement preppedStatement = null;
            preppedStatement = database.getConn().prepareStatement(select);
            preppedStatement.setInt(1, 2);

            ResultSet rows = preppedStatement.executeQuery();

            while (rows.next()){
                Logger.console(rows.getString("email"));
            }

        } catch (SQLException error) {
            Logger.console(error.getMessage());
        }
    }
}
