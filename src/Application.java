import authentication.Authentication;
import authentication.AuthenticationException;

import static spark.Spark.*;

public class Application {
    public static void main(String[] args) {

        setPort(1999);

        before((request, response) -> {
            String authHeader = request.headers("Authorization");
            Authentication authentication = new Authentication(authHeader);

            try {
                authentication.checkCredentials();
            } catch (AuthenticationException authError) {
                System.out.println(request.ip() + ": " + authError.getMessage());
                halt(401, "Du er ikke autentisert.");
            }
        });

        get("/", (req, res) -> {
            return "It works!";
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