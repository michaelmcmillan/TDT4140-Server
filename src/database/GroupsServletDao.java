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

        return ServletHelper.create("Gruppe", group.toHashMap()) > 0;
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

        return ServletHelper.update("Gruppe", group.toHashMap());
    }

    @Override
    public boolean delete(int id) {
        return ServletHelper.delete("Gruppe", id);
    }
}
