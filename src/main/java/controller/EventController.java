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
import java.util.*;
import java.util.stream.Collectors;


public class EventController {
    private static EventDao eventDao = new EventDaoSqlite();
    private static EventCategoryDao eventCategoryDao = new EventCategoryDaoSqlite();

    public List<Event> getAllEvents() {
        return eventDao.getAll();
    }

    public static ModelAndView renderEvents(Request req, Response res) {
        List<Event> events;
        if (!req.queryMap("search").hasValue()) {
            events = req.params(":id") == null ? eventDao.getAll() : eventDao.getBy(eventCategoryDao.find(Integer.parseInt(req.params(":id"))));
        }else {
            String searchPhrase = req.queryMap("search").value().toLowerCase().trim();
            events = eventDao.getAll().stream().filter(x -> x.getName().toLowerCase().contains(searchPhrase) || x.getDescription().toLowerCase().contains(searchPhrase) || x.getCategory().getName().contains(searchPhrase)).collect(Collectors.toList());
        }

        events.sort(Comparator.comparing(Event::getDate));
        List<Event> eventsActive = events.stream().filter(x -> x.getDate().isAfter(LocalDate.now())).collect(Collectors.toList());
        List<Event> eventsArchived = events.stream().filter(x -> x.getDate().isBefore(LocalDate.now())).collect(Collectors.toList());

        List<EventCategory> categories = eventCategoryDao.getAll();
        Map params = new HashMap<>();
        params.put("eventContainer", eventsActive);
        params.put("eventContainerArchived", eventsArchived);
        params.put("categoryContainer", categories);
        params.put("userStatus", req.session().attribute("userStatus"));
        return new ModelAndView(params, "event/index");
    }

    public static ModelAndView renderEventDetails(Request req, Response res) {
        int eventId = Integer.parseInt(req.params(":id"));
        Event event = eventDao.find(eventId);
        Map params = new HashMap<>();
        params.put("event", event);
        params.put("userStatus", req.session().attribute("userStatus"));
        return new ModelAndView(params, "event/show");
    }

    public static ModelAndView renderEventAdd(Request req, Response res) {
        Map params = new HashMap<>();
        List<EventCategory> categories = eventCategoryDao.getAll();
        params.put("event", null);
        if (req.attribute("new_event")!=null)
            params.put("event", req.attribute("new_event"));
        params.put("categoryContainer", categories);
        params.put("userStatus", req.session().attribute("userStatus"));
        return new ModelAndView(params, "event/form");
    }

    public static ModelAndView addNewEvent(Request req, Response res) {
        Event newEvent = new Event(req.queryMap("event_name").value());
        System.out.println(req.queryMap("event_date").value());
        LocalDate eventDate = !req.queryMap("event_date").value().isEmpty() ? LocalDate.parse(req.queryMap("event_date").value()) : null;
        newEvent.setDate(eventDate);
        LocalTime eventTime = !req.queryMap("event_time").value().isEmpty() ? LocalTime.parse(req.queryMap("event_time").value()) : null;
        newEvent.setTime(eventTime);
        newEvent.setDescription(req.queryMap("description").value());
        newEvent.setUrl(req.queryMap("event_url").value());
        EventCategory category = eventCategoryDao.find(Integer.parseInt(req.queryMap("category").value()));
        newEvent.setCategory(category);
        if (newEvent.getDate() == null || eventDate.isBefore(LocalDate.now()) || newEvent.getTime()==null ) {
            req.attribute("new_event", newEvent);
            return renderEventAdd(req, res);
        }
        eventDao.add(newEvent);
        res.redirect("/");
        return null;
    }

    public static ModelAndView renderAdminPanel(Request req, Response res) {
        List<Event> events = eventDao.getAll();
        List<EventCategory> categories = eventCategoryDao.getAll();
        Map params = new HashMap<>();

        params.put("eventContainer", events);
        params.put("categoryContainer", categories);
        params.put("userStatus", req.session().attribute("userStatus"));
        return new ModelAndView(params, "panel/panel");
    }

    public static String removeEvents(Request req, Response res) {
        for (int i=0; i<req.queryMap("events_to_remove").values().length; i++)
            eventDao.remove(Integer.parseInt(req.queryMap("events_to_remove").values()[i]));
        res.redirect("/");
        return null;
    }

    public static ModelAndView renderEventRemove(Request req, Response res) {
        int eventId = Integer.parseInt(req.params(":id"));
        Event event = eventDao.find(eventId);
        Map params = new HashMap<>();
        params.put("event", event);
        params.put("userStatus", req.session().attribute("userStatus"));
        return new ModelAndView(params, "event/remove");
    }

    public static String removeEvent(Request req, Response res) {
        int eventId = Integer.parseInt(req.params(":id"));
        eventDao.remove(eventId);
        res.redirect("/");
        return null;
    }

    public static ModelAndView renderEventEdit(Request req, Response res) {
        int eventId = Integer.parseInt(req.params(":id"));
        Event event = eventDao.find(eventId);
        Map params = new HashMap<>();
        List<EventCategory> categories = eventCategoryDao.getAll();
        params.put("event", event);
        params.put("categoryContainer", categories);
        params.put("userStatus", req.session().attribute("userStatus"));
        return new ModelAndView(params, "event/form");
    }

    public static String editEvent(Request req, Response res) {
        int eventId = Integer.parseInt(req.params(":id"));
        Event eventToEdit = eventDao.find(eventId);
        eventToEdit.setName(req.queryMap("event_name").value());
        eventToEdit.setDate(req.queryMap("event_date").value());
        eventToEdit.setTime(req.queryMap("event_time").value());
        eventToEdit.setDescription(req.queryMap("description").value());
        eventToEdit.setUrl(req.queryMap("url").value());
        EventCategory category = eventCategoryDao.find(Integer.parseInt(req.queryMap("category").value()));
        eventToEdit.setCategory(category);
        eventDao.add(eventToEdit);
        res.redirect("/");
        return null;
    }

}
