package authentication;

import models.Person;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Base64;

public class Authentication {

    private HashMap<String, String> users = new HashMap<String, String>();
    private String authHeader;

    public Authentication (String authHeader) {
        this.authHeader = authHeader;
        for (Person person: Person.getAll()) {
            users.put(person.getEmail(), person.getPassword());
        }
    }

    public String[] parseCredentials () {

        // Check if the Authorization header is correctly formatted
        // Basic *base64string*==
        if (authHeader == null
        ||  authHeader.startsWith("Basic") == false
        ||  authHeader.split(" ").length != 2)
            throw new AuthenticationException("Ugyldig format på autentiseringsstrengen.");
        else
            authHeader = authHeader.split(" ")[1];

        // Decode the base64 string
        try {
            byte[] decoded = Base64.getDecoder().decode(authHeader);
            authHeader = new String(decoded, "UTF-8");
        } catch (UnsupportedEncodingException error) {
            System.out.println("hehe");
            authHeader = "null:null";
        }

        // Split out the parts by colon
        // username:password
        return authHeader.split(":");
    }

    public boolean checkCredentials () {

        // Get the username and password
        String username = parseCredentials()[0];
        String password = parseCredentials()[1];

        // Check if the username exists
        if (!users.containsKey(username))
            throw new AuthenticationException("Finnes ingen bruker med det brukernavnet.");

        // Check if the password provided is valid
        if (!users.get(username).equals(password))
            throw new AuthenticationException("Feil passord oppgitt.");

        return true;
    }
}
