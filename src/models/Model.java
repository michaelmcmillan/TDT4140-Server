package models;

import org.json.JSONException;
import org.json.JSONObject;


public interface Model {

    public void create();
    public void read(int id);
    public void update();
    public void delete();

}
