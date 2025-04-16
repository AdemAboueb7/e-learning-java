package services;

import entities.participation;
import utils.MyConnection; // adjust if needed

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParticipationService {

    private Connection cnx;

    public ParticipationService() {
        cnx = MyConnection.getInstance().getConnection();
    }

    public void addParticipation(participation p) throws SQLException {
        String query = "INSERT INTO participation (event_id, user_id, created_at) VALUES (?, ?, ?)";
        PreparedStatement ps = cnx.prepareStatement(query);
        ps.setInt(1, p.getEvent_id());
        ps.setInt(2, p.getUser_id());
        ps.setDate(3, new java.sql.Date(p.getCreated_at().getTime()));
        ps.executeUpdate();
    }

    public boolean hasParticipated(int eventId, int userId) throws SQLException {
        String query = "SELECT COUNT(*) FROM participation WHERE event_id = ? AND user_id = ?";
        PreparedStatement ps = cnx.prepareStatement(query);
        ps.setInt(1, eventId);
        ps.setInt(2, userId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt(1) > 0;
    }

    public List<Integer> getParticipatedEventIdsByUser(int userId) throws SQLException {
        List<Integer> ids = new ArrayList<>();
        String query = "SELECT event_id FROM participation WHERE user_id = ?";
        PreparedStatement ps = cnx.prepareStatement(query);
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            ids.add(rs.getInt("event_id"));
        }
        return ids;
    }

    public void cancelParticipation(int eventId, int userId) throws SQLException {
        String query = "DELETE FROM participation WHERE event_id = ? AND user_id = ?";
        PreparedStatement ps = cnx.prepareStatement(query);
        ps.setInt(1, eventId);
        ps.setInt(2, userId);
        ps.executeUpdate();
    }
}
