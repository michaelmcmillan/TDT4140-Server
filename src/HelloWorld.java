import static spark.Spark.*;

public class HelloWorld {
    public static void main(String[] args) {

        setPort(1339);

        before((request, response) -> {

            System.out.println(request.headers());

            if (true) {
                halt(401, "Du er ikke autentisert.");
            }
        });

        get("/hello", (req, res) -> {
            return "what is going on";
        });
    }
}