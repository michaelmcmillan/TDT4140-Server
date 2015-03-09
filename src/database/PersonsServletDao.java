package database;

import models.Appointment;
import models.Group;
import models.Person;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by sharklaks on 04/03/15.
 */
public class PersonsServletDao<T extends Person> implements DbService{

    private Connection conn;
    private Statement stmt;
    private String uri;
    private static String   user = "fellesprosjekt",
            password = "zK8!iQ9!",
            dbName = "fellesprosjekt";

    public  PersonsServletDao(){
        uri = "jdbc:mysql://littlist.no:3306/" + dbName;
    }


    public ArrayList<Appointment> readAllAppointments(int id){
        try{

            conn = DriverManager.getConnection(uri, user, password);
            stmt = conn.createStatement();

            ResultSet resultSet = stmt.executeQuery("SELECT * FROM Person_has_Appointment join Appointment ON Person_has_Appointment.Appointment_id=id WHERE Person_id=" + id);

            ArrayList<Appointment> appointments = new ArrayList<>();
            while (resultSet.next()){
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

        }catch (Exception e){
            System.out.println("Database error: " + e.getMessage());
        }
        return null;
    }

    public ArrayList<Group> readAllGroups(int id){
        try{

            conn = DriverManager.getConnection(uri, user, password);
            stmt = conn.createStatement();

            ResultSet resultSet = stmt.executeQuery("SELECT id, name, Calendar_id, Person_has_Gruppe.Gruppe_id FROM Person_has_Gruppe join Gruppe ON Person_has_Gruppe.Gruppe_id=id WHERE Person_id=" + id);

            ArrayList<Group> groups = new ArrayList<>();
            while (resultSet.next()){
                Group group = new Group();

                group.setId(resultSet.getInt("id"));
                group.setName(resultSet.getString("name"));
                group.setCalendarId(resultSet.getInt("Calendar_id"));
                if (resultSet.getInt("Gruppe_id") != 0)
                    group.setSuperGroupId(resultSet.getInt("Gruppe_id"));


                groups.add(group);
            }

            return groups;

        }catch (Exception e){
            System.out.println("Database error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean create(Object entity) {
        Person person = (Person)entity;
        try {
            conn = DriverManager.getConnection(uri, user, password);
            stmt = conn.createStatement();

            String values = "('";
            values += person.getEmail() + "','";
            values += person.getFirstName() + "','";
            values += person.getSurname() + "','";
            values += person.getPassword() + "',";
            values += person.getAlarmTime() + ",";
            values += person.getCalendarId() + ");";

            System.out.println("insert into Person (email, firstname, surname, password, alarm_time, Calendar_id) values " + values);

            stmt.execute("insert into Person (email, firstname, surname, password, alarm_time, Calendar_id) values " + values);


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

            ResultSet resultSet = stmt.executeQuery("select * from Person where id=" + id);
            while (resultSet.next()){
                Person person = new Person();

                person.setId(resultSet.getInt("id"));
                person.setAlarmTime(resultSet.getInt("alarm_time"));
                person.setEmail(resultSet.getString("email"));
                person.setFirstName(resultSet.getString("firstname"));
                person.setSurname(resultSet.getString("surname"));
                person.setPassword(resultSet.getString("password"));
                person.setCalendarId(resultSet.getInt("Calendar_id"));

                return person;
            }

        }catch (Exception e){
            System.out.println("Database error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<Person> readAll(){
        try{

            conn = DriverManager.getConnection(uri, user, password);
            stmt = conn.createStatement();

            ResultSet resultSet = stmt.executeQuery("select * from Person");

            ArrayList<Person> allPersons = new ArrayList<>();
            while (resultSet.next()){
                Person person = new Person();

                person.setId(resultSet.getInt("id"));
                person.setAlarmTime(resultSet.getInt("alarm_time"));
                person.setEmail(resultSet.getString("email"));
                person.setFirstName(resultSet.getString("firstname"));
                person.setSurname(resultSet.getString("surname"));
                person.setPassword(resultSet.getString("password"));
                person.setCalendarId(resultSet.getInt("Calendar_id"));

                allPersons.add(person);
            }

            return allPersons;

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
