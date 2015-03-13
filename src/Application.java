import authentication.Authentication;
import authentication.AuthenticationException;
import json.JSONTranslator;
import logger.Logger;
import models.Appointment;
import models.Calendar;
import models.Group;
import models.Person;
import org.json.JSONArray;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import static spark.Spark.*;

public class Application {

    public static void main(String[] args) {

        //setIpAddress("78.91.74.53");
        setPort(1338);

        before((request, response) -> {

            String authHeader = request.headers("Authorization");
            Authentication authentication = new Authentication(authHeader);

            try {
                response.raw().setIntHeader("User", authentication.checkCredentials());
            } catch (AuthenticationException authError) {
                Logger.console(authError.getMessage(), request.ip());
                halt(401, "{\"message\": \"Du er ikke autentisert.\"}");
            }
        });

        after((req, res) -> {
            res.raw().setHeader("User", null);
            res.type("application/json");
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

            ArrayList<Appointment> appointments = person.getAllAppointments();
            ArrayList<Appointment> appointmentsInterval = new ArrayList<Appointment>();

            for (Appointment appointment: appointments) {
                String appointmentStart = appointment.getStartTime().split(" ")[0];
                String appointmentEnd   = appointment.getEndTime().split(" ")[0];

                if (appointmentStart.compareTo(fromDate) >= 0 && appointmentEnd.compareTo(toDate) <= 0)
                    appointmentsInterval.add(appointment);
            }

            return JSONTranslator.toJSONAppointments(appointmentsInterval);
        });

        get("/calendar/:calendarId/appointments/:fromyyyyMMdd/:toyyyyMMdd", (req, res) ->{
            int calendarId = Integer.parseInt(req.params(":calendarId"));

            Calendar calendar = new Calendar();
            calendar.setId(calendarId);

            String fromDate = req.params(":fromyyyyMMdd");
            String toDate = req.params(":toyyyyMMdd");

            ArrayList<Appointment> appointments = calendar.getAllAppointments();
            ArrayList<Appointment> appointmentsInterval = new ArrayList<Appointment>();

            for (Appointment appointment: appointments) {

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
            ArrayList<Appointment> appointments = person.getAllAppointments();

            return JSONTranslator.toJSONAppointments(appointments);
        });

        get("/user/groups", (req, res) -> {
            int userId = Integer.parseInt(res.raw().getHeader("User"));

            Person person = new Person();
            person.read(userId);
            ArrayList<Group> groups = person.getAllGroups();
            return JSONTranslator.toJSONGroups(groups);
        });

        get("/calendar/:calendarId/appointments", (req, res) -> {
            Calendar calendar = new Calendar();
            calendar.setId(Integer.parseInt(req.params("calendarId")));
            ArrayList<Appointment> appointments = calendar.getAllAppointments();
            return JSONTranslator.toJSONAppointments(appointments);
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

        post("/appointment/:calendarId", (req, res) ->{
            int userId = Integer.parseInt(res.raw().getHeader("User"));

            Appointment appointment = JSONTranslator.toAppointment(new JSONObject(req.body()));
            appointment.setPersonId(userId);
            return appointment.create(Integer.parseInt(req.params("calendarId")));
        });

        post("/appointment/:appointmentId", (req, res) -> {
            int userId = Integer.parseInt(res.raw().getHeader("User"));

            Appointment appointment = new Appointment();
            appointment.setId(Integer.parseInt(req.params("appointmentId")));
            return appointment.invite(userId);
        });

        delete("/appointment/:appointmentId", (req, res) -> {
            int userId = Integer.parseInt(res.raw().getHeader("User"));

            Appointment appointment = new Appointment();
            appointment.setId(Integer.parseInt(req.params("appointmentId")));
            return appointment.removeUser(userId);
        });

        post("/group/:groupId/members", (req, res) -> {
            ArrayList<Person> persons = JSONTranslator.toPersonArrayList(new JSONArray(req.body()));

            Group group = new Group();
            group.setId(Integer.parseInt(req.params("groupId")));

            for (Person person : persons){
                group.addUser(person.getId());
            }
            return JSONTranslator.toJSONPersons(persons);
        });

        delete("/group/:groupId/:userIdToRemove", (req, res) -> {
            int userIdToRemove = Integer.parseInt(req.params("userIdToRemove"));

            Group group = new Group();
            group.setId(Integer.parseInt(req.params("groupId")));

            return group.removeUser(userIdToRemove);
        });
    }
}
