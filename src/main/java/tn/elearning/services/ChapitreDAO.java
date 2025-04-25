package tn.elearning.services;

import tn.elearning.entities.Chapitre;
import tn.elearning.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChapitreDAO {
    private Connection cnx = DataSource.getInstance().getConnection();

    // Create
    public void createChapitre(Chapitre chapitre) throws SQLException {
        String query = "INSERT INTO chapitre (nom, module_id, description) VALUES (?, ?, ?)";
        try (
             PreparedStatement stmt = cnx.prepareStatement(query)) {
            stmt.setString(1, chapitre.getNom());
            stmt.setInt(2, chapitre.getModuleId());
            stmt.setString(3, chapitre.getDescription());
            stmt.executeUpdate();
        }
    }

    // Read All
    public List<Chapitre> getAllChapitres() throws SQLException {
        List<Chapitre> list = new ArrayList<>();
        String query = "SELECT * FROM chapitre";
        try (PreparedStatement stmt = cnx.prepareStatement(query);
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Chapitre chapitre = new Chapitre();
                chapitre.setId(rs.getInt("id"));
                chapitre.setNom(rs.getString("nom"));
                chapitre.setModuleId(rs.getInt("module_id"));
                chapitre.setDescription(rs.getString("description"));
                list.add(chapitre);
            }
        }
        return list;
    }

    // Update
    public void updateChapitre(Chapitre chapitre) throws SQLException {
        String query = "UPDATE chapitre SET nom = ?, module_id = ?, description = ? WHERE id = ?";
        try (PreparedStatement stmt = cnx.prepareStatement(query)) {
            stmt.setString(1, chapitre.getNom());
            stmt.setInt(2, chapitre.getModuleId());
            stmt.setString(3, chapitre.getDescription());
            stmt.setInt(4, chapitre.getId());
            stmt.executeUpdate();
        }
    }

    // Delete
    public void deleteChapitre(int id) throws SQLException {
        String query = "DELETE FROM chapitre WHERE id = ?";
        try (PreparedStatement stmt = cnx.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
    public Chapitre getChapitreById(int chapitreId) throws SQLException {
        String query = "SELECT * FROM chapitre WHERE id = ?";
        try (PreparedStatement stmt = cnx.prepareStatement(query)) {

            stmt.setInt(1, chapitreId);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                Chapitre chapitre = new Chapitre();
                chapitre.setId(resultSet.getInt("id"));
                chapitre.setNom(resultSet.getString("nom"));
                chapitre.setDescription(resultSet.getString("description"));
                return chapitre;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return null;
    }

}

