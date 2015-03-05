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
        Appointment appointment = (Appointment)entity;
        try {
            conn = DriverManager.getConnection(uri, user, password);
            stmt = conn.createStatement();

            String values = "('";
            values += appointment.getTittel() + "','";
            values += appointment.getDescription() + "','";
            values += appointment.getStartTime() + "','";
            values += appointment.getEndTime() + "',";
            if (appointment.getRoomId() != 0){
                values += appointment.getRoomId() + ",";
            }
            values += appointment.getPersonId() + ");";

            System.out.println("insert into Appointment (tittel, description, start_time, end_time, Room_id, Person_id) values " + values);

            if (appointment.getRoomId() != 0){
                stmt.execute("insert into Appointment (tittel, description, start_time, end_time, Room_id, Person_id) values " + values);
            }else
                stmt.execute("insert into Appointment (tittel, description, start_time, end_time, Person_id) values " + values);

            return true;


        }catch (Exception e){
            System.out.println("Database error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Object readOne(int id) {
        try{

            conn = DriverManager.getConnection(uri, user, password);
            stmt = conn.createStatement();

            ResultSet resultSet = stmt.executeQuery("select * from Appointment where id=" + id);
            while (resultSet.next()){
                Appointment appointment = new Appointment();

                appointment.setId(resultSet.getInt("id"));
                appointment.setTittel(resultSet.getString("tittel"));
                appointment.setDescription(resultSet.getString("description"));
                appointment.setStartTime(resultSet.getString("start_time"));
                appointment.setEndTime(resultSet.getString("end_time"));
                appointment.setRoomId(resultSet.getInt("Room_id"));
                appointment.setPersonId(resultSet.getInt("Person_id"));

                return appointment;
            }

        }catch (Exception e){
            System.out.println("Database error: " + e.getMessage());
        }
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
