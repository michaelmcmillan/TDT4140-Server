import authentication.Authentication;
import authentication.AuthenticationException;
import database.ModelTest;
import email.Email;
import logger.Logger;
import static spark.Spark.*;

public class Application {
    public static void main(String[] args) {




        setPort(2000);

        before((request, response) -> {
            String authHeader = request.headers("Authorization");
            Authentication authentication = new Authentication(authHeader);

            try {
                authentication.checkCredentials();
            } catch (AuthenticationException authError) {
                Logger.console(authError.getMessage(), request.ip());
                //halt(401, "Du er ikke autentisert.");
            }
        });

        get("/", (req, res) -> {
            ModelTest ok = new ModelTest();
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
