import controller.EventController;
import spark.Request;
import spark.Response;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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
         get("/", EventController::renderProducts, new ThymeleafTemplateEngine());

        get("/event/:id/", (req, res) -> EventController.renderEventDetails(req, res, Integer.parseInt(req.params(":id"))), new ThymeleafTemplateEngine());

//        get("/", (req, res) -> eventController.getAllEvents().toString());

        // Equivalent with above
        // get("/index", (Request req, Response res) -> {
         //   return new ThymeleafTemplateEngine().render( EventController.renderProducts(req, res) );
       // });
    }


}