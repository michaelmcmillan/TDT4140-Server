package database;

import logger.Logger;
import models.Appointment;
import java.sql.*;
import java.util.ArrayList;


/**
 * Created by sharklaks on 04/03/15.
 */
public class AppointmentsServletDao<T extends Appointment> implements DbService {

    DatabaseConnection database = new DatabaseConnection();


    @Override
    public boolean create(Object entity) {
        Appointment appointment = (Appointment)entity;
        String insert = "INSERT INTO Appointment (tittel, description, start_time, end_time, Room_id, Person_id) " +
                            "(?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement preppedStatement = null;
            preppedStatement = database.getConn().prepareStatement(insert);
            preppedStatement.setString(1, appointment.getTittel());
            preppedStatement.setString(2, appointment.getDescription());
            preppedStatement.setString(3, appointment.getStartTime());
            preppedStatement.setString(4, appointment.getEndTime());
            preppedStatement.setInt(5, appointment.getRoomId());
            preppedStatement.setInt(6, appointment.getPersonId());
            preppedStatement.executeQuery();
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
    public boolean update(Object newObject) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
