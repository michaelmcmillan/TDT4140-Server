package database;

import models.Group;
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
public class GroupsServletDao<T extends Group> implements DbService {

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
    public boolean create(Object entity) {
        Group group = (Group)entity;
        try {
            conn = DriverManager.getConnection(uri, user, password);
            stmt = conn.createStatement();

            String values = "('";
            values += group.getName() + "',";
            values += group.getCalendarId();
            if (group.getSuperGroupId() > -1){
                values += ",'" + group.getSuperGroupId() + "')";
                stmt.execute("insert into Gruppe (name, Calendar_id, Group_id) values " + values);
                System.out.println("insert into Group (name, Calendar_id, Group_id) values " + values);
            }
            else {
                values += ")";
                System.out.println("insert into 'Group' (name, Calendar_id) values " + values);

                stmt.execute("insert into Gruppe (name, Calendar_id) values " + values);

            }

            return true;

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
