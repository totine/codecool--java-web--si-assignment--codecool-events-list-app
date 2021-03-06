package dao;


import model.EventCategory;

import java.util.List;

public interface EventCategoryDao {
    void add(EventCategory category);

    EventCategory find(int id);

    EventCategory find(String categoryName);

    void remove(int id);

    List<EventCategory> getAll();
}
