package models;


import database.PersonsServletDao;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

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

    private ArrayList<Group> groups;
    private ArrayList<Appointment> appointments;



    public Person() {
        personsServlet = new PersonsServletDao();
    }

    public void addAppointment(Appointment appointment){
        appointments.add(appointment);
    }

    public ArrayList<Group> getAllGroups(){
        return personsServlet.readAllGroups(id);
    }

    public ArrayList<Appointment> getAllAppointments(){
        return personsServlet.readAllAppointments(id);
    }

    public void addGroup(Group group){
        groups.add(group);
    }


    private void fetchAppointments(){

    }

    private void fetchGroups(){

    }

    public JSONArray appointmentsToJSON(){
        return new JSONArray();
    }

    public JSONArray groupsToJSON(){
        return new JSONArray();
    }



    //*******************OVERRIDE METHODS****************************
    @Override
    public void create(){
        personsServlet = new PersonsServletDao();
        calendar = new Calendar();
        calendar.create();
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
    public void delete(){
        personsServlet.delete(id);
    }

    @Override
    public HashMap<String, Object> toHashMap() {
        return null;
    }

    //*************************************************************




    //*******************STATIC METHODS****************************
    public static ArrayList<Person> getAll (){
        PersonsServletDao personsServlet = new PersonsServletDao();
        return personsServlet.readAll();
    }
    //*************************************************************





    //*******************GETTERS AND SETTERS **********************
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
    public ArrayList<Group> getGroups() {
        return groups;
    }
    public void setGroups(ArrayList<Group> groups) {
        this.groups = groups;
    }
    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }
    public void setAppointments(ArrayList<Appointment> appointments) {
        this.appointments = appointments;
    }
    //*************************************************************

}