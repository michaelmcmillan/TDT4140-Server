package models;


import database.GroupsServletDao;

public class Group {

    private GroupsServletDao groupServlet;

    private int id;
    private String name;
    private int calendarId;
    private int superGroupId;

    private Calendar calendar;

    public Group() {
        this.superGroupId = -1;
    }

    public void createEntity(){
        groupServlet = new GroupsServletDao();
        calendar = new Calendar();
        calendar.createEntity();
        calendarId = calendar.getId();
        groupServlet.create(this);
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