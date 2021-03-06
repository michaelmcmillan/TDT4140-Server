package models;

import database.CalendarServletDao;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by sharklaks on 04/03/15.
 */
public class Calendar implements Model{

    private CalendarServletDao calendarServlet;

    //*****TABLE ATTRIBUTES*******
    private int id;
    //*********THATS IT***********

    public Calendar (){
        calendarServlet = new CalendarServletDao();
    }

    private void fetchAppointments(){

    }

    public ArrayList<Appointment> getAllAppointments(int userId){
        return calendarServlet.readAllAppointments(id, userId);
    }

    public int getSuperGroupId(){
        return calendarServlet.getSuperGroupId(id);
    }


    public JSONArray appointmentsToJSON(){
        return new JSONArray();
    }

    @Override
    public boolean create(){
        return calendarServlet.create(this);
    }

    @Override
    public void read(int id) {

    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {
        calendarServlet.delete(id);
    }

    @Override
    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        return map;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
