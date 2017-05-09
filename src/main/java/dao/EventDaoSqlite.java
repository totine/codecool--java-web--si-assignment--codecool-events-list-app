package dao;

import model.Event;
import model.EventCategory;

import java.sql.PreparedStatement;
import java.sql.SQLException;
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

        try {
            PreparedStatement statement = this.getConnection().prepareStatement("SELECT * FROM events");
            events = this.getEvents(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return events;
    }


    @Override
    public List<Event> getBy(EventCategory eventCategoryCategory) {
        return null;
    }
    private List<Event> getEvents(PreparedStatement statement){
        return null;
    }
}
