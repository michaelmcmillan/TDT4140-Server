package alarm;

import database.AlarmServlet;
import email.Email;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.TimerTask;

/**
 * Created by sharklaks on 17/03/15.
 */
public class Alarm extends TimerTask {
    HashMap<String, String> map = new HashMap<>();

    @Override
    public void run() {

        ResultSet rows = AlarmServlet.readAll();

        try {
            while(rows.next()){
                String key = rows.getString("Person_id") + "." + rows.getString("Appointment_id");
                if (!map.containsKey(key) && rows.getInt("alarm_time") != 0){
                    map.put(key, "sent");
                    new Email(rows.getString("email"), "Avtale begynner snart", "Avtale " + rows.getString("tittel") + " begynner om " + rows.getInt("alarm_seconds")/60 + " minutter.").send();
                    System.out.println("Sender mail " + rows.getString("tittel"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
