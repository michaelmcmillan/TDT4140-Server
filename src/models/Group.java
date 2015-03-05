package models;


import database.GroupsServletDao;
import org.json.JSONException;
import org.json.JSONObject;

public class Group implements Model{

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

    public void pullFromDatabase (int id){
        groupServlet = new GroupsServletDao();
        Group group = (Group)groupServlet.readOne(id);

        this.id = group.getId();
        this.name = group.getName();
        this.calendarId = group.getCalendarId();

        if (group.getSuperGroupId() == 0)
            this.superGroupId = group.getSuperGroupId();
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("name", name);
        jsonObject.put("Calendar_id", calendarId);
        jsonObject.put("Gruppe_id", superGroupId);
        return jsonObject;
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