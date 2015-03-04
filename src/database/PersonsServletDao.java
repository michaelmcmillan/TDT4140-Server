package database;

import models.Person;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by sharklaks on 04/03/15.
 */
public class PersonsServletDao<T extends Person> implements DbService{

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
    public boolean create(Object entity) {
        return false;
    }

    @Override
    public Object readOne(int id) {
        try{

            conn = DriverManager.getConnection(uri, user, password);
            stmt = conn.createStatement();

            ResultSet resultSet = stmt.executeQuery("select * from Person where id=" + id);
            while (resultSet.next()){
                Person person = new Person();

                person.setId(resultSet.getInt("id"));
                person.setAlarmTime(resultSet.getInt("alarm_time"));
                person.setEmail(resultSet.getString("email"));
                person.setFirstName(resultSet.getString("firstname"));
                person.setSurname(resultSet.getString("surname"));
                person.setPassword(resultSet.getString("password"));
                person.setCalendarId(resultSet.getInt("Calendar_id"));

                return person;
            }

        }catch (Exception e){
            System.out.println("Database error: " + e.getMessage());
        }
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
