import authentication.Authentication;
import authentication.AuthenticationException;
import database.DatabaseConnection;
import email.Email;
import json.JSONTranslator;
import logger.Logger;
import models.Appointment;
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

        get("/", (req, res) -> {
            ArrayList<Person> folks = Person.getAll();

            res.type("application/json");
            return "{}";
        });

        get("/:userId/appointments", (req, res) -> {
            Person person = new Person();
            person.read(Integer.parseInt(req.params("userId")));
            ArrayList<Appointment> appointments = person.getAllAppointments();
            return JSONTranslator.toJSONAppointments(appointments);
        });

        get("/:userId/groups", (req, res) -> {
            Person person = new Person();
            person.read(Integer.parseInt(req.params("userId")));
            ArrayList<Group> groups = person.getAllGroups();
            return JSONTranslator.toJSONGroups(groups);
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
