package logger;

import authentication.AuthenticationException;


import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.HashMap;

public class Logger {

    public static void console (String message) {
        System.out.println("[" + HHMM() + "] " + message);
    }

    public static void console (String message, String ip) {
        console(ip + ": " + message);
    }

    public static String HHMM () {
        Calendar calendar = Calendar.getInstance();
        return new SimpleDateFormat("HH:mm:ss").format(calendar.getTime());
    }

}
