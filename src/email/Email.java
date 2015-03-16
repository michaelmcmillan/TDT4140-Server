package email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;

public class Email {
    
    private String subject;
    private List<String> to;
    private String from = "yolo.fellesprosjekt@gmail.com";
    private String body;

    public Email (ArrayList<String> to, String subject, String body) {
        this.to = to;
        this.subject = subject;
        this.body = body;
    }

    public Email (String to, String subject, String body) {
        this(new ArrayList<String>(), subject, body);
        this.to.add(to);
    }
    
    public boolean send () {
        final String username = this.from;
        final String password = "fellesprosjekternoedrit";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            for (String recipient : to) {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(this.from));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
                message.setSubject(this.subject);
                message.setText(this.body);
                Transport.send(message);
            }

            System.out.println("Done");

        } catch (MessagingException mex) {
            System.out.println("\n--Exception handling in Email.java");

            mex.printStackTrace();
            System.out.println();
            Exception ex = mex;
            do {
                if (ex instanceof SendFailedException) {
                    SendFailedException sfex = (SendFailedException) ex;
                    Address[] invalid = sfex.getInvalidAddresses();
                    if (invalid != null) {
                        System.out.println("    ** Invalid Addresses");
                        for (int i = 0; i < invalid.length; i++)
                            System.out.println("         " + invalid[i]);
                    }
                    Address[] validUnsent = sfex.getValidUnsentAddresses();
                    if (validUnsent != null) {
                        System.out.println("    ** ValidUnsent Addresses");
                        for (int i = 0; i < validUnsent.length; i++)
                            System.out.println("         " + validUnsent[i]);
                    }
                    Address[] validSent = sfex.getValidSentAddresses();
                    if (validSent != null) {
                        System.out.println("    ** ValidSent Addresses");
                        for (int i = 0; i < validSent.length; i++)
                            System.out.println("         " + validSent[i]);
                    }
                }
                System.out.println();
                if (ex instanceof MessagingException)
                    ex = ((MessagingException) ex).getNextException();
                else
                    ex = null;
            } while (ex != null);
        }

        return true;
    }
}
