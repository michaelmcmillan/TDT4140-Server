package models;

import database.CalendarServletDao;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sharklaks on 04/03/15.
 */
public class Calendar implements Model{

    private int id;

    public void create(){
        CalendarServletDao servlet = new CalendarServletDao();
        servlet.create(this);
    }

    @Override
    public void read(int id) {

    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }

    @Override
    public JSONObject toJSON() throws JSONException {
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
