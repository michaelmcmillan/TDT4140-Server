package email;

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
    }
    
    public boolean send () {
        return true;
    }

}
