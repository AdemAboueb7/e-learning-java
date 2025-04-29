
package tn.elearning.services;

import tn.elearning.entities.Chapitre;
import tn.elearning.entities.Cours;
import tn.elearning.utils.DataSource;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoursDAO {
    private Connection cnx = DataSource.getInstance().getConnection();
    // Create
    public int createCours(Cours cours) throws SQLException {
        String query = "INSERT INTO cours (chapitre_id, titre, updated_at, description) VALUES (?, ?, ?, ?)";
        try (
             PreparedStatement stmt = cnx.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            stmt.setInt(1, cours.getChapitreId());
            stmt.setString(2, cours.getTitre());
            stmt.setString(3, cours.getUpdatedAt());
            stmt.setString(4, cours.getDescription());
            stmt.executeUpdate();

            // Get the generated ID
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
            return -1;
        }
    }

    // Read All
    public List<Cours> getAllCours() throws SQLException {
        List<Cours> list = new ArrayList<>();
        String query = "SELECT * FROM cours";
        try (
             Statement stmt = cnx.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Cours cours = new Cours();
                cours.setId(rs.getInt("id"));
                cours.setChapitreId(rs.getInt("chapitre_id"));
                cours.setTitre(rs.getString("titre"));
                cours.setUpdatedAt(rs.getString("updated_at"));
                cours.setDescription(rs.getString("description"));

                // Get binary data from BLOB column
                Blob blob = rs.getBlob("contenu_fichier");
                if (blob != null) {
                    cours.setContenuFichier(blob.getBytes(1, (int)blob.length()));
                } else {
                    cours.setContenuFichier(null);
                }

                list.add(cours);
            }
        }
        return list;
    }

    // Update
    public void updateCours(Cours cours) throws SQLException {
        String query = "UPDATE cours SET titre = ?, contenu_fichier = ?, updated_at = ?, description = ? WHERE id = ?";
        try (
             PreparedStatement stmt = cnx.prepareStatement(query)) {
            stmt.setString(1, cours.getTitre());
            stmt.setBytes(2, cours.getContenuFichier());
            stmt.setString(3, cours.getUpdatedAt());
            stmt.setString(4, cours.getDescription());
            stmt.setInt(5, cours.getId());
            stmt.executeUpdate();
        }
    }

    // Delete
    public void deleteCours(int id) throws SQLException {
        String query = "DELETE FROM cours WHERE id = ?";
        try (
             PreparedStatement stmt = cnx.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // Upload File
    public void uploadFile(int coursId, File file) throws SQLException, IOException {
        String query = "UPDATE cours SET contenu_fichier = ? WHERE id = ?";
        try (
             PreparedStatement stmt = cnx.prepareStatement(query);
             FileInputStream fis = new FileInputStream(file)) {
            stmt.setBinaryStream(1, fis, (int) file.length());
            stmt.setInt(2, coursId);
            stmt.executeUpdate();
        }
    }

    public List<Cours> getCoursByChapitre(int chapitreId) throws SQLException {
        String query = "SELECT id, chapitre_id, titre, contenu_fichier, updated_at, description, " +
                "COALESCE(rating_sum, 0) as rating_sum, COALESCE(rating_count, 0) as rating_count " +
                "FROM cours WHERE chapitre_id = ?";
        List<Cours> courses = new ArrayList<>();

        try (
                PreparedStatement stmt = cnx.prepareStatement(query)) {
            stmt.setInt(1, chapitreId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Cours course = new Cours();
                course.setId(rs.getInt("id"));
                course.setChapitreId(rs.getInt("chapitre_id"));
                course.setTitre(rs.getString("titre"));
                course.setContenuFichier(rs.getBytes("contenu_fichier"));
                course.setUpdatedAt(rs.getString("updated_at"));
                course.setDescription(rs.getString("description"));
                course.setRatingSum(rs.getInt("rating_sum"));
                course.setRatingCount(rs.getInt("rating_count"));
                courses.add(course);
            }
        }
        return courses;
    }

    public int getTotalCourses() throws SQLException {
        String query = "SELECT COUNT(*) FROM cours";
        try (PreparedStatement stmt = cnx.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    public Map<String, Long> getCoursesPerModule() throws SQLException {
        String query = "SELECT m.nom, COUNT(c.id) FROM module m " +
                "LEFT JOIN chapitre ch ON m.id = ch.module_id " +
                "LEFT JOIN cours c ON ch.id = c.chapitre_id " +
                "GROUP BY m.nom";

        Map<String, Long> result = new HashMap<>();
        try (PreparedStatement stmt = cnx.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                result.put(rs.getString(1), rs.getLong(2));
            }
        }
        return result;
    }

    public List<Cours> getRecentCourses(int limit) throws SQLException {
        String query = "SELECT c.* FROM cours c " +
                "ORDER BY c.updated_at DESC LIMIT ?";

        List<Cours> courses = new ArrayList<>();
        try (PreparedStatement stmt = cnx.prepareStatement(query)) {
            stmt.setInt(1, limit);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Cours cours = new Cours();
                    cours.setId(rs.getInt("id"));
                    cours.setTitre(rs.getString("titre"));
                    cours.setUpdatedAt(rs.getString("updated_at"));
                    cours.setDescription(rs.getString("description"));
                    cours.setChapitreId(rs.getInt("chapitre_id"));
                    courses.add(cours);
                }
            }
        }
        return courses;
    }
    public void updateCourseRating(Cours course) throws SQLException {
        String query = "UPDATE cours SET rating_sum = ?, rating_count = ? WHERE id = ?";
        try( PreparedStatement stmt = cnx.prepareStatement(query)) {
            stmt.setInt(1, course.getRatingSum());
            stmt.setInt(2, course.getRatingCount());
            stmt.setInt(3, course.getId());
            stmt.executeUpdate();
        }
    }

    // Download File
    public void downloadFile(int coursId, String outputPath) throws SQLException, IOException {
        String query = "SELECT contenu_fichier FROM cours WHERE id = ?";
        try (
             PreparedStatement stmt = cnx.prepareStatement(query)) {
            stmt.setInt(1, coursId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    try (InputStream is = rs.getBinaryStream("contenu_fichier");
                         FileOutputStream fos = new FileOutputStream(outputPath)) {
                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = is.read(buffer)) != -1) {
                            fos.write(buffer, 0, bytesRead);
                        }
                    }
                }
            }
        }
    }
}
