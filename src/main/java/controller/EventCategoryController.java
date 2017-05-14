package controller;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;


public class EventCategoryController {

    public static ModelAndView renderEventCategoryAdd(Request req, Response res) {
        Map params = new HashMap<>();
        return new ModelAndView(params, "category/form");
    }
}
