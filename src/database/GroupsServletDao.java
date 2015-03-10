package database;

import logger.Logger;
import models.Group;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by sharklaks on 04/03/15.
 */
public class GroupsServletDao<T extends Group> implements DbService {

    DatabaseConnection database = new DatabaseConnection();

    public  GroupsServletDao(){

    }

    @Override
    public boolean create(Object entity) {
        Group group = (Group) entity;

        String insert =
                "INSERT INTO Gruppe (name, Calendar_id, Group_id) " +
                        "(?, ?, ?)";
        try {
            PreparedStatement preppedStatement = null;
            preppedStatement = database.getConn().prepareStatement(insert);
            preppedStatement.setString( 1, group.getName());
            preppedStatement.setInt(    2, group.getCalendarId());
            preppedStatement.setInt(    3, group.getSuperGroupId());
            preppedStatement.execute();
        } catch (SQLException error) {
            Logger.console(error.getMessage());
        }

        return true;
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

    @Override
    public ArrayList readAll() {
        ArrayList<Group> groups = new ArrayList<>();
        String select = "SELECT * FROM Person";

        try{
            PreparedStatement preppedStatement = null;
            preppedStatement = database.getConn().prepareStatement(select);
            ResultSet rows = preppedStatement.executeQuery();

            while (rows.next()) {
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

        String insert =
                "UPDATE Gruppe " +
                        "SET name=?, Calendar_id=?, Group_id=? " +
                        "WHERE id=" + group.getId();
        try {
            PreparedStatement preppedStatement = null;
            preppedStatement = database.getConn().prepareStatement(insert);
            preppedStatement.setString( 1, group.getName());
            preppedStatement.setInt(    2, group.getCalendarId());
            preppedStatement.setInt(    3, group.getSuperGroupId());
            preppedStatement.executeUpdate();
        } catch (SQLException error) {
            Logger.console(error.getMessage());
        }

        return true;
    }

    @Override
    public boolean delete(int id) {
        String delete = "DELETE FROM Gruppe WHERE id=" + id;
        try{
            PreparedStatement preparedStatement = database.getConn().prepareStatement(delete);
            preparedStatement.execute();
            return true;
        }catch (SQLException error){
            Logger.console(error.getMessage());
        }
        return false;
    }
}
