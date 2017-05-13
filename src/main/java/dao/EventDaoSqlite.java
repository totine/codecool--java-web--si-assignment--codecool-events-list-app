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
                String query = event.getId()==null ? "INSERT INTO events (name, event_date, event_time, description, url, category_id) VALUES (?, ?, ?, ?, ?, ?)" :
                                                     "UPDATE events SET name = ?, event_date = ?, event_time = ?, description = ?, url = ?, category_id = ? WHERE id = ?";
                statement = this.getConnection().prepareStatement(query);
                statement.setString(1, event.getName());
                statement.setString(2, String.valueOf(event.getDate()));
                statement.setString(3, String.valueOf(event.getTime()));
                statement.setString(4, event.getDescription());
                statement.setString(5, event.getUrl());
                statement.setInt(6, 1);
                if (event.getId()!=null)
                    statement.setInt(7, event.getId());
                statement.execute();

            } catch (SQLException e) {
                e.printStackTrace();
            }

    }

    @Override
    public Event find(int id) {
        Event event = null;
        PreparedStatement statement = null;
        try {
            statement = this.getConnection().prepareStatement("SELECT id, name, event_date, event_time, description, category_id, url FROM events WHERE id = ?");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            event = new Event();
            event.setId(rs.getInt("id"));
            event.setName(rs.getString("name"));
            event.setDate(rs.getString("event_date"));
            event.setTime(rs.getString("event_time"));
            event.setDescription(rs.getString("description"));
            event.setUrl(rs.getString("url"));
            EventCategory category = new EventCategoryDaoSqlite().find(rs.getInt("category_id"));
            event.setCategory(category);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return event;
    }

    @Override
    public void remove(int id) {
        PreparedStatement statement = null;
        try {
            statement = this.getConnection().prepareStatement("DELETE FROM events WHERE id = ?");
            statement.setInt(1, id);
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
            event.setUrl(rs.getString("url"));
            event.setCategory(category);
            events.add(event);

        }

        return events;
    }
}
