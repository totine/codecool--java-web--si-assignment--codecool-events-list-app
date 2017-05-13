package dao;

import model.EventCategory;

import java.util.ArrayList;
import java.util.List;

public class EventCategoryDaoSqlite extends BaseDao implements EventCategoryDao {
    @Override
    public void add(EventCategory category) {

    }

    @Override
    public EventCategory find(int id) {
        return new EventCategory("taka sobie kategoria");
    }

    @Override
    public EventCategory find(String categoryName){
        return new EventCategory("taka sobie kategoria");
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<EventCategory> getAll() {
        List<EventCategory> categories = new ArrayList<>();
        categories.add(new EventCategory("Python"));
        categories.add(new EventCategory("Java"));
        categories.add(new EventCategory("Codecool Events"));
        return categories;
    }
}
