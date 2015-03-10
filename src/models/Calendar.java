package models;

import database.CalendarServletDao;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sharklaks on 04/03/15.
 */
public class Calendar implements Model{

    private CalendarServletDao calendarServlet;

    private ArrayList<Appointment> appointments;

    //*****TABLE ATTRIBUTES*******
    private int id;
    //*********THATS IT***********

    public Calendar (){
        calendarServlet = new CalendarServletDao();
    }

    private void fetchAppointments(){

    }

    public ArrayList<Appointment> getAllAppointments(){
        return calendarServlet.readAllAppointments(id);
    }


    public JSONArray appointmentsToJSON(){
        return new JSONArray();
    }

    @Override
    public void create(){
        calendarServlet.create(this);
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


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }
    public void setAppointments(ArrayList<Appointment> appointments) {
        this.appointments = appointments;
    }
}
