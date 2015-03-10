package database;

import java.util.ArrayList;


/**
 * Created by sharklaks on 02/03/15.
 */
public interface DbService<T> {
    public boolean create(T entity);
    public T readOne (int id);
    public ArrayList<T> readAll();
    public boolean update(T newObject);
    public boolean delete(int id);
}
