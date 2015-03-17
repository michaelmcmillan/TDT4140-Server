package models;


import database.GroupsServletDao;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Group implements Model{

    private GroupsServletDao groupServlet;

    //*****TABLE ATTRIBUTES*******
    private int id;
    private String name;
    private int calendarId;
    private int superGroupId;
    //****************************

    private Calendar calendar;

    public Group() {
        groupServlet = new GroupsServletDao();
    }


    public boolean addUser(int userId){
        return groupServlet.addUser(id, userId);
    }

    public ArrayList<Person> getAllMembers(){
        return groupServlet.getAllMembers(id);
    }

    public boolean removeUser(int userId){
        return groupServlet.removeUser(id, userId);
    }


    public static ArrayList<Group> getAllSupergroups(){
        GroupsServletDao groupServlet = new GroupsServletDao();
        ArrayList<Group> groups = groupServlet.readAll();

        ArrayList<Group> groupsToReturn = new ArrayList<>();

        for (Group group : groups){
            if (group.getSuperGroupId() == 0)
                groupsToReturn.add(group);
        }

        return groupsToReturn;
    }

    @Override
    public boolean create(){
        calendar = new Calendar();
        calendar.create();
        calendarId = calendar.getId();
        return groupServlet.create(this);
    }

    @Override
    public void read (int id){
        Group group = (Group)groupServlet.readOne(id);

        this.id = group.getId();
        this.name = group.getName();
        this.calendarId = group.getCalendarId();
        this.superGroupId = group.getSuperGroupId();
    }

    @Override
    public void update() {
        groupServlet.update(this);
    }

    @Override
    public void delete() {
        groupServlet.delete(id);
    }

    @Override
    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("name", name);
        map.put("Calendar_id", calendarId);
        map.put("Gruppe_id", superGroupId);
        return map;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getCalendarId() {
        return calendarId;
    }
    public void setCalendarId(int calendarId) {
        this.calendarId = calendarId;
    }
    public int getSuperGroupId() {
        return superGroupId;
    }
    public void setSuperGroupId(int superGroupId) {
        this.superGroupId = superGroupId;
    }


}