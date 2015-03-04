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
public class GroupsServletDao implements DbService {

    private Connection conn;
    private Statement stmt;
    private String uri;
    private static String   user = "sql368919",
            password = "zK8!iQ9!",
            dbName = "sql368919";

    public  GroupsServletDao(){
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

            ResultSet resultSet = stmt.executeQuery("select * from Group where id=" + id);
            while (resultSet.next()){
                JSONObject object = new JSONObject();
                object.put("name", resultSet.getString("name"));
                object.put("Calendar_id", resultSet.getInt("Calendar_id"));
                object.put("Group_name", resultSet.getString("Group_name"));


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

            ResultSet resultSet = stmt.executeQuery("select * from sql368919.Group");

            while (resultSet.next()){
                JSONObject object = new JSONObject();
                object.put("name", resultSet.getString("name"));
                object.put("Calendar_id", resultSet.getInt("Calendar_id"));
                object.put("Group_name", resultSet.getString("Group_name"));
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
