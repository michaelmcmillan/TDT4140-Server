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

    public  TestServletDao(){
        String  user = "sql368919",
                password = "zK8!iQ9!",
                dbName = "sql368919";

        String uri = "jdbc:mysql://sql3.freemysqlhosting.net:3306/" + dbName;

        try{

            conn = DriverManager.getConnection(uri, user, password);
            stmt = conn.createStatement();

            ResultSet resultSet = stmt.executeQuery("select * from test");

            while (resultSet.next()){
                System.out.println(resultSet.getString("test"));
            }
        }catch (Exception e){
            System.out.println("Database error: " + e.getMessage());
        }

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
    public ArrayList readAll() {
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
