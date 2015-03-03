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
                halt(401, authError.getMessage());
            }
        });

        get("/hello", (req, res) -> {
            return "what is going on";
        });
    }
}