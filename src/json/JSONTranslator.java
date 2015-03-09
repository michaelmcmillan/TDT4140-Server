package json;

import models.Appointment;
import models.Group;
import models.Person;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by sharklaks on 09/03/15.
 */
public class JSONTranslator {


    public static JSONObject toJSON(Person person) throws JSONException{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", person.getId());
        jsonObject.put("email", person.getEmail());
        jsonObject.put("firstname", person.getFirstName());
        jsonObject.put("surname", person.getSurname());
        jsonObject.put("password", person.getPassword());
        jsonObject.put("alarm_time", person.getAlarmTime());
        jsonObject.put("Calendar_id", person.getCalendarId());
        return jsonObject;
    }

    public static JSONObject toJSON(Group group) throws JSONException{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", group.getId());
        jsonObject.put("name", group.getName());
        jsonObject.put("Calendar_id", group.getCalendarId());
        jsonObject.put("Gruppe_id", group.getSuperGroupId());
        return jsonObject;
    }

    public static JSONObject toJSON(Appointment appointment) throws JSONException{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", appointment.getId());
        jsonObject.put("tittel", appointment.getTittel());
        jsonObject.put("description", appointment.getDescription());
        jsonObject.put("start_time", appointment.getStartTime());
        jsonObject.put("end_time", appointment.getEndTime());
        jsonObject.put("Person_id", appointment.getPersonId());
        jsonObject.put("Room_id", appointment.getRoomId());
        return jsonObject;
    }

    public static JSONArray toJSONAppointments(ArrayList<Appointment> appointments) throws JSONException{
        JSONArray jsonArray = new JSONArray();

        for (Appointment appointment : appointments){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", appointment.getId());
            jsonObject.put("tittel", appointment.getTittel());
            jsonObject.put("description", appointment.getDescription());
            jsonObject.put("start_time", appointment.getStartTime());
            jsonObject.put("end_time", appointment.getEndTime());
            jsonObject.put("Person_id", appointment.getPersonId());
            jsonObject.put("Room_id", appointment.getRoomId());
            jsonArray.put(jsonObject);
        }
        return jsonArray;
    }

    public static JSONArray toJSONPersons(ArrayList<Person> persons) throws JSONException{
        JSONArray jsonArray = new JSONArray();

        for (Person person : persons){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", person.getId());
            jsonObject.put("email", person.getEmail());
            jsonObject.put("firstname", person.getFirstName());
            jsonObject.put("surname", person.getSurname());
            jsonObject.put("password", person.getPassword());
            jsonObject.put("alarm_time", person.getAlarmTime());
            jsonObject.put("Calendar_id", person.getCalendarId());
            jsonArray.put(jsonObject);
        }
        return jsonArray;
    }

    public static JSONArray toJSONGroups(ArrayList<Group> groups) throws JSONException{
        JSONArray jsonArray = new JSONArray();
        for (Group group : groups){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", group.getId());
            jsonObject.put("name", group.getName());
            jsonObject.put("Calendar_id", group.getCalendarId());
            jsonObject.put("Gruppe_id", group.getSuperGroupId());
            jsonArray.put(jsonObject);
        }

        return jsonArray;
    }

}
