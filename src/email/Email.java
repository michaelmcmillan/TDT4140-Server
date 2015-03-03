package authentication;

public class Email {
    
    private string subject;
    private String to;
    private string from;
    private string body;

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
