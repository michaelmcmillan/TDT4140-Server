package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by sharklaks on 02/03/15.
 */
public class TestServletDao<T extends String> implements  DbService {


    private Connection conn;
    private Statement stmt;
    private String uri;
    private static String   user = "sql368919",
                            password = "zK8!iQ9!",
                            dbName = "sql368919";

    public  TestServletDao(){
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

            ResultSet resultSet = stmt.executeQuery("select * from test limit 1");

            while (resultSet.next()){
                return resultSet.getString("test");
            }

        }catch (Exception e){
            System.out.println("Database error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList readAll() {
        try{
            ArrayList<String> arrayList = new ArrayList<>();

            conn = DriverManager.getConnection(uri, user, password);
            stmt = conn.createStatement();

            ResultSet resultSet = stmt.executeQuery("select * from test limit 1");

            while (resultSet.next()){
                arrayList.add(resultSet.getString("test"));
            }
            return arrayList;
        }catch (Exception e){
            System.out.println("Database error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean update(int id, Object newObject) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
