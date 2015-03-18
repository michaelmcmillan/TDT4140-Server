package email;

import java.util.ArrayList;
import java.util.Stack;

public class EmailThread {

    public static EmailThread instance = null;
    public static Stack<SimpleMail> queue = new Stack<>();

    public static Thread emailRunner = new Thread(new Runnable() {
        public void run() {
            while (true) {
                if (EmailThread.getInstance().queue.size() > 0) {
                    SimpleMail current = queue.pop();
                    //new Email(current.to, current.subject, current.body).send();
                    System.out.println(current.subject + " ble sendt til " + current.to);
                    EmailThread.getInstance().queue.remove(current);
                }

                try {
                    Thread.sleep(1000);
                } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    });

    public static EmailThread getInstance () {
        if (instance == null)
            instance = new EmailThread();
        return instance;
    }
}