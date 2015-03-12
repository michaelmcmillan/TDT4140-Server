package models;


import database.AppointmentsServletDao;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Appointment implements Model {

    AppointmentsServletDao appServlet;

    //*****TABLE ATTRIBUTES*******
    private int id;
    private String tittel;
    private String description;
    private String startTime;
    private String endTime;
    private int roomId;
    private int personId;
    //****************************

    public Appointment(){
        appServlet = new AppointmentsServletDao();
    }

    @Override
    public boolean create() {
        return appServlet.create(this);
    }

    public boolean create(int calendarId){
        create();
        appServlet.create(id, calendarId);
        return true;
    }

    @Override
    public void read(int id) {
        Appointment appointment = (Appointment)appServlet.readOne(id);
        this.id = appointment.getId();
        this.tittel = appointment.getTittel();
        this.description = appointment.getDescription();
        this.startTime = appointment.getStartTime();
        this.endTime = appointment.getEndTime();
        this.roomId = appointment.getRoomId();
        this.personId = appointment.getPersonId();
    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {
        appServlet.delete(id);
    }

    @Override
    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("tittel", tittel);
        map.put("description", description);
        map.put("start_time", startTime);
        map.put("end_time", endTime);
        map.put("Room_id", roomId);
        map.put("Person_id", personId);
        return map;
    }

    public AppointmentsServletDao getAppServlet() {
        return appServlet;
    }
    public void setAppServlet(AppointmentsServletDao appServlet) {
        this.appServlet = appServlet;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTittel() {
        return tittel;
    }
    public void setTittel(String tittel) {
        this.tittel = tittel;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    public int getRoomId() {
        return roomId;
    }
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
    public int getPersonId() {
        return personId;
    }
    public void setPersonId(int personId) {
        this.personId = personId;
    }
}
