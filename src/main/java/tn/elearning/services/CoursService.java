package tn.elearning.services;

import tn.elearning.entities.Cours;
import tn.elearning.entities.Chapitre;
import tn.elearning.tools.MyDataBase;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CoursService implements IServices<Cours> {
    Connection cnx = MyDataBase.getInstance().getCnx();

    @Override
    public void ajouter(Cours cours) throws SQLException {
        String sql = "INSERT INTO cours(titre, contenu_fichier, chapitre_id, updated_at, description) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setString(1, cours.getTitre());
        ps.setString(2, cours.getContenuFichier());
        ps.setInt(3, cours.getChapitre().getId());
        ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(5, cours.getDescription());
        ps.executeUpdate();
    }

    @Override
    public void modifier(Cours cours) throws SQLException {
        String sql = "UPDATE cours SET titre = ?, contenu_fichier = ?, chapitre_id = ?, updated_at = ?, description = ? WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setString(1, cours.getTitre());
        ps.setString(2, cours.getContenuFichier());
        ps.setInt(3, cours.getChapitre().getId());
        ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(5, cours.getDescription());
        ps.setInt(6, cours.getId());
        ps.executeUpdate();
    }

    @Override
    public void supprimer(Cours cours) throws SQLException {
        String sql = "DELETE FROM cours WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setInt(1, cours.getId());
        ps.executeUpdate();
    }

    @Override
    public void modifier(int id, String nom) throws SQLException {

    }

    @Override
    public List<Cours> recuperer() throws SQLException {
        String sql = "SELECT * FROM cours";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);
        List<Cours> coursList = new ArrayList<>();
        while (rs.next()) {
            Cours c = new Cours();
            c.setId(rs.getInt("id"));
            c.setTitre(rs.getString("titre"));
            c.setContenuFichier(rs.getString("contenu_fichier"));
            c.setDescription(rs.getString("description"));
            c.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
            Chapitre ch = new Chapitre();
            ch.setId(rs.getInt("chapitre_id"));
            c.setChapitre(ch);
            coursList.add(c);
        }
        return coursList;
    }

    @Override
    public Cours recupererParId(int id) throws SQLException {
        String sql = "SELECT * FROM cours WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            Cours c = new Cours();
            c.setId(rs.getInt("id"));
            c.setTitre(rs.getString("titre"));
            c.setContenuFichier(rs.getString("contenu_fichier"));
            c.setDescription(rs.getString("description"));
            c.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
            Chapitre ch = new Chapitre();
            ch.setId(rs.getInt("chapitre_id"));
            c.setChapitre(ch);
            return c;
        }
        return null;
    }


}