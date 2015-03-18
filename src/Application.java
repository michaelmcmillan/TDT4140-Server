import alarm.Alarm;
import authentication.Authentication;
import authentication.AuthenticationException;
import email.Email;
import json.JSONTranslator;
import logger.Logger;
import models.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;

import static spark.Spark.*;

public class Application {

    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new Alarm(),0, 5000);


        setIpAddress("78.91.83.32");
        setPort(1343);

        before((request, response) -> {

            String authHeader = request.headers("Authorization");
            Authentication authentication = new Authentication(authHeader);

            if(!(request.uri().equals("/user") && request.requestMethod().equals("POST"))){
                try {
                    response.raw().setIntHeader("User", authentication.checkCredentials());
                } catch (AuthenticationException authError) {
                    Logger.console(authError.getMessage(), request.ip());
                    halt(401, "{\"message\": \"Du er ikke autentisert.\"}");
                }
            }

        });

        after((req, res) -> {
            res.raw().setHeader("User", null);
            res.type("application/json");
        });

        get("/user", (req, res) -> {
            int userId = Integer.parseInt(res.raw().getHeader("User"));

            Person person = new Person();
            person.read(userId);


            ArrayList<Person> persons = Person.getAll();
            ArrayList<Person> personsToReturn = new ArrayList<Person>();
            for (Person user : persons){
                if (user.getId() != userId)
                    personsToReturn.add(user);
            }

            return JSONTranslator.toJSONPersons(personsToReturn);
        });

        get("/user/me", (req, res) -> {
            int userId = Integer.parseInt(res.raw().getHeader("User"));
            Person person = new Person();
            person.read(userId);
            return JSONTranslator.toJSON(person).toString();
        });



        get("/user/appointments/:fromyyyyMMdd/:toyyyyMMdd", (req, res) -> {
            int userId = Integer.parseInt(res.raw().getHeader("User"));
            Person person = new Person();
            person.read(userId);

            String fromDate = req.params(":fromyyyyMMdd");
            String toDate = req.params(":toyyyyMMdd");

            ArrayList<Appointment> appointments = person.getAllAttendingAppointments();
            ArrayList<Appointment> appointmentsInterval = new ArrayList<Appointment>();

            for (Appointment appointment : appointments) {
                String appointmentStart = appointment.getStartTime().split(" ")[0];
                String appointmentEnd = appointment.getEndTime().split(" ")[0];

                if (appointmentStart.compareTo(fromDate) >= 0 && appointmentEnd.compareTo(toDate) <= 0)
                    appointmentsInterval.add(appointment);
            }

            return JSONTranslator.toJSONAppointments(appointmentsInterval);
        });

        get("/calendar/:calendarId/appointments/:fromyyyyMMdd/:toyyyyMMdd", (req, res) ->{
            int calendarId = Integer.parseInt(req.params(":calendarId"));
            int userId = Integer.parseInt(res.raw().getHeader("User"));

            String fromDate = req.params(":fromyyyyMMdd");
            String toDate = req.params(":toyyyyMMdd");

            Person person = new Person();
            person.read(userId);

            ArrayList<Appointment> combinedAppointments = new ArrayList<Appointment>();
            ArrayList<Appointment> appointmentsInterval = new ArrayList<Appointment>();


            if (calendarId == person.getCalendarId()){
                /* HER MERGES PRIVAT KALENDER MED ALLE DELTAKENDE APPOINTMENTS */
                ArrayList<Appointment> attendingAppointments = person.getAllAttendingAppointments();

                Calendar personalCalendar = new Calendar();
                personalCalendar.read(calendarId);

                ArrayList<Appointment> appointmentsInCalendar = personalCalendar.getAllAppointments(userId);
                combinedAppointments.addAll(personalCalendar.getAllAppointments(userId));
                combinedAppointments.addAll(attendingAppointments);

                String hei = "";
            }else {
                /* HER MERGES SUPERGROUP MED SUBGROUP */

                Calendar subCalendar = new Calendar();
                Calendar superCalendar = new Calendar();
                subCalendar.setId(calendarId);

                int superGroupId = subCalendar.getSuperGroupId();
                if (superGroupId > 0){
                    Group group = new Group();
                    group.read(superGroupId);
                    int superCalendarId = group.getCalendarId();
                    superCalendar.setId(superCalendarId);
                }


                ArrayList<Appointment> appointmentsFromSubGroup = subCalendar.getAllAppointments(userId);
                ArrayList<Appointment> appointmentsFromSuperGroup = superCalendar.getAllAppointments(userId);

                combinedAppointments.addAll(appointmentsFromSuperGroup);
                combinedAppointments.addAll(appointmentsFromSubGroup);
            }

            for (Appointment appointment: combinedAppointments) {

                String appointmentStart = appointment.getStartTime().split(" ")[0];
                String appointmentEnd   = appointment.getEndTime().split(" ")[0];

                if (appointmentStart.compareTo(fromDate) >= 0 && appointmentEnd.compareTo(toDate) <= 0)
                    appointmentsInterval.add(appointment);
            }

            return JSONTranslator.toJSONAppointments(appointmentsInterval);
        });

        get("/appointment/:appointmentId", (req, res) ->{

            Appointment appointment = new Appointment();
            appointment.read(Integer.parseInt(req.params("appointmentId")));
            return JSONTranslator.toJSON(appointment);
        });

        get("/group/:groupId", (req, res) ->{
            Group group = new Group();
            group.read(Integer.parseInt(req.params("groupId")));
            return JSONTranslator.toJSON(group);
        });

        get("/user/appointments", (req, res) -> {
            int userId = Integer.parseInt(res.raw().getHeader("User"));

            Person person = new Person();
            person.read(userId);
            ArrayList<Appointment> appointments = person.getAllAttendingAppointments();

            return JSONTranslator.toJSONAppointments(appointments);
        });

        get("/user/groups", (req, res) -> {
            int userId = Integer.parseInt(res.raw().getHeader("User"));

            Person person = new Person();
            person.read(userId);
            ArrayList<Group> groups = person.getAllGroups();
            return JSONTranslator.toJSONGroups(groups);
        });

        post("/user", (req, res) -> {
            Person newUser = JSONTranslator.toPerson(new JSONObject(req.body()));
            if (newUser.create())
                return "{ message: \"Success\"}";
            return "{ message: \"fail\"}";
        });

        post("/group", (req, res) -> {
            int userId = Integer.parseInt(res.raw().getHeader("User"));

            Calendar calendar = new Calendar();
            calendar.create();

            Group group = JSONTranslator.toGroup(new JSONObject(req.body()));
            group.setCalendarId(calendar.getId());
            group.create();
            group.addUser(userId);

            return JSONTranslator.toJSON(group);
        });

        post("/appointment/:calendarId", (req, res) -> {
            int userId = Integer.parseInt(res.raw().getHeader("User"));

            Appointment appointment = JSONTranslator.toAppointment(new JSONObject(req.body()));
            appointment.setPersonId(userId);
            appointment.create(Integer.parseInt(req.params("calendarId")));
            return appointment.invite(userId);
        });

        post("/appointment/:appointmentId/participants", (req, res) -> {
            int userId = Integer.parseInt(res.raw().getHeader("User"));

            Appointment appointment = new Appointment();
            appointment.setId(Integer.parseInt(req.params("appointmentId")));
            return appointment.invite(userId);
        });

        put("/appointment/:appointmentId", (req, res) -> {
            int appointmentId = Integer.parseInt(req.params("appointmentId"));

            Appointment inputAppointment = JSONTranslator.toAppointment(new JSONObject(req.body()));

            Appointment appointment = new Appointment();

            appointment.read(appointmentId);
            appointment.setTittel(inputAppointment.getTittel());
            appointment.setStartTime(inputAppointment.getStartTime());
            appointment.setEndTime(inputAppointment.getEndTime());
            appointment.setDescription(inputAppointment.getDescription());

            appointment.update();
            return "{ \"message:\" \"Appointment successfully updated\"}";
        });

        put("/group/:groupId", (req, res) -> {
            Group inputGroup = JSONTranslator.toGroup(new JSONObject(req.body()));

            Group group = new Group();
            group.read(Integer.parseInt(req.params("groupId")));

            group.setName(inputGroup.getName());
            group.update();

            return "{ \"message:\" \"Group successfully updated\"}";
        });

        delete("/appointment/:appointmentId/participants", (req, res) -> {
            int userId = Integer.parseInt(res.raw().getHeader("User"));

            Appointment appointment = new Appointment();
            appointment.setId(Integer.parseInt(req.params("appointmentId")));
            return appointment.removeUser(userId);
        });

        get("/group/:groupId/members", (req, res) -> {
            Group group = new Group();
            group.setId(Integer.parseInt(req.params("groupId")));
            return JSONTranslator.toJSONPersons(group.getAllMembers());
        });

        get("/supergroups", (req,res) -> {
            int userId = Integer.parseInt(res.raw().getHeader("User"));
            return JSONTranslator.toJSONGroups(Group.getAllSupergroups(userId));
        });

        post("/group/:groupId/members", (req, res) -> {
            Group group = new Group();
            group.setId(Integer.parseInt(req.params("groupId")));

            JSONArray array = new JSONArray(req.body());
            for (int i = 0; i < array.length(); i++) {
                int userIdToAdd = array.getJSONObject(i).getInt("id");
                String emailToAdd = array.getJSONObject(i).getString("email");
                group.addUser(userIdToAdd);
//                new Email(emailToAdd, "Lagt til", "lol").send();
            }

            return "";
        });

        delete("/group/:groupId", (req, res) -> {
            int userId = Integer.parseInt(res.raw().getHeader("User"));

            Group group = new Group();
            group.setId(Integer.parseInt(req.params("groupId")));

            return group.removeUser(userId);
        });

        delete("/appointment/:appointmentId", (req, res) ->{
            int userId = Integer.parseInt(res.raw().getHeader("User"));
            Appointment appointment = new Appointment();
            appointment.read(Integer.parseInt(req.params("appointmentId")));

            if (appointment.getPersonId() == userId)
                appointment.delete();
            return "";
        });

        get("/room/:seats/:appointmentId", (req, res) -> {

            ArrayList<Room> rooms = Room.readRecommendation(Integer.parseInt(req.params("appointmentId")), Integer.parseInt(req.params("seats")));
            return JSONTranslator.toJSONRooms(rooms);
        });
    }
}
