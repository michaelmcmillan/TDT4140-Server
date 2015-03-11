package authentication;

import models.Person;
import logger.Logger;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Base64;

public class Authentication {

    private ArrayList<Person> users = new ArrayList<Person>();
    private final String authHeader;

    public Authentication (String authHeader) {
        this.authHeader = authHeader;
        users = Person.getAll();
    }

    public String[] parseCredentials () {
        String authHeaderToBeDecoded = "";

        // Check if the Authorization header is correctly formatted
        if (this.authHeader == null
        ||  this.authHeader.toLowerCase().startsWith("basic") == false
        ||  this.authHeader.split(" ").length != 2)
            throw new AuthenticationException("Ugyldig format på autentiseringsstrengen.");
        else {
            authHeaderToBeDecoded = authHeader.split(" ")[1];
        }
        // Decode the base64 string
        try {
            byte[] decoded = Base64.getDecoder().decode(authHeaderToBeDecoded);
            authHeaderToBeDecoded = new String(decoded, "UTF-8");
        } catch (UnsupportedEncodingException error) {
            authHeaderToBeDecoded = "null:null";
        }

        // Split out the parts by colon
        // username:password
        return authHeaderToBeDecoded.split(":");
    }

    public int checkCredentials () {

        // Get the username and password
        String username = parseCredentials()[0];
        String password = parseCredentials()[1];

        // Check if the username exists
        for (Person user : users)
            if (user.getEmail().equals(username) && user.getPassword().equals(password))
                return user.getId();

        throw new AuthenticationException("Finnes ingen bruker med det brukernavnet og passordet.");
    }
}
