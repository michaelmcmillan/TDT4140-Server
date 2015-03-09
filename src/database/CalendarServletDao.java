package database;

import models.Appointment;
import models.Calendar;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by sharklaks on 04/03/15.
 */
public class CalendarServletDao<T extends Calendar> implements DbService {

    private Connection conn;
    private Statement stmt;
    private String uri;
    private static String   user = "fellesprosjekt",
            password = "zK8!iQ9!",
            dbName = "fellesprosjekt";

    public  CalendarServletDao(){
        uri = "jdbc:mysql://littlist.no:3306/" + dbName;
    }

    public ArrayList<Appointment> readAllAppointments(int id) {
        try {

            conn = DriverManager.getConnection(uri, user, password);
            stmt = conn.createStatement();
            String queryString = "SELECT id, tittel, description, start_time, end_time, Room_id, Person_id FROM fellesprosjekt.Appointment_has_Calendar join fellesprosjekt.Appointment ON Appointment_has_Calendar.Appointment_id=Appointment.id Where Calendar_id=" + id;
            ResultSet resultSet = stmt.executeQuery(queryString);

            ArrayList<Appointment> appointments = new ArrayList<>();
            while (resultSet.next()) {
                Appointment appointment = new Appointment();

                appointment.setId(resultSet.getInt("id"));
                appointment.setTittel(resultSet.getString("tittel"));
                appointment.setDescription(resultSet.getString("description"));
                appointment.setStartTime(resultSet.getString("start_time"));
                appointment.setEndTime(resultSet.getString("end_time"));
                appointment.setRoomId(resultSet.getInt("Room_id"));
                appointment.setPersonId(resultSet.getInt("Person_id"));

                appointments.add(appointment);
            }

            return appointments;

        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean create(Object entity) {
        Calendar calendar = (Calendar)entity;

        try {
            int generatedKey = -1;
            conn = DriverManager.getConnection(uri, user, password);
            Statement statement = conn.createStatement();
            statement.executeUpdate("INSERT INTO Calendar VALUES ()", Statement.RETURN_GENERATED_KEYS);
            ResultSet keyset = statement.getGeneratedKeys();

            if (keyset.next())  generatedKey = keyset.getInt(1);
            calendar.setId(generatedKey);

            return  true;
        }catch (Exception e){
            System.out.println("Database error: " + e.getMessage());
        }
        return false;
    }

    private int getNewId(){
        try {
            conn = DriverManager.getConnection(uri, user, password);
            stmt = conn.createStatement();

            stmt.executeQuery("select max(id) + 1 as id from Calendar");

            if (stmt.getResultSet().next()){
                return stmt.getResultSet().getInt("id");
            }


        }catch (Exception e){
            System.out.println("Database error: " + e.getMessage());
        }
        return 0;
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
    public boolean update(Object newObject) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
