package dao;

import model.Event;
import model.EventCategory;

import java.util.List;

public interface EventDao {

    void add(Event event);
    Event find(int id);
    void remove(int id);

    List<Event> getAll();
    List<Event> getBy(EventCategory eventCategory);
}
