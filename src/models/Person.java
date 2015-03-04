package models;


import database.PersonsServletDao;
import org.json.JSONObject;

public class Person implements Model{

    private PersonsServletDao personsServlet;

    //****TABLE ATTRIBUTES****
    private int id;
    private String email;
    private String firstName;
    private String surName;
    private String password;
    private int alarmTime;
    private int calendarId;
    //************************

    private Calendar calendar;



    public Person() {

    }

    public void update(){
        personsServlet = new PersonsServletDao();
        personsServlet.update(this);
    }

    public void pullFromDatabase (int id){
        personsServlet = new PersonsServletDao();
        Person person = (Person)personsServlet.readOne(id);
        this.id = person.id;
        this.firstName = person.firstName;
        this.surName = person.surName;
        this.password = person.password;
        this.alarmTime = person.alarmTime;
        this.calendarId = person.calendarId;
    }

    public void createEntity(){
        personsServlet = new PersonsServletDao();
        calendar = new Calendar();
        calendar.createEntity();
        calendarId = calendar.getId();
        personsServlet.create(this);
    }

    public void createFromJson (JSONObject jsonObject){

    }

    @Override
    public JSONObject toJSON() {
        return null;
    }





    /*GETTERS AND SETTERS */
    public int getId() {
        return id;
    }
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
        return surName;
    }
    public void setSurname(String surname) {
        this.surName = surname;
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
    public int getCalendarId() {
        return calendarId;
    }
    public void setCalendarId(int calendar_id) {
        calendarId = calendar_id;
    }
}