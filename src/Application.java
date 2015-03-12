import authentication.Authentication;
import authentication.AuthenticationException;
import database.DatabaseConnection;
import email.Email;
import json.JSONTranslator;
import logger.Logger;
import models.Appointment;
import models.Calendar;
import models.Group;
import models.Person;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            Date fromDate = format.parse(req.params(":fromyyyyMMdd"));
            Date toDate = format.parse(req.params(":toyyyyMMdd"));

            ArrayList<Appointment> appointments = person.getAllAppointments();
            ArrayList<Appointment> appointmentsInterval = new ArrayList<Appointment>();

            for (Appointment appointment: appointments) {
                Date appointmentStart = format.parse(appointment.getStartTime());
                Date appointmentEnd   = format.parse(appointment.getEndTime());

                if (appointmentStart.after(fromDate) && appointmentEnd.before(toDate))
                    appointmentsInterval.add(appointment);
            }

            return JSONTranslator.toJSONAppointments(appointmentsInterval);
        });


        get("/appointment/:", (req, res) ->{

            Appointment appointment = new Appointment();
            appointment.read(Integer.parseInt(req.params("appointmentId")));
            return JSONTranslator.toJSON(appointment);
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
            if(newUser.create())
                return "{ message: \"Success\"}";
            return "{ message: \"fail\"}";
        });

        post("/appointment", (req, res) ->{
            int userId = Integer.parseInt(res.raw().getHeader("User"));
            Appointment appointment = JSONTranslator.toAppointment(new JSONObject(req.body()));
            appointment.setPersonId(userId);
            if (appointment.create())
                return "{ message: \"Succes\"}";
            return "{ message: \"fail\"}";
        });

        post("/appointment/:calendarId", (req, res) ->{
            int userId = Integer.parseInt(res.raw().getHeader("User"));

            Appointment appointment = JSONTranslator.toAppointment(new JSONObject(req.body()));
            appointment.setPersonId(userId);
            return appointment.create(Integer.parseInt(req.params("calendarId")));
        });
    }
}
