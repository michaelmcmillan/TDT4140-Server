package models;


import database.AppointmentsServletDao;

public class Appointment {

    AppointmentsServletDao appServlet;

    public Appointment(){
        appServlet = new AppointmentsServletDao();
    }

}
