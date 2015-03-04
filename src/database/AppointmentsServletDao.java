package database;

import models.Appointment;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


/**
 * Created by sharklaks on 04/03/15.
 */
public class AppointmentsServletDao<T extends Appointment> implements DbService {

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
    public boolean create(Object entity) {
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
