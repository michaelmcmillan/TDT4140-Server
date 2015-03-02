import static spark.Spark.*;
import java.util.Base64;

public class HelloWorld {
    public static void main(String[] args) {

        setPort(1999);

        before((request, response) -> {

            String authHeader = request.headers("Authorization");

            if (authHeader == null
            ||  authHeader.startsWith("Basic") == false
            ||  authHeader.split(" ").length != 2)
                halt(401, "Ugyldig format på autentiseringsstrengen.");
            else
                authHeader = authHeader.split(" ")[1];

            byte[] decoded = Base64.getDecoder().decode(authHeader);
            authHeader = new String(decoded, "UTF-8");
            String[] authParts = authHeader.split(":");

            String username = (String) authParts[0];
            String password = (String) authParts[1];

            if (!username.equals("mike") && !password.equals("hello"))
                halt(401, "Feil brukernavn eller passord.");
        });

        get("/hello", (req, res) -> {
            return "what is going on";
        });
    }
}