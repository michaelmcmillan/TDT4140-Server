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
public class PersonsServletDao implements DbService{

    private Connection conn;
    private Statement stmt;
    private String uri;
    private static String   user = "sql368919",
            password = "zK8!iQ9!",
            dbName = "sql368919";

    public  PersonsServletDao(){
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

            ResultSet resultSet = stmt.executeQuery("select * from Person where id=" + id);
            while (resultSet.next()){
                JSONObject object = new JSONObject();
                object.put("email", resultSet.getString("email"));
                object.put("firstname", resultSet.getString("firstname"));
                object.put("surname", resultSet.getString("surname"));
                object.put("password", resultSet.getString("password"));
                object.put("alarm_time", resultSet.getInt("alarm_time"));
                object.put("Calendar_id", resultSet.getInt("Calendar_id"));


                return object;
            }

        }catch (Exception e){
            System.out.println("Database error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public JSONArray readAll() {
        try{
            JSONArray jsonArray = new JSONArray();

            conn = DriverManager.getConnection(uri, user, password);
            stmt = conn.createStatement();

            ResultSet resultSet = stmt.executeQuery("select * from Person");

            while (resultSet.next()){
                JSONObject object = new JSONObject();
                object.put("email", resultSet.getString("email"));
                object.put("firstname", resultSet.getString("firstname"));
                object.put("surname", resultSet.getString("surname"));
                object.put("password", resultSet.getString("password"));
                object.put("alarm_time", resultSet.getInt("alarm_time"));
                object.put("Calendar_id", resultSet.getInt("Calendar_id"));
                jsonArray.put(object);
            }
            return jsonArray;
        }catch (Exception e){
            System.out.println("Database error: " + e.getMessage());
        }
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
