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

//        calendar.setId(getNewId());
        try {
            conn = DriverManager.getConnection(uri, user, password);
            stmt = conn.createStatement();

//            String values = "(" + calendar.getId() + ");";

//            System.out.println("insert into Calendar (id) values " + values);

            stmt.execute("INSERT INTO Calendar (name) VALUES ('jonas')");
            int id = stmt.getGeneratedKeys().getInt("id");
            calendar.setId(id);
            System.out.println(id);

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
    public boolean update(Object newObject) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
