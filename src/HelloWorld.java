import static spark.Spark.*;
import java.util.Base64;
import java.util.HashMap;

public class HelloWorld {
    public static void main(String[] args) {

        setPort(1999);

        before((request, response) -> {

            // Fill in dummy users
            HashMap<String, String> users = new HashMap<String, String>();
            users.put("mike",   "hund");
            users.put("odin",   "katt");
            users.put("morten", "mus");
            users.put("lasse",  "fugl");

            // Check if the Authorization header is correctly formatted
            // Basic *base64string*==
            String authHeader = request.headers("Authorization");
            if (authHeader == null
            ||  authHeader.startsWith("Basic") == false
            ||  authHeader.split(" ").length != 2)
                halt(401, "Ugyldig format på autentiseringsstrengen.");
            else
                authHeader = authHeader.split(" ")[1];

            // Decode the base64 string
            byte[] decoded = Base64.getDecoder().decode(authHeader);
            authHeader = new String(decoded, "UTF-8");

            // Split out the parts by colon
            // username:password
            String[] authParts = authHeader.split(":");
            String username = (String) authParts[0];
            String password = (String) authParts[1];

            // Check if the username exists
            if (!users.containsKey(username))
                halt(401, "Finnes ingen bruker med det brukernavnet.");

            // Check if the correct password is provided
            if (!users.get(username).equals(password))
                halt(401, "Feil passord.");
        });

        get("/hello", (req, res) -> {
            return "what is going on";
        });
    }
}