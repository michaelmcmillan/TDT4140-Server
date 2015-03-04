package database;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sharklaks on 02/03/15.
 */
public interface DbService {
    public boolean create(String entity);
    public JSONObject readOne (int id);
    public JSONArray readAll();
    public boolean update(int id, String newObject);
    public boolean delete(int id);
}
