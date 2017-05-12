package controller;

import dao.EventDao;
import dao.EventDaoSqlite;
import model.Event;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EventController {
    private EventDao eventDao = new EventDaoSqlite();

    public List<Event> getAllEvents() {
        return eventDao.getAll();
    }

    public static ModelAndView renderProducts(Request req, Response res) {
        EventDao eventDao = new EventDaoSqlite();
        List<Event> events = eventDao.getAll();

        Map params = new HashMap<>();
        params.put("eventContainer", events);
        return new ModelAndView(params, "event/index");
    }

    public static ModelAndView renderEventDetails(Request req, Response res, Integer eventId) {
        EventDao eventDao = new EventDaoSqlite();
        Event event = eventDao.find(eventId);

        Map params = new HashMap<>();
        params.put("event", event);
        return new ModelAndView(params, "event/show");
    }
}
