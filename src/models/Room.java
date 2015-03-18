package models;

import database.PersonsServletDao;
import database.RoomServlet;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;


public class Room implements Model{

    //****TABLE ATTRIBUTES****
    private int id;
    private String name;
    private int seats;
    //************************

    public static ArrayList<Room> readRecommendation(int appointmentId, int seats){
        RoomServlet roomServlet = new RoomServlet();
        return roomServlet.readRecommendation(appointmentId, seats);
    }

    @Override
    public boolean create() {
        return false;
    }

    @Override
    public void read(int id) {

    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }

    @Override
    public HashMap<String, Object> toHashMap() {
        return null;
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

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }
}
