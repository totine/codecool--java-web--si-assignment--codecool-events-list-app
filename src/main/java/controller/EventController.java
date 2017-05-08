package controller;

import dao.EventDao;
import model.Event;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rafalstepien on 28/04/2017.
 */
public class EventController {

    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        events.add(new Event("event1"));
        events.add(new Event("event2"));
        events.add(new Event("event3"));
        events.add(new Event("event4"));
        return events;


    }
    public static ModelAndView renderProducts(Request req, Response res) {
        //Get events from database by Dao

        Map params = new HashMap<>();
        params.put("eventContainer", "Codecool cinema");
        return new ModelAndView(params, "product/index");
    }
}
