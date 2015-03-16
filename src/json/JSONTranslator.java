package json;

import models.Appointment;
import models.Group;
import models.Person;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by sharklaks on 09/03/15.
 */
public class JSONTranslator {

    public static ArrayList<Group> toGroupArrayList(JSONArray jsonArray) throws JSONException{
        ArrayList<Group> groups = new ArrayList<>();
        for (int i = 0 ; i <jsonArray.length() ; i ++){
            groups.add(toGroup(jsonArray.getJSONObject(i)));
        }
        return groups;
    }

    public static ArrayList<Person> toPersonArrayList(JSONArray jsonArray) throws JSONException{
        ArrayList<Person> persons = new ArrayList<>();
        for (int i = 0 ; i <jsonArray.length() ; i ++){
            persons.add(toPerson(jsonArray.getJSONObject(i)));
        }
        return persons;
    }

    public static ArrayList<Appointment> toAppointmentArrayList(JSONArray jsonArray) throws JSONException{
        ArrayList<Appointment> appointments = new ArrayList<>();
        for (int i = 0 ; i <jsonArray.length() ; i ++){
            appointments.add(toAppointment(jsonArray.getJSONObject(i)));
        }
        return appointments;
    }

    public static Group toGroup(JSONObject jsonObject) throws JSONException{
        Group group = new Group();
        if (group.getId() > 0)
            group.setId(jsonObject.getInt("id"));
        group.setName(jsonObject.getString("name"));

        if (group.getCalendarId() > 0)
            group.setCalendarId(jsonObject.getInt("Calendar_id"));

        if (group.getSuperGroupId() > 0)
            group.setSuperGroupId(jsonObject.getInt("Gruppe_id"));
        return group;
    }

    public static Person toPerson(JSONObject jsonObject) throws JSONException{
        Person person = new Person();
        if (person.getId() > 0)
            person.setId(jsonObject.getInt("id"));
        person.setEmail(jsonObject.getString("email"));
        person.setFirstName(jsonObject.getString("firstname"));
        person.setSurname(jsonObject.getString("surname"));
        person.setPassword(jsonObject.getString("password"));
        person.setAlarmTime(jsonObject.getInt("alarm_time"));

        if (person.getCalendarId() > 0)
            person.setCalendarId(jsonObject.getInt("Calendar_id"));
        return person;
    }

    public static Appointment toAppointment(JSONObject jsonObject) throws JSONException{
        Appointment appointment = new Appointment();
        if( appointment.getId() > 0)
            appointment.setId(jsonObject.getInt("id"));
        appointment.setTittel(jsonObject.getString("tittel"));
        appointment.setDescription(jsonObject.getString("description"));
        appointment.setStartTime(jsonObject.getString("start_time"));
        appointment.setEndTime(jsonObject.getString("end_time"));
        if (appointment.getRoomId() > 0)
            appointment.setRoomId(jsonObject.getInt("Room_id"));
        if (appointment.getPersonId() > 0)
            appointment.setPersonId(jsonObject.getInt("Person_id"));
        return appointment;
    }

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
        jsonObject.put("participating", appointment.getParticipating());
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
            jsonObject.put("participating", appointment.getParticipating());
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
