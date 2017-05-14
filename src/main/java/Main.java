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
        get("/event/:id/show", EventController::renderEventDetails, new ThymeleafTemplateEngine());
        get("/event/:id/edit", EventController::renderEventEdit, new ThymeleafTemplateEngine());
        post("/event/:id/edit", EventController::editEvent);
        get("/event/:id/remove", EventController::renderEventRemove, new ThymeleafTemplateEngine());
        post("/event/:id/remove", EventController::removeEvent);
        get("/event/add", EventController::renderEventAdd, new ThymeleafTemplateEngine());
        post("/event/add", EventController::addNewEvent);
        get("/event/panel", EventController::renderAdminPanel, new ThymeleafTemplateEngine());
        post("/event/panel", EventController::removeEvents);
        get("/event/category/:id", EventController::renderEvents, new ThymeleafTemplateEngine());
        get("/category/add", EventCategoryController::renderEventCategoryAdd, new ThymeleafTemplateEngine());
        post("/category/add", EventCategoryController::addNewEventCategory);
        get("/category/:id/show", EventCategoryController::renderEventCategoryDetails, new ThymeleafTemplateEngine());
        get("/category/:id/edit", EventCategoryController::renderEventCategoryEdit, new ThymeleafTemplateEngine());
        post("/category/:id/edit", EventCategoryController::editEventCategory);
        get("/category/:id/remove", EventCategoryController::renderEventCategoryRemove, new ThymeleafTemplateEngine());
        post("/category/:id/remove", EventCategoryController::removeEventCategory);
    }
}