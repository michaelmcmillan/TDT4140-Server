package database;

import logger.Logger;
import models.Appointment;
import models.Group;
import models.Person;
import java.sql.*;
import java.util.ArrayList;


public class PersonsServletDao<T extends Person> implements DbService{

    DatabaseConnection database = DatabaseConnection.getInstance();

    public ArrayList<Appointment> readAllAttendingAppointments(int id){

        ArrayList<Appointment> appointments = new ArrayList<>();

        String select =
                "SELECT id, tittel, description, start_time, " +
                "end_time, Room_id, Appointment.Person_id " +
                "FROM Person_has_Appointment " +
                    "JOIN Appointment ON Person_has_Appointment.Appointment_id=id " +
                "WHERE Person_has_Appointment.Person_id=" + id;

        try {

            PreparedStatement preppedStatement = null;
            preppedStatement = database.getConn().prepareStatement(select);

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
                appointment.setParticipating(true);
                appointments.add(appointment);
            }

            return appointments;
        } catch (SQLException error) {
            Logger.console(error.getMessage());
            return null;
        }
    }

    public ArrayList<Group> readAllGroups(int id){
        ArrayList<Group> groups = new ArrayList<>();

        String select =
                "SELECT id as groupId, name, Calendar_id,  Gruppe.Gruppe_id " +
                        "FROM Person_has_Gruppe " +
                        "JOIN Gruppe ON Person_has_Gruppe.Gruppe_id = id " +
                        "WHERE Person_id = ?";
        try {

            PreparedStatement preppedStatement = null;
            preppedStatement = database.getConn().prepareStatement(select);
            preppedStatement.setInt(1, id);

            ResultSet rows = preppedStatement.executeQuery();

            while (rows.next()) {
                Group group= new Group();

                group.setId(rows.getInt("groupId"));
                group.setName(rows.getString("name"));
                group.setCalendarId(rows.getInt("Calendar_id"));
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

        return ServletHelper.create("Person", person.toHashMap()) > 0;
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
    public boolean update(Object object) {
        Person person = (Person) object;

        return ServletHelper.update("Person", person.toHashMap());
    }

    @Override
    public boolean delete(int id) {
        return ServletHelper.delete("Person", id);
    }

}
