package database;

import org.json.JSONArray;
import org.json.JSONObject;

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
    public JSONObject readOne(int id) {
        try{

            conn = DriverManager.getConnection(uri, user, password);
            stmt = conn.createStatement();

            ResultSet resultSet = stmt.executeQuery("select * from Appointment where id=" + id);
            while (resultSet.next()){
                JSONObject object = new JSONObject();
                object.put("id", resultSet.getInt("id"));
                object.put("tittel", resultSet.getString("tittel"));
                object.put("description", resultSet.getString("description"));
                object.put("Room_id", resultSet.getInt("Room_id"));
                object.put("start_time", resultSet.getDate("start_time"));
                object.put("end_time", resultSet.getDate("end_time"));
                object.put("Person_email", resultSet.getString("Person_email"));


                return object;
            }

        }catch (Exception e){
            System.out.println("Database error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public JSONArray readAll() {
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
