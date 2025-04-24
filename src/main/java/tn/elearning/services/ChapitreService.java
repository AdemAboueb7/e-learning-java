package tn.elearning.services;

import tn.elearning.entities.Chapitre;
import tn.elearning.tools.MyDataBase;
import tn.elearning.entities.Module;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChapitreService implements IServices<Chapitre>{

    Connection cnx = MyDataBase.getInstance().getCnx();

    @Override
    public void ajouter(Chapitre c) throws SQLException {
        String sql = "INSERT INTO chapitre(nom, description, module_id) VALUES (?, ?, ?)";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setString(1, c.getNom());
        ps.setString(2, c.getDescription());
        ps.setInt(3, c.getModule().getId());
        ps.executeUpdate();
    }

    @Override
    public void modifier(Chapitre c) throws SQLException {
        String sql = "UPDATE chapitre SET nom = ?, description = ?, module_id = ? WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setString(1, c.getNom());
        ps.setString(2, c.getDescription());
        ps.setInt(3, c.getModule().getId());
        ps.setInt(4, c.getId());
        ps.executeUpdate();
    }

    @Override
    public void supprimer(Chapitre c) throws SQLException {
        String sql = "DELETE FROM chapitre WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setInt(1, c.getId());
        ps.executeUpdate();
    }

    @Override
    public void modifier(int id, String nom) throws SQLException {

    }

    @Override
    public List<Chapitre> recuperer() throws SQLException {
        String sql = "SELECT * FROM chapitre";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);
        List<Chapitre> chapitres = new ArrayList<>();
        while (rs.next()) {
            Chapitre c = new Chapitre();
            c.setId(rs.getInt("id"));
            c.setNom(rs.getString("nom"));
            c.setDescription(rs.getString("description"));
            Module m = new Module();
            m.setId(rs.getInt("module_id"));
            c.setModule(m);
            chapitres.add(c);
        }
        return chapitres;
    }

    @Override
    public Chapitre recupererParId(int id) throws SQLException {
        String sql = "SELECT * FROM chapitres WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            Chapitre c = new Chapitre();
            c.setId(rs.getInt("id"));
            c.setNom(rs.getString("nom"));
            c.setDescription(rs.getString("description"));
            Module m = new Module();
            m.setId(rs.getInt("module_id"));
            c.setModule(m);
            return c;
        }
        return null;
    }

    public List<Chapitre> recupererParModule(int moduleId) throws SQLException {
        String sql = "SELECT * FROM chapitres WHERE module_id = ?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setInt(1, moduleId);
        ResultSet rs = ps.executeQuery();
        List<Chapitre> chapitres = new ArrayList<>();
        while (rs.next()) {
            Chapitre c = new Chapitre();
            c.setId(rs.getInt("id"));
            c.setNom(rs.getString("nom"));
            c.setDescription(rs.getString("description"));
            Module m = new Module();
            m.setId(rs.getInt("module_id"));
            c.setModule(m);
            chapitres.add(c);
        }
        return chapitres;
    }
}