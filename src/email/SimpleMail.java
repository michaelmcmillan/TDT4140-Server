package email;

/**
 * Created by michaelmcmillan on 18.03.15.
 */
public class SimpleMail {
    public String to;
    public String subject;
    public String body;

    public SimpleMail(String to, String subject, String body) {
        this.to = to;
        this.subject = subject;
        this.body = body;
    }
}
