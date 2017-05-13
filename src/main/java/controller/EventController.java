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

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EventController {
    private static EventDao eventDao = new EventDaoSqlite();
    private static EventCategoryDao eventCategoryDao = new EventCategoryDaoSqlite();

    public List<Event> getAllEvents() {
        return eventDao.getAll();
    }

    public static ModelAndView renderEvents(Request req, Response res) {
        List<Event> events = req.params(":category").isEmpty() || req.params(":category").equals("all") ? eventDao.getAll() : eventDao.getBy(eventCategoryDao.find(req.params(":category")));
        List<EventCategory> categories = eventCategoryDao.getAll();
        Map params = new HashMap<>();
        params.put("eventContainer", events);
        params.put("categoryContainer", categories);
        params.put("userStatus", req.session().attribute("userStatus"));
        return new ModelAndView(params, "event/index");
    }

    public static ModelAndView renderEventDetails(Request req, Response res, Integer eventId) {

        Event event = eventDao.find(eventId);

        Map params = new HashMap<>();
        params.put("event", event);
        return new ModelAndView(params, "event/show");
    }

    public static ModelAndView renderEventAdd(Request req, Response res) {
        Map params = new HashMap<>();
        params.put("event", null);
        return new ModelAndView(params, "event/form");
    }

    public static String addNewEvent(Request req, Response res) {
        Event newEvent = new Event(req.queryMap("event_name").value());
        LocalDate eventDate = LocalDate.parse(req.queryMap("event_date").value());
        newEvent.setDate(eventDate);
        LocalTime eventTime = LocalTime.parse(req.queryMap("event_time").value());
        newEvent.setTime(eventTime);
        newEvent.setDescription(req.queryMap("description").value());
        newEvent.setUrl(req.queryMap("event_url").value());
        eventDao.add(newEvent);
        res.redirect("/");
        return null;
    }

    public static ModelAndView renderRemoveEvents(Request req, Response res) {
        List<Event> events = eventDao.getAll();

        Map params = new HashMap<>();
        params.put("eventContainer", events);
        return new ModelAndView(params, "event/remove");
    }

    public static String removeEvents(Request req, Response res) {
        for (int i=0; i<req.queryMap("events_to_remove").values().length; i++)
            eventDao.remove(Integer.parseInt(req.queryMap("events_to_remove").values()[i]));
        res.redirect("/");
        return null;
    }

    public static ModelAndView renderEventEdit(Request req, Response res, int eventId) {
        System.out.println(eventId);
        Event event = eventDao.find(eventId);
        System.out.println(event.getUrl());
        System.out.println(event.getName());
        Map params = new HashMap<>();
        params.put("event", event);
        return new ModelAndView(params, "event/form");
    }

    public static String editEvent(Request req, Response res, int eventId) {
        Event eventToEdit = eventDao.find(eventId);
        eventToEdit.setName(req.queryMap("event_name").value());
        eventToEdit.setDate(req.queryMap("event_date").value());
        eventToEdit.setTime(req.queryMap("event_time").value());
        eventToEdit.setDescription(req.queryMap("description").value());
        eventToEdit.setUrl(req.queryMap("url").value());
        eventDao.add(eventToEdit);
        res.redirect("/");
        return null;
    }
}
