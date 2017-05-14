import controller.EventCategoryController;
import controller.EventController;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import static spark.Spark.*;


public class Main {
    private static EventController eventController = new EventController();

    public static void main(String[] args) {

        // Configure Spark
        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        staticFileLocation("/public");
        port(8888);


        before((request, response) -> {
            if (args.length > 0) {
                request.session().attribute("userStatus", args[0]);
            }
        });


        get("/", EventController::renderEvents, new ThymeleafTemplateEngine());
        get("/event", EventController::renderEvents, new ThymeleafTemplateEngine());
        get("/event/:id/show", (req, res) -> EventController.renderEventDetails(req, res, Integer.parseInt(req.params(":id"))), new ThymeleafTemplateEngine());
        get("/event/:id/edit", (req, res) -> EventController.renderEventEdit(req, res, Integer.parseInt(req.params(":id"))), new ThymeleafTemplateEngine());
        post("/event/:id/edit", (req, res) -> EventController.editEvent(req, res, Integer.parseInt(req.params(":id"))));
        get("/event/add", EventController::renderEventAdd, new ThymeleafTemplateEngine());
        post("/event/add", EventController::addNewEvent);
        get("/event/panel", EventController::renderRemoveEvents, new ThymeleafTemplateEngine());
        post("/event/panel", EventController::removeEvents);
        get("/event/category/:id", EventController::renderEvents, new ThymeleafTemplateEngine());
        get("/category/add", EventCategoryController::renderEventCategoryAdd, new ThymeleafTemplateEngine());
        post("/category/add", EventCategoryController::addNewEventCategory);

    }
}