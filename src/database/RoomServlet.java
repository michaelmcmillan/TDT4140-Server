package database;

import models.Appointment;
import models.Room;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by sharklaks on 18/03/15.
 */
public class RoomServlet implements DbService {

    DatabaseConnection database = DatabaseConnection.getInstance();

    public ArrayList<Room> readRecommendation(int appointmentId, int seats){

        Appointment appointment = new Appointment();
        appointment.read(appointmentId);

        long startTime = RoomServlet.dateToUnix(appointment.getStartTime());
        long endTime = RoomServlet.dateToUnix(appointment.getEndTime());

        String select = "SELECT id as Room_id, name, seats FROM Room\n" +
                "WHERE id not in ( \n" +
                "SELECT Room_id\n" +
                "FROM Appointment\n" +
                "JOIN Room\n" +
                "ON Room.id = Appointment.Room_id\n" +
                "WHERE (unix_timestamp(start_time) > " + startTime + " and unix_timestamp(end_time) < " + endTime + ") or\n" +
                "\t\t unix_timestamp(start_time) > " + startTime + " and unix_timestamp(start_time) > "+ endTime + " or\n" +
                "         unix_timestamp(end_time) > " + startTime + " and unix_timestamp(end_time) < " + endTime + "\n" +
                ")\n" +
                "and seats >=" + seats + " " +
                "ORDER BY seats asc";

        try {

            ArrayList<Room> rooms = new ArrayList<>();

            PreparedStatement preppedStatement = null;
            preppedStatement = database.getConn().prepareStatement(select);

            ResultSet rows = preppedStatement.executeQuery();

            while (rows.next()){
                Room room = new Room();

                room.setId(rows.getInt("Room_id"));
                room.setName(rows.getString("name"));
                room.setSeats(rows.getInt("seats"));
                rooms.add(room);

            }
            return rooms;
        } catch (Exception e){
            System.out.println("Database error: " + e.getMessage());
            return null;
        }
    }


    @Override
    public boolean create(Object entity) {
//        return ServletHelper.create("Room", )
        return true;
    }

    @Override
    public Object readOne(int id) {
        return null;
    }

    @Override
    public ArrayList readAll() {
        return null;
    }

    @Override
    public boolean update(Object object) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    public static long dateToUnix(String date){
        try {
            return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date).getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
