package dao;

import model.Event;
import model.EventCategory;

import java.util.ArrayList;
import java.util.List;


public class EventDaoSqlite extends BaseDao implements EventDao {
    @Override
    public void add(Event event) {

    }

    @Override
    public Event find(int id) {
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Event> getAll() {
        List<Event> events = new ArrayList<>();
        events.add(new Event("event1"));
        events.add(new Event("event2"));
        events.add(new Event("event3"));
        events.add(new Event("event4"));
        return events;
    }

    @Override
    public List<Event> getBy(EventCategory eventCategoryCategory) {
        return null;
    }
}
