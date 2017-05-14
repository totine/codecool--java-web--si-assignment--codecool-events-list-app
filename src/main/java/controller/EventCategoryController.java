package controller;

import dao.EventCategoryDao;
import dao.EventCategoryDaoSqlite;
import model.EventCategory;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;


public class EventCategoryController {
    private static EventCategoryDao eventCategoryDao = new EventCategoryDaoSqlite();

    public static ModelAndView renderEventCategoryAdd(Request req, Response res) {
        Map params = new HashMap<>();
        if (req.session().attribute("category_to_flash")!=null)

            params.put("categoryToFlash", req.session().attribute("categoryToFlash"));

        return new ModelAndView(params, "category/form");
    }

    public static String addNewEventCategory(Request req, Response res) {
        if (eventCategoryDao.find(req.queryMap("category_name").value().toLowerCase().trim())!=null) {

            res.redirect("/category/add");
            req.session().attribute("categoryToFlash", req.queryMap("category_name").value());
            System.out.println(req.session().attributes());
            return null;
        }
        EventCategory newEventCategory = new EventCategory(req.queryMap("category_name").value());
        eventCategoryDao.add(newEventCategory);

        res.redirect("/event/add");
        return null;
    }
}
