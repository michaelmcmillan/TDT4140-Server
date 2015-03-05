package models;

import org.json.JSONException;
import org.json.JSONObject;


public interface Model {

    public JSONObject toJSON() throws JSONException;
}
