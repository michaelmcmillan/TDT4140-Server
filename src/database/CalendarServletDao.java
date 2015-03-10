package database;

import logger.Logger;
import models.Appointment;
import models.Calendar;
import models.Group;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by sharklaks on 04/03/15.
 */
public class CalendarServletDao<T extends Calendar> implements DbService {

    DatabaseConnection database = new DatabaseConnection();


    public ArrayList<Appointment> readAllAppointments(int id) {
        String select = "SELECT id, tittel, description, start_time, end_time, Room_id, Person_id FROM fellesprosjekt.Appointment_has_Calendar join fellesprosjekt.Appointment ON Appointment_has_Calendar.Appointment_id=Appointment.id Where Calendar_id=" + id;

        try {
            PreparedStatement preppedStatement = null;
            preppedStatement = database.getConn().prepareStatement(select);
            ResultSet rows = preppedStatement.executeQuery();

            ArrayList<Appointment> appointments = new ArrayList<>();
            while (rows.next()) {
                Appointment appointment = new Appointment();
                appointment.setId(rows.getInt("id"));
                appointment.setTittel(rows.getString("tittel"));
                appointment.setDescription(rows.getString("description"));
                appointment.setStartTime(rows.getString("start_time"));
                appointment.setEndTime(rows.getString("end_time"));
                appointment.setRoomId(rows.getInt("Room_id"));
                appointment.setPersonId(rows.getInt("Person_id"));
                appointments.add(appointment);
            }
            return appointments;
        } catch (SQLException error) {
            Logger.console(error.getMessage());
        }
        return null;
    }

    @Override
    public boolean create(Object entity) {
        Calendar calendar = (Calendar)entity;
        String insert = "INSERT INTO Calendar VALUES ()";

        try {
            PreparedStatement preppedStatement = null;
            preppedStatement = database.getConn().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            preppedStatement.execute();
            ResultSet rows = preppedStatement.getGeneratedKeys();
            if (rows.next()){
                calendar.setId(rows.getInt(1));
            }

//            while (pre)
//            calendar.setId(preppedStatement.getGeneratedKeys().getInt("id"));
            return  true;
        }catch (Exception error){
            Logger.console(error.getMessage());
        }
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
    public boolean update(Object newObject) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
