import authentication.Authentication;
import authentication.AuthenticationException;
<<<<<<< HEAD
import email.Email;

=======
import logger.Logger;
>>>>>>> logger
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
            new Email("lol", "lol", "lol", "lele");

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
