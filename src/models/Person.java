package models;


import database.PersonsServletDao;
import org.json.JSONObject;

public class Person implements Model{

    private PersonsServletDao personsServlet;

    private String email;
    private String firstName;
    private String surName;
    private String password;
    private int alarmTime;
    private int calendarId;

    private Calendar calendar;

    public Person() {
        email = "odin@loker.no";
        firstName = "O";
        surName = "Dawq";
        password = "heisann";
        int alarmTime = -1;
        calendar = new Calendar();
        calendarId = calendar.getId();

        //************FJERN QUOTES****************
        personsServlet.create("this");
        //****************************************
    }

    public Person(int id){
//        Person readPerson = personsServlet.readOne(id);
//        this.id = id;
//        this.firstName = readPerson.firstName;
//        this.surName = readPerson.surName;
//        this.password = readPerson.password;
//        this.alarmTime = readPerson.alarmTime;
//        this.calendarId = readPerson.calendarId;
    }

    public Person(JSONObject jsonObject){

    }

    @Override
    public JSONObject toJSON() {
        return null;
    }





    /*GETTERS AND SETTERS */
    public void setId(int id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public int getAlarmTime() {
        return alarmTime;
    }
    public void setAlarmTime(int alarmTime) {
        this.alarmTime = alarmTime;
    }
    public int getCalendar_id() {
        return Calendar_id;
    }
    public void setCalendar_id(int calendar_id) {
        Calendar_id = calendar_id;
    }
}