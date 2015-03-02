import static spark.Spark.*;


public class HelloWorld {
    public static void main(String[] args) {
        setPort(1337);

        get("/hello", (req, res) -> {

            // Hente modeller

            // Hente views

            return "what is going on";
        });
    }
}