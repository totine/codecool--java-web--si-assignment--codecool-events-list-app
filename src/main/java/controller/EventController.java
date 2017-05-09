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
        //Get events from database by Dao

        Map params = new HashMap<>();
        params.put("eventContainer", "Codecool cinema");
        return new ModelAndView(params, "product/index");
    }
}
