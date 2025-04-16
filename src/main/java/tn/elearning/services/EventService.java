package services;
import entities.event;
import utils.MyConnection;
import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EventService implements Iservice<event> {
    private Connection cnx;

    public EventService() {
        cnx = MyConnection.getInstance().getConnection();
    }
    @Override
    public void create(event entity) throws SQLException {
        String query = "INSERT INTO event (titre, description, type, prix, date_debut, date_fin, image) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = cnx.prepareStatement(query);
            preparedStatement.setString(1, entity.getTitre());
            preparedStatement.setString(2, entity.getDescription());
            preparedStatement.setString(3, entity.getType());
            preparedStatement.setInt(4, entity.getPrix());
            preparedStatement.setDate(5, new java.sql.Date(entity.getDate_debut1().getTime()));
            preparedStatement.setDate(6, new java.sql.Date(entity.getDate_fin1().getTime()));
            preparedStatement.setString(7, entity.getImage());
            preparedStatement.executeUpdate();
            System.out.println("Event added!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean update(event entity) throws SQLException {
        String query = "UPDATE event SET titre = ?, description = ?, type = ?, prix = ?, date_debut = ?, date_fin = ?, image = ? WHERE id = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(query);
            ps.setString(1, entity.getTitre());
            ps.setString(2, entity.getDescription());
            ps.setString(3, entity.getType());
            ps.setInt(4, entity.getPrix());
            ps.setDate(5, new java.sql.Date(entity.getDate_debut1().getTime()));
            ps.setDate(6, new java.sql.Date(entity.getDate_fin1().getTime()));
            ps.setString(7, entity.getImage());
            ps.setInt(8, entity.getId());

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0; // ✅ retourne vrai si la mise à jour a été faite
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // ou relancer l’exception selon ton usage
        }
    }


    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM event WHERE id = ?";
        try {
            PreparedStatement preparedStatement = cnx.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Event with the id = " + id + " is deleted!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<event> getAll() throws SQLException {
        List<event> events = new ArrayList<>();
        String query = "SELECT * FROM event";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            int id = rs.getInt("id");
            int prix = rs.getInt("prix");
            String titre = rs.getString("titre");
            String description = rs.getString("description");
            String type = rs.getString("type");
            Date date_debut = rs.getDate("date_debut");
            Date date_fin = rs.getDate("date_fin");
            String image = rs.getString("image");
            event e = new event(id,prix, titre, description, type, date_debut, date_fin, image);
            events.add(e);
        }
        return events;
    }
    public event getEventById(int id) throws SQLException {
        String query = "SELECT * FROM event WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new event(
                    rs.getInt("id"),
                    rs.getInt("prix"),
                    rs.getString("titre"),
                    rs.getString("description"),
                    rs.getString("type"),
                    rs.getDate("date_debut"),
                    rs.getDate("date_fin"),
                    rs.getString("image")
            );
        }
        return null;
    }


}
