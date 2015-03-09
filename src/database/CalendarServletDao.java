package database;

import models.Calendar;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by sharklaks on 04/03/15.
 */
public class CalendarServletDao<T extends Calendar> implements DbService {

    private Connection conn;
    private Statement stmt;
    private String uri;
    private static String   user = "fellesprosjekt",
            password = "zK8!iQ9!",
            dbName = "fellesprosjekt";

    public  CalendarServletDao(){
        uri = "jdbc:mysql://littlist.no:3306/" + dbName;
    }


    @Override
    public boolean create(Object entity) {
        Calendar calendar = (Calendar)entity;

        try {
            int generatedKey = -1;
            conn = DriverManager.getConnection(uri, user, password);
            Statement statement = conn.createStatement();
            statement.executeUpdate("INSERT INTO Calendar VALUES ()", Statement.RETURN_GENERATED_KEYS);
            ResultSet keyset = statement.getGeneratedKeys();

            if (keyset.next())  generatedKey = keyset.getInt(1);
            calendar.setId(generatedKey);

            return  true;
        }catch (Exception e){
            System.out.println("Database error: " + e.getMessage());
        }
        return false;
    }

    private int getNewId(){
        try {
            conn = DriverManager.getConnection(uri, user, password);
            stmt = conn.createStatement();

            stmt.executeQuery("select max(id) + 1 as id from Calendar");

            if (stmt.getResultSet().next()){
                return stmt.getResultSet().getInt("id");
            }


        }catch (Exception e){
            System.out.println("Database error: " + e.getMessage());
        }
        return 0;
    }

    @Override
    public Object readOne(int id) {
        return null;
    }

    @Override
    public ArrayList readAll() {
        return null;
    }

    @Override
    public boolean update(Object newObject) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
