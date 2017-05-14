package dao;

import model.Event;
import model.EventCategory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventCategoryDaoSqlite extends BaseDao implements EventCategoryDao {
    @Override
    public void add(EventCategory category) {
        PreparedStatement statement = null;
        try {
            String query = category.getId()==null ? "INSERT INTO event_categories (name) VALUES (?)" :
                    "UPDATE event_categories SET name = ? WHERE id = ?";
            statement = this.getConnection().prepareStatement(query);
            statement.setString(1, category.getName());
            if (category.getId()!=null)
                statement.setInt(2, category.getId());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



    @Override
    public EventCategory find(int id) {
        EventCategory category = null;
        PreparedStatement statement = null;
        try {
            statement = this.getConnection().prepareStatement("SELECT id, name FROM event_categories WHERE id = ?");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            category = new EventCategory(rs.getString("name"));
            category.setId(rs.getInt("id"));
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }

    @Override
    public EventCategory find(String categoryName){
        categoryName = categoryName.replace("-", " ");
        EventCategory category = null;
        PreparedStatement statement = null;
        try {
            statement = this.getConnection().prepareStatement("SELECT id, name FROM event_categories WHERE lower(name) = ?");
            statement.setString(1, categoryName);
            ResultSet rs = statement.executeQuery();
            category = new EventCategory(rs.getString("name"));
            category.setId(rs.getInt("id"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }


    @Override
    public void remove(int id) {
        PreparedStatement statement = null;
        try {
            statement = this.getConnection().prepareStatement("DELETE FROM event_categories WHERE id = ?");
            statement.setInt(1, id);
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<EventCategory> getAll() {
        List<EventCategory> categories = new ArrayList<>();

        try {
            PreparedStatement statement = this.getConnection().prepareStatement("SELECT * FROM event_categories");
            categories = this.getCategories(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }
    private List<EventCategory> getCategories(PreparedStatement statement) throws SQLException {
        List<EventCategory> categories = new ArrayList<>();
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            EventCategory category = new EventCategory(rs.getString("name"));
            category.setId(rs.getInt("id"));
            categories.add(category);

        }

        return categories;
    }
}
