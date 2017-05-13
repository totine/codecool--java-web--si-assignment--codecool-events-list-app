import controller.EventController;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import  static spark.Spark.*;


public class Main {
    private static EventController eventController = new EventController();

    public static void main(String[] args) {

        // Configure Spark
        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        staticFileLocation("/public");
        port(8888);



        // Always start with more specific routes
        get("/hello", (req, res) -> "Hello World");

        // Always add generic routes to the end
         get("/", EventController::renderEvents, new ThymeleafTemplateEngine());

        get("/event/:id/", (req, res) -> EventController.renderEventDetails(req, res, Integer.parseInt(req.params(":id"))), new ThymeleafTemplateEngine());
        get("/event/add", EventController::renderEventAdd, new ThymeleafTemplateEngine());
        post("/event/add", EventController::addNewEvent);
        get("/event/remove", EventController::renderRemoveEvents, new ThymeleafTemplateEngine());


        // Equivalent with above
        // get("/index", (Request req, Response res) -> {
         //   return new ThymeleafTemplateEngine().render( EventController.renderEvents(req, res) );
       // });
    }


}