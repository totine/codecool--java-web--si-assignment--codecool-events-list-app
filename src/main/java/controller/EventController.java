package controller;

import dao.EventDao;
import dao.EventDaoSqlite;
import model.Event;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EventController {
    private static EventDao eventDao = new EventDaoSqlite();

    public List<Event> getAllEvents() {
        return eventDao.getAll();
    }

    public static ModelAndView renderEvents(Request req, Response res) {
        List<Event> events = eventDao.getAll();

        Map params = new HashMap<>();
        params.put("eventContainer", events);
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
        return new ModelAndView(params, "event/add");
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
        System.out.println(req.queryMap());
        res.redirect("/");
        return null;
    }
}
