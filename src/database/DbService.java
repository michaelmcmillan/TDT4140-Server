package database;

import java.util.ArrayList;

/**
 * Created by sharklaks on 02/03/15.
 */
public interface DbService {
    public boolean create(String entity);
    public String readOne (int id);
    public String readAll();
    public boolean update(int id, String newObject);
    public boolean delete(int id);
}
