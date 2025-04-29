package tn.elearning.services;

import tn.elearning.entities.Cours;
import tn.elearning.utils.DataSource;
import tn.elearning.utils.UserSession;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoursDAO {
    private Connection cnx = DataSource.getInstance().getConnection();

    private boolean isTeacher() {
        return UserSession.getInstance().getUser().getRoles().contains("ROLE_TEACHER");
    }

    private boolean isParent() {
        return UserSession.getInstance().getUser().getRoles().contains("ROLE_PARENT");
    }

    // Create
    public int createCours(Cours cours) throws SQLException {
        if (!isTeacher()) {
            throw new SecurityException("Accès refusé : seuls les enseignants peuvent créer des cours.");
        }

        String query = "INSERT INTO cours (chapitre_id, titre, updated_at, description) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = cnx.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, cours.getChapitreId());
            stmt.setString(2, cours.getTitre());
            stmt.setString(3, cours.getUpdatedAt());
            stmt.setString(4, cours.getDescription());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
            return -1;
        }
    }

    // Read All (accessible à Teacher ET Parent)
    public List<Cours> getAllCours() throws SQLException {
        List<Cours> list = new ArrayList<>();
        String query = "SELECT * FROM cours";
        try (Statement stmt = cnx.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Cours cours = new Cours();
                cours.setId(rs.getInt("id"));
                cours.setChapitreId(rs.getInt("chapitre_id"));
                cours.setTitre(rs.getString("titre"));
                cours.setUpdatedAt(rs.getString("updated_at"));
                cours.setDescription(rs.getString("description"));

                Blob blob = rs.getBlob("contenu_fichier");
                if (blob != null) {
                    cours.setContenuFichier(blob.getBytes(1, (int) blob.length()));
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
        if (!isTeacher()) {
            throw new SecurityException("Accès refusé : seuls les enseignants peuvent modifier des cours.");
        }

        String query = "UPDATE cours SET titre = ?, contenu_fichier = ?, updated_at = ?, description = ? WHERE id = ?";
        try (PreparedStatement stmt = cnx.prepareStatement(query)) {
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
        if (!isTeacher()) {
            throw new SecurityException("Accès refusé : seuls les enseignants peuvent supprimer des cours.");
        }

        String query = "DELETE FROM cours WHERE id = ?";
        try (PreparedStatement stmt = cnx.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // Upload File
    public void uploadFile(int coursId, File file) throws SQLException, IOException {
        if (!isTeacher()) {
            throw new SecurityException("Accès refusé : seuls les enseignants peuvent uploader des fichiers.");
        }

        String query = "UPDATE cours SET contenu_fichier = ? WHERE id = ?";
        try (PreparedStatement stmt = cnx.prepareStatement(query);
             FileInputStream fis = new FileInputStream(file)) {
            stmt.setBinaryStream(1, fis, (int) file.length());
            stmt.setInt(2, coursId);
            stmt.executeUpdate();
        }
    }

    // Read cours par chapitre (accessible Teacher + Parent)
    public List<Cours> getCoursByChapitre(int chapitreId) throws SQLException {
        List<Cours> coursList = new ArrayList<>();
        String query = "SELECT * FROM cours WHERE chapitre_id = ?";
        try (PreparedStatement stmt = cnx.prepareStatement(query)) {
            stmt.setInt(1, chapitreId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Cours cours = new Cours();
                cours.setId(rs.getInt("id"));
                cours.setTitre(rs.getString("titre"));
                cours.setDescription(rs.getString("description"));
                cours.setChapitreId(rs.getInt("chapitre_id"));

                Blob blob = rs.getBlob("contenu_fichier");
                if (blob != null) {
                    cours.setContenuFichier(blob.getBytes(1, (int) blob.length()));
                } else {
                    cours.setContenuFichier(null);
                }
                coursList.add(cours);
            }
        }
        return coursList;
    }

    // Download File
    public void downloadFile(int coursId, String outputPath) throws SQLException, IOException {
        if (!isTeacher()) {
            throw new SecurityException("Accès refusé : seuls les enseignants peuvent télécharger des fichiers.");
        }

        String query = "SELECT contenu_fichier FROM cours WHERE id = ?";
        try (PreparedStatement stmt = cnx.prepareStatement(query)) {
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
