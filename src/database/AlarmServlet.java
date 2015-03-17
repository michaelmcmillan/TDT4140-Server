package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by sharklaks on 17/03/15.
 */
public class AlarmServlet {

    static DatabaseConnection database = DatabaseConnection.getInstance();

    public static ResultSet readAll(){
        String select = "SELECT email, Appointment.tittel, Person_has_Appointment.Person_id, Appointment_id, alarm_time, alarm_time*60 as alarm_seconds, unix_timestamp(start_time) - unix_timestamp(now()) as time_to_ring " +
                "FROM Person_has_Appointment " +
                "JOIN Person ON Person_has_Appointment.Person_id=Person.id " +
                "JOIN Appointment ON Person_has_Appointment.Appointment_id=Appointment.id " +
                "HAVING time_to_ring < alarm_seconds AND time_to_ring > 0";

        try {

            PreparedStatement preppedStatement = null;
            preppedStatement = database.getConn().prepareStatement(select);

            ResultSet rows = preppedStatement.executeQuery();
            return rows;
        } catch (Exception e){
            System.out.println("Database error: " + e.getMessage());
            return null;
        }
    }
}
