package tn.elearning.services;
import tn.elearning.entities.Module;
import tn.elearning.utils.DataSource;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ModuleDAO {
   private Connection cnx = DataSource.getInstance().getConnection();

    // Create
    public void createModule(Module module) throws SQLException {
        String query = "INSERT INTO module (nom, description) VALUES (?, ?)";
        try (PreparedStatement stmt = cnx.prepareStatement(query)){
            stmt.setString(1, module.getNom());
            stmt.setString(2, module.getDescription());
            stmt.executeUpdate();
        }
    }

    // Read All
    public List<Module> getAllModules() throws SQLException {
        List<Module> list = new ArrayList<>();
        String query = "SELECT * FROM module";
        try (
             Statement stmt = cnx.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Module module = new Module();
                module.setId(rs.getInt("id"));
                module.setNom(rs.getString("nom"));
                module.setDescription(rs.getString("description"));
                list.add(module);
            }
        }
        return list;
    }

    // Update
    public void updateModule(Module module) throws SQLException {
        String query = "UPDATE module SET nom = ?, description = ? WHERE id = ?";
        try (
             PreparedStatement stmt = cnx.prepareStatement(query)) {
            stmt.setString(1, module.getNom());
            stmt.setString(2, module.getDescription());
            stmt.setInt(3, module.getId());
            stmt.executeUpdate();
        }
    }

    // Delete
    public void deleteModule(int id) throws SQLException {
        String query = "DELETE FROM module WHERE id = ?";
        try (
             PreparedStatement stmt = cnx.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}

