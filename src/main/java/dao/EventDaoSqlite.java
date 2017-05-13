package dao;

import model.Event;
import model.EventCategory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class EventDaoSqlite extends BaseDao implements EventDao {
    @Override
    public void add(Event event) {

        PreparedStatement statement = null;
        try {
            statement = this.getConnection().prepareStatement("INSERT INTO events (name, event_date, event_time, description, url, category_id) VALUES (?, ?, ?, ?, ?, 1)");
            statement.setString(1, event.getName());
            statement.setString(2, String.valueOf(event.getDate()));
            statement.setString(3, String.valueOf(event.getTime()));
            statement.setString(4, event.getDescription());
            statement.setString(5, event.getUrl());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Event find(int id) {
        Event event = new Event("new event");
        return event;
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


    private List<Event> getEvents(PreparedStatement statement) throws SQLException {
        List<Event> events = new ArrayList<Event>();
        EventCategory category = new EventCategory("Category-name");

        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            Event event = new Event(rs.getString("name"));
            event.setId(rs.getInt("id"));
            event.setDate(rs.getString("event_date"));
            event.setTime(rs.getString("event_time"));
            event.setDescription(rs.getString("description"));
            event.setCategory(category);
            events.add(event);

        }

        return events;
    }
}
