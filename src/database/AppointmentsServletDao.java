package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by sharklaks on 04/03/15.
 */
public class AppointmentsServletDao implements DbService {

    private Connection conn;
    private Statement stmt;
    private String uri;
    private static String   user = "sql368919",
            password = "zK8!iQ9!",
            dbName = "sql368919";

    public  AppointmentsServletDao(){
        uri = "jdbc:mysql://sql3.freemysqlhosting.net:3306/" + dbName;
    }

    @Override
    public boolean create(String entity) {
        return false;
    }

    @Override
    public String readOne(int id) {
        try{

            conn = DriverManager.getConnection(uri, user, password);
            stmt = conn.createStatement();

            ResultSet resultSet = stmt.executeQuery("select * from Appointment limit 1");

            while (resultSet.next()){
                return resultSet.getString("tittel");
            }

        }catch (Exception e){
            System.out.println("Database error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public String readAll() {
        return null;
    }

    @Override
    public boolean update(int id, String newObject) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
