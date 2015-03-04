package models;

import database.CalendarServletDao;

/**
 * Created by sharklaks on 04/03/15.
 */
public class Calendar {

    private int id;

    public void createEntity(){
        CalendarServletDao servlet = new CalendarServletDao();
        servlet.create(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
