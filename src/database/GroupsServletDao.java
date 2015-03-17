package database;

import logger.Logger;
import models.Group;
import models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by sharklaks on 04/03/15.
 */
public class GroupsServletDao<T extends Group> implements DbService {

    DatabaseConnection database = DatabaseConnection.getInstance();


    public boolean addUser(int groupId, int personId){
        HashMap<String, Object> map = new HashMap<>();

        map.put("Gruppe_id", groupId);
        map.put("Person_id", personId);

        return ServletHelper.create("Person_has_Gruppe", map) == 0;
    }

    public boolean removeUser(int groupId, int personId){
        DatabaseConnection database = DatabaseConnection.getInstance();
        String delete = "DELETE FROM Person_has_Gruppe WHERE Person_id=" + personId + " AND Gruppe_id=" + groupId;
        try{
            PreparedStatement preparedStatement = database.getConn().prepareStatement(delete);
            preparedStatement.execute();
            return true;
        }catch (SQLException error){
            Logger.console(error.getMessage());
        }
        return false;
    }

    public ArrayList<Person> getAllMembers(int id) {
        ArrayList<Person> persons = new ArrayList<>();

        String select = "SELECT * FROM fellesprosjekt.Person_has_Gruppe " +
                        "JOIN fellesprosjekt.Person ON Person_has_Gruppe.Person_id=Person.id " +
                        "WHERE Person_has_Gruppe.Gruppe_id=" +id ;
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
    public boolean create(Object entity) {
        Group group = (Group) entity;
        group.setId(ServletHelper.create("Gruppe", group.toHashMap()));
        return  group.getId() > 0;
    }

    @Override
    public Object readOne(int id) {
        String select = "SELECT * FROM Gruppe WHERE id=" + id;
        try{
            PreparedStatement preppedStatement = null;
            preppedStatement = database.getConn().prepareStatement(select);
            ResultSet rows = preppedStatement.executeQuery();

            while (rows.next()){
                Group group = new Group();

                group.setId(rows.getInt("id"));
                group.setName(rows.getString("name"));
                group.setCalendarId(rows.getInt("Calendar_id"));
                group.setSuperGroupId(rows.getInt("Gruppe_id"));
                return group;
            }

        }catch (Exception e){
            System.out.println("Database error: " + e.getMessage());
        }
        return null;
    }

    public ArrayList readSuperGroups(int userId) {
        ArrayList<Group> groups = new ArrayList<>();
        String select = "SELECT *  " +
                "FROM Person_has_Gruppe " +
                "RIGHT JOIN Gruppe ON Person_has_Gruppe.Gruppe_id=Gruppe.id " +
                "WHERE Gruppe.Gruppe_id is null AND Person_id=" + userId;

        try{
            PreparedStatement preppedStatement = null;
            preppedStatement = database.getConn().prepareStatement(select);
            ResultSet rows = preppedStatement.executeQuery();

            while (rows.next()){
                Group group = new Group();

                group.setId(rows.getInt("id"));
                group.setName(rows.getString("name"));
                group.setCalendarId(rows.getInt("Calendar_id"));
                group.setSuperGroupId(rows.getInt("Gruppe_id"));
                groups.add(group);
            }

        }catch (Exception e){
            System.out.println("Database error: " + e.getMessage());
        }
        return groups;
    }

    @Override
    public ArrayList readAll() {
        ArrayList<Group> groups = new ArrayList<>();
        String select = "SELECT * FROM Gruppe";

        try{
            PreparedStatement preppedStatement = null;
            preppedStatement = database.getConn().prepareStatement(select);
            ResultSet rows = preppedStatement.executeQuery();

            while (rows.next()){
                Group group = new Group();

                group.setId(rows.getInt("id"));
                group.setName(rows.getString("name"));
                group.setCalendarId(rows.getInt("Calendar_id"));
                group.setSuperGroupId(rows.getInt("Gruppe_id"));
                groups.add(group);
            }

        }catch (Exception e){
            System.out.println("Database error: " + e.getMessage());
        }
        return groups;
    }

    @Override
    public boolean update(Object object) {
        Group group = (Group) object;

        HashMap<String, Object> map = group.toHashMap();
        map.remove("Calendar_id");


        return ServletHelper.update("Gruppe", map);
    }

    @Override
    public boolean delete(int id) {
        return ServletHelper.delete("Gruppe", id);
    }
}
