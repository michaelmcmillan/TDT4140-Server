package tests;

import email.Email;

import java.util.ArrayList;

/**
 * Created by Morten on 04.03.15.
 */
public class TestEmail {
    public static void main(String[] args) {
        System.out.println("Hei");
        ArrayList<String> recipients = new ArrayList<String>();
        recipients.add("mortengkleveland@gmail.com");
        recipients.add("mortengkleveland@gmail.com");
        Email email = new Email(recipients, "Mr. X", "Avtalen din er flyttet.", "Avtalen du hadde mellom 10.15 og 12.00 fredag 12. mars 2015 har blitt flyttet til mellom 12.15 og 14.00 fredag 12. mars.");
    }
}
