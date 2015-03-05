package models;


import database.PersonsServletDao;
import org.json.JSONException;
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
        personsServlet = new PersonsServletDao();
    }

    @Override
    public void create(){
        personsServlet = new PersonsServletDao();
        calendar = new Calendar();
        calendar.createEntity();
        calendarId = calendar.getId();
        personsServlet.create(this);
    }

    @Override
    public void read (int id){

        Person person = (Person)personsServlet.readOne(id);
        this.id = person.getId();
        this.email = person.getEmail();
        this.firstName = person.getFirstName();
        this.surName = person.getSurname();
        this.password = person.getPassword();
        this.alarmTime = person.getAlarmTime();
        this.calendarId = person.getCalendarId();
    }

    @Override
    public void update(){
        personsServlet.update(this);
    }


    @Override
    public void delete(){}

    @Override
    public JSONObject toJSON() throws JSONException{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("email", email);
        jsonObject.put("firstname", firstName);
        jsonObject.put("surname", surName);
        jsonObject.put("password", password);
        jsonObject.put("alarm_time", alarmTime);
        jsonObject.put("Calendar_id", calendarId);
        return jsonObject;
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