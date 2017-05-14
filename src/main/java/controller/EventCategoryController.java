package controller;

import dao.EventCategoryDao;
import dao.EventCategoryDaoSqlite;
import dao.EventDao;
import dao.EventDaoSqlite;
import model.Event;
import model.EventCategory;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EventCategoryController {
    private static EventCategoryDao eventCategoryDao = new EventCategoryDaoSqlite();
    private static EventDao eventDao = new EventDaoSqlite();

    public static ModelAndView renderEventCategoryAdd(Request req, Response res) {
        Map params = new HashMap<>();
        if (req.session().attribute("category_to_flash")!=null)
            params.put("categoryToFlash", req.session().attribute("categoryToFlash"));
        params.put("userStatus", req.session().attribute("userStatus"));
        return new ModelAndView(params, "category/form");
    }

    public static String addNewEventCategory(Request req, Response res) {
        if (eventCategoryDao.find(req.queryMap("category_name").value().toLowerCase().trim())!=null) {

            res.redirect("/category/add");
            req.session().attribute("categoryToFlash", req.queryMap("category_name").value());
            return null;
        }
        EventCategory newEventCategory = new EventCategory(req.queryMap("category_name").value());
        eventCategoryDao.add(newEventCategory);

        res.redirect("/event/add");
        return null;
    }

    public static ModelAndView renderEventCategoryDetails(Request req, Response res) {
        int categoryId = Integer.parseInt(req.params(":id"));
        EventCategory category = eventCategoryDao.find(categoryId);
        List<Event> eventsByCategory = eventDao.getBy(category);
        Map params = new HashMap<>();
        params.put("category", category);
        params.put("eventContainer", eventsByCategory);
        params.put("userStatus", req.session().attribute("userStatus"));
        return new ModelAndView(params, "category/show");
    }

    public static ModelAndView renderEventCategoryRemove(Request req, Response res) {
        int categoryId = Integer.parseInt(req.params(":id"));
        EventCategory category = eventCategoryDao.find(categoryId);
        Map params = new HashMap<>();
        params.put("category", category);
        params.put("userStatus", req.session().attribute("userStatus"));
        return new ModelAndView(params, "category/remove");
    }

    public static String removeEventCategory(Request req, Response res) {
        int categoryId = Integer.parseInt(req.params(":id"));
        eventCategoryDao.remove(categoryId);
        res.redirect("/");
        return null;
    }

    public static ModelAndView renderEventCategoryEdit(Request req, Response res) {
        int categoryId = Integer.parseInt(req.params(":id"));
        EventCategory category = eventCategoryDao.find(categoryId);
        Map params = new HashMap<>();
        params.put("category", category);
        return new ModelAndView(params, "category/form");
    }

    public static String editEventCategory(Request req, Response res) {
        int categoryId = Integer.parseInt(req.params(":id"));
        EventCategory categoryToEdit = eventCategoryDao.find(categoryId);
        categoryToEdit.setName(req.queryMap("category_name").value());
        eventCategoryDao.add(categoryToEdit);
        res.redirect("/");
        return null;
    }
}
