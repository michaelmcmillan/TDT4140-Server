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
import org.json.JSONObject;

import java.util.ArrayList;

import static spark.Spark.*;

public class Application {

    public static void main(String[] args) {

        //setIpAddress("78.91.74.53");
        setPort(1337);

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
            return "{\"success\": true}";
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
    }
}
