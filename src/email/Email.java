package email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class Email {
    
    private String subject;
    private String to;
    private String from;
    private String body;



    public Email (String to, String from, String subject, String body) {
        this.to = to; 
        this.from = from; 
        this.subject = subject; 
        this.body = body;

        send();
    }
    
    public boolean send () {

        String to = "email@michaelmcmillan.net";
        String from = "email@michaelmcmillan.net";
        String host = "gmail.com";
        boolean debug = true;

        // create some properties and get the default Session
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        if (debug) props.put("mail.debug", debug);

        Session session = Session.getInstance(props, null);
        session.setDebug(debug);

        try {
            // create a message
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(to)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject("JavaMail APIs Test");
            msg.setSentDate(new Date());
            // If the desired charset is known, you can use
            // setText(text, charset)
            msg.setText("hehehhe");

            Transport.send(msg);
        } catch (MessagingException mex) {
            System.out.println("\n--Exception handling in msgsendsample.java");

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
