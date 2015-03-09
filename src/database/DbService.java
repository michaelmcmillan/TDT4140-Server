package database;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * Created by sharklaks on 02/03/15.
 */
public interface DbService<T> {
    public boolean create(T entity);
    public T readOne (int id);
    public boolean update(T newObject);
    public boolean delete(int id);
}
