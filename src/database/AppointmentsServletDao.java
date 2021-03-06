package database;

import logger.Logger;
import models.Appointment;
import models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by sharklaks on 04/03/15.
 */
public class AppointmentsServletDao<T extends Appointment> implements DbService {

    DatabaseConnection database = DatabaseConnection.getInstance();

    public boolean isParticipating(int userId, int appointmentId){
        String select = "SELECT * FROM Person_has_Appointment WHERE Person_id=" + userId + " AND Appointment_id=" + appointmentId ;

        try{

            PreparedStatement preppedStatement = null;
            preppedStatement = database.getConn().prepareStatement(select);
            ResultSet rows = preppedStatement.executeQuery();
            return rows.next();
        }catch (SQLException error){
            Logger.console(error.getMessage());
        }
        return false;
    }

    public static int nAttendies (int appointmentId){
        DatabaseConnection database = DatabaseConnection.getInstance();
        String select = "SELECT count(*) as total FROM Person_has_Appointment WHERE Appointment_id=" + appointmentId;

        try{

            PreparedStatement preppedStatement = null;
            preppedStatement = database.getConn().prepareStatement(select);
            ResultSet rows = preppedStatement.executeQuery();
            if (rows.next())
                return rows.getInt("total");
        }catch (SQLException error){
            Logger.console(error.getMessage());
        }
        return 0;
    }

    public ArrayList<Person> getMembers (int appointmentId){
        DatabaseConnection database = DatabaseConnection.getInstance();
        String select = "SELECT * FROM Person_has_Appointment WHERE Appointment_id=" + appointmentId;
        ArrayList<Person> persons = new ArrayList<>();
        try{

            PreparedStatement preppedStatement = null;
            preppedStatement = database.getConn().prepareStatement(select);
            ResultSet rows = preppedStatement.executeQuery();
            while (rows.next()){
                Person person = new Person();
                person.read(rows.getInt("Person_id"));
                persons.add(person);
            }

            return persons;

        }catch (SQLException error){
            Logger.console(error.getMessage());
        }
        return null;
    }


    @Override
    public boolean create(Object entity) {
        Appointment appointment = (Appointment)entity;
        appointment.setId(ServletHelper.create("Appointment", appointment.toHashMap()));
        return appointment.getId() > 0;
    }

    public boolean create(int appointmentId, int calendarId){
        HashMap<String, Object> map = new HashMap<>();

        map.put("Appointment_id", appointmentId);
        map.put("Calendar_id", calendarId);
        return ServletHelper.create("Appointment_has_Calendar", map) > 0;
    }

    public boolean invite(int appointmentId, int userId){
        HashMap<String, Object> map = new HashMap<>();

        map.put("Appointment_id", appointmentId);
        map.put("Person_id", userId);
        return ServletHelper.create("Person_has_Appointment", map) > 0;
    }

    public boolean removeUser(int appointmentId, int userId){
        DatabaseConnection database = DatabaseConnection.getInstance();
        String delete = "DELETE FROM Person_has_Appointment WHERE Person_id=" + userId + " AND Appointment_id=" + appointmentId;
        try{
            PreparedStatement preparedStatement = database.getConn().prepareStatement(delete);
            preparedStatement.execute();
            return true;
        }catch (SQLException error){
            Logger.console(error.getMessage());
        }
        return false;
    }

    @Override
    public Object readOne(int id) {
        String select = "SELECT * FROM Appointment WHERE id=" + id;

        try{

            PreparedStatement preppedStatement = null;
            preppedStatement = database.getConn().prepareStatement(select);
            ResultSet rows = preppedStatement.executeQuery();

            while (rows.next()){
                Appointment appointment = new Appointment();

                appointment.setId(rows.getInt("id"));
                appointment.setTittel(rows.getString("tittel"));
                appointment.setDescription(rows.getString("description"));
                appointment.setStartTime(rows.getString("start_time"));
                appointment.setEndTime(rows.getString("end_time"));
                appointment.setRoomId(rows.getInt("Room_id"));
                appointment.setPersonId(rows.getInt("Person_id"));

                return appointment;
            }

        }catch (SQLException error){
            Logger.console(error.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList readAll() {
        return null;
    }

    @Override
    public boolean update(Object object) {
        Appointment appointment = (Appointment) object;

        HashMap<String, Object> appointmentMap = appointment.toHashMap();
        appointmentMap.remove("participating");

        return ServletHelper.update("Appointment", appointmentMap);

    }

    @Override
    public boolean delete(int id) {
        return ServletHelper.delete("Appointment", id);
    }
}
