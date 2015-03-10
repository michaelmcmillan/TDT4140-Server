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

import java.util.ArrayList;

import static spark.Spark.*;

public class Application {

    public static void main(String[] args) {

        //setIpAddress("78.91.74.53");
        setPort(2009);

//        before((request, response) -> {
//            String authHeader = request.headers("Authorization");
//            Logger.console(authHeader);
//            Authentication authentication = new Authentication(authHeader);
//
//            try {
//                authentication.checkCredentials();
//            } catch (AuthenticationException authError) {
//                Logger.console(authError.getMessage(), request.ip());
//                halt(401, "Du er ikke autentisert.");
//            }
//
//        });

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

        get("/user/:userId", (req, res) -> {
            Person person = new Person();
            person.read(Integer.parseInt(req.params("userId")));
            return JSONTranslator.toJSON(person);
        });

        get("/user/:userId/appointments", (req, res) -> {
            Person person = new Person();
            person.read(Integer.parseInt(req.params("userId")));
            ArrayList<Appointment> appointments = person.getAllAppointments();
            return JSONTranslator.toJSONAppointments(appointments);
        });

        get("/user/:userId/groups", (req, res) -> {
            Person person = new Person();
            person.read(Integer.parseInt(req.params("userId")));
            ArrayList<Group> groups = person.getAllGroups();
            return JSONTranslator.toJSONGroups(groups);
        });

        get("/calendar/:calendarId/appointments", (req, res) -> {
            Calendar calendar = new Calendar();
            calendar.setId(Integer.parseInt(req.params("calendarId")));
            ArrayList<Appointment> appointments = calendar.getAllAppointments();
            return JSONTranslator.toJSONAppointments(appointments);
        });

        get("/api/v1/user", (req, res) -> {
            res.type("application/json");
            return "{}";
        });

        get("/api/v1/user/appointments", (req, res) -> {
            res.type("application/json");
            return "{}";
        });
    }
}
