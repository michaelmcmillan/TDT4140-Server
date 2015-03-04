package database;

import models.Calendar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Created by sharklaks on 04/03/15.
 */
public class CalendarServletDao<T extends Calendar> implements DbService {

    private Connection conn;
    private Statement stmt;
    private String uri;
    private static String   user = "sql368919",
            password = "zK8!iQ9!",
            dbName = "sql368919";

    public  CalendarServletDao(){
        uri = "jdbc:mysql://sql3.freemysqlhosting.net:3306/" + dbName;
    }


    @Override
    public boolean create(Object entity) {
        Calendar calendar = (Calendar)entity;
        try {
            conn = DriverManager.getConnection(uri, user, password);
            stmt = conn.createStatement();

            String values = "(" + calendar.getId() + ");";

            System.out.println("insert into Calendar (id) values " + values);

            stmt.execute("insert into Calendar (id) values " + values);


        }catch (Exception e){
            System.out.println("Database error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Object readOne(int id) {
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
