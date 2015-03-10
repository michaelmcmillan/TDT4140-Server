package database;

import logger.Logger;
import models.Appointment;
import models.Group;
import models.Person;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.xml.crypto.Data;
import java.lang.reflect.Array;
import java.sql.*;
import java.util.ArrayList;


public class PersonsServletDao<T extends Person> implements DbService{

    DatabaseConnection database = new DatabaseConnection();

    public ArrayList<Appointment> readAllAppointments(int id){

        ArrayList<Appointment> appointments = new ArrayList<>();

        String select =
                "SELECT id, tittel, description, start_time, " +
                "end_time, Room_id, Appointment.Person_id " +
                "FROM Person_has_Appointment " +
                    "JOIN Appointment ON Person_has_Appointment.Appointment_id=id " +
                "WHERE Person_has_Appointment.Person_id= ?";

        try {

            PreparedStatement preppedStatement = null;
            preppedStatement = database.getConn().prepareStatement(select);
            preppedStatement.setInt(1, id);

            ResultSet rows = preppedStatement.executeQuery();

            while (rows.next()) {
                Appointment appointment = new Appointment();

                appointment.setId         (rows.getInt("id"));
                appointment.setTittel     (rows.getString("tittel"));
                appointment.setDescription(rows.getString("description"));
                appointment.setStartTime  (rows.getString("start_time"));
                appointment.setEndTime    (rows.getString("end_time"));
                appointment.setRoomId     (rows.getInt("Room_id"));
                appointment.setPersonId   (rows.getInt("Person_id"));

                appointments.add(appointment);
            }
        } catch (SQLException error) {
            Logger.console(error.getMessage());
        } finally {
            return appointments;
        }
    }

    public ArrayList<Group> readAllGroups(int id){
        ArrayList<Group> groups = new ArrayList<>();

        String select =
                "SELECT id, name, Calendar_id, Person_has_Gruppe.Gruppe_id " +
                        "FROM Person_has_Gruppe " +
                        "JOIN Gruppe ON Person_has_Gruppe.Gruppe_id = id " +
                        "WHERE Person_id.Person_id = ?";
        try {

            PreparedStatement preppedStatement = null;
            preppedStatement = database.getConn().prepareStatement(select);
            preppedStatement.setInt(1, id);

            ResultSet rows = preppedStatement.executeQuery();

            while (rows.next()) {
                Group group= new Group();

                group.setId(rows.getInt("id"));
                group.setName(rows.getString("name"));
                group.setCalendarId(rows.getInt("Calendar_id"));
                if (rows.getInt("Gruppe_id") != 0)
                    group.setSuperGroupId(rows.getInt("Gruppe_id"));

                groups.add(group);
            }
        } catch (SQLException error) {
            Logger.console(error.getMessage());
        } finally {
            return groups;
        }
    }

    public boolean create (Object entity) {
        Person person = (Person) entity;

        String insert =
                "INSERT INTO Person (email, firstname, surname, password, alarm_time, Calendar_id) VALUES" +
                "(?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preppedStatement = null;
            preppedStatement = database.getConn().prepareStatement(insert);
            preppedStatement.setString(1, person.getEmail());
            preppedStatement.setString(2, person.getFirstName());
            preppedStatement.setString(3, person.getSurname());
            preppedStatement.setString(4, person.getPassword());
            preppedStatement.setInt(   5, person.getAlarmTime());
            preppedStatement.setInt(   6, person.getCalendarId());

            preppedStatement.execute();

        } catch (SQLException error) {
            Logger.console(error.getMessage());
        }

        return true;
    }

    @Override
    public Object readOne(int id) {
        String select = "SELECT * FROM Person WHERE id=" + id;

        try {

            PreparedStatement preppedStatement = null;
            preppedStatement = database.getConn().prepareStatement(select);

            ResultSet rows = preppedStatement.executeQuery();


            while (rows.next()){
                Person person = new Person();

                person.setId(rows.getInt("id"));
                person.setAlarmTime(rows.getInt("alarm_time"));
                person.setEmail(rows.getString("email"));
                person.setFirstName(rows.getString("firstname"));
                person.setSurname(rows.getString("surname"));
                person.setPassword(rows.getString("password"));
                person.setCalendarId(rows.getInt("Calendar_id"));

                return person;
            }
        } catch (Exception e){
            System.out.println("Database error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<Person> readAll(){
        ArrayList<Person> persons = new ArrayList<>();

        String select = "SELECT * FROM Person";

        try {

            PreparedStatement preppedStatement = null;
            preppedStatement = database.getConn().prepareStatement(select);

            ResultSet rows = preppedStatement.executeQuery();

            while (rows.next()){
                Person person = new Person();

                person.setId(rows.getInt("id"));
                person.setAlarmTime(rows.getInt("alarm_time"));
                person.setEmail(rows.getString("email"));
                person.setFirstName(rows.getString("firstname"));
                person.setSurname(rows.getString("surname"));
                person.setPassword(rows.getString("password"));
                person.setCalendarId(rows.getInt("Calendar_id"));

                persons.add(person);
            }
        } catch (Exception e){
            System.out.println("Database error: " + e.getMessage());
        } finally {
            return persons;
        }
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
