package database;

import logger.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sharklaks on 10/03/15.
 */
public class ServletHelper {


    public static int create(String table, HashMap<String, Object> map){
        map.remove("id");
        DatabaseConnection database = new DatabaseConnection();
        ArrayList<Object> values = new ArrayList<>();
        String fields = "";
        String questionMarks = "";

        for (Map.Entry<String, Object> row: map.entrySet()){
            fields += row.getKey() + ",";
            questionMarks += "?,";
            values.add(row.getValue());
        }
        if (fields.length() > 0){
            fields = fields.substring(0, fields.length() - 1);
            questionMarks = questionMarks.substring(0, questionMarks.length() - 1);
        }


        String insert = "INSERT INTO " + table + " (" + fields + ") " + "VALUES (" + questionMarks + ")";
        try{
            PreparedStatement preppedStatement = null;
            preppedStatement = database.getConn().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

            int count = 1;
            for (Object value: values){
                if (value.getClass().getName() == "java.lang.String")   preppedStatement.setString( count, (String)values.get(count - 1));
                else {
                    int tempInt = (Integer) values.get(count - 1);
                    if (tempInt > 0)
                        preppedStatement.setInt(count, (Integer) values.get(count - 1));
                    else
                        preppedStatement.setNull(count, Types.INTEGER);

                }

                count ++;
            }
            preppedStatement.execute();
            ResultSet rows = preppedStatement.getGeneratedKeys();
            if (rows.next()){
                return  rows.getInt(1);
            }
        }catch (SQLException error){
            Logger.console(error.getMessage());
        }

        return 0;
    }

    public static boolean delete(String table, int id){
        return true;
    }

    public static boolean update(String table, HashMap<String, Object> map){
        return true;
    }

}
