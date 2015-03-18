package database;

import logger.Logger;
import models.Appointment;
import models.Group;
import models.Room;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by sharklaks on 18/03/15.
 */
public class RoomServlet implements DbService {

    DatabaseConnection database = DatabaseConnection.getInstance();

    public ArrayList<Room> readRecommendation(String startTime, String endTime, int seats){

        String select = "SELECT id as Room_id, name, seats FROM Room WHERE id not in ( " +
                "SELECT Room_id FROM Appointment " +
                "JOIN Room ON Room.id = Appointment.Room_id " +
                "WHERE (start_time > '" + startTime + "' and start_time < '"+ endTime + "')  " +
                "or    (end_time > '" + startTime + "' and end_time < '" + endTime + "') " +
        ") " +
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
        String select =
                "SELECT * from Room";
        try {

            PreparedStatement preppedStatement = null;
            preppedStatement = database.getConn().prepareStatement(select);

            ResultSet rows = preppedStatement.executeQuery();

            ArrayList<Room> rooms = new ArrayList<>();
            while (rows.next()) {
                Room room = new Room();
                room.setId(rows.getInt("id"));
                room.setName(rows.getString("name"));
                room.setSeats(rows.getInt("seats"));
                rooms.add(room);
            }
            return rooms;
        } catch (SQLException error) {
            Logger.console(error.getMessage());
            return null;
        }
    }

    @Override
    public boolean update(Object object) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
