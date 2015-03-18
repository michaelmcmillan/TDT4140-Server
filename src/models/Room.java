package models;

import database.PersonsServletDao;
import database.RoomServlet;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;


public class Room implements Model{

    RoomServlet roomServlet = new RoomServlet();


    //****TABLE ATTRIBUTES****
    private int id;
    private String name;
    private int seats;
    //************************

    public static ArrayList<Room> readRecommendation(String fromTime, String toTime, int seats){
        RoomServlet roomServlet = new RoomServlet();
        return roomServlet.readRecommendation(fromTime, toTime, seats);
    }

    @Override
    public boolean create() {
        return false;
    }

    @Override
    public void read(int id) {
    }

    public static ArrayList<Room> readAll() {
        RoomServlet roomServlet = new RoomServlet();
        return roomServlet.readAll();
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
