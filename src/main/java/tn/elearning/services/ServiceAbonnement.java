package tn.elearning.services;

import tn.elearning.entities.Abonnement;
import tn.elearning.tools.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceAbonnement implements IServices<Abonnement> {
    Connection cnx = MyDataBase.getInstance().getCnx();
    @Override
    public void ajouter(Abonnement abonnement) throws SQLException {
        String sql = "insert into abonnement(type,prix,duree,description)values (?,?,?,?)";
        PreparedStatement ps = this.cnx.prepareStatement(sql);
        ps.setDouble(2, abonnement.getPrix());
        ps.setString(1, abonnement.getType());
        ps.setString(3, abonnement.getDuree());
        ps.setString(4, abonnement.getDescription());
        ps.executeUpdate();
        System.out.println("abonnement ajouté");

    }

    @Override
    public void supprimer(Abonnement abonnement) throws SQLException {
        String sql = "delete from abonnement where id=" + abonnement.getId();
        Statement statement = cnx.createStatement();
        statement.executeUpdate(sql);
        System.out.println("abonnement supprimé");

    }

    @Override
    public void modifier(Abonnement a) throws SQLException {
        String sql = "UPDATE abonnement SET type = ?, prix = ?, description = ?, duree = ? WHERE id = ?";
        PreparedStatement ps = this.cnx.prepareStatement(sql);
        ps.setString(1, a.getType());
        ps.setDouble(2, a.getPrix());
        ps.setString(3, a.getDescription());
        ps.setString(4, a.getDuree());
        ps.setInt(5, a.getId());

        ps.executeUpdate();
        System.out.println("Abonnement modifié avec succès");

    }

    @Override
    public List<Abonnement> recuperer() throws SQLException {
        String sql = "select * from abonnement";
        Statement st = this.cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);
        List<Abonnement> abonnements = new ArrayList();

        while(rs.next()) {
            Abonnement a = new Abonnement();
            a.setId(rs.getInt("id"));
            a.setType(rs.getString("type"));
            a.setDescription(rs.getString("description"));
            a.setDuree(rs.getString("duree"));
            a.setPrix(rs.getDouble("prix"));
            abonnements.add(a);
        }

        return abonnements;
    }

    @Override
    public Abonnement recupererParId(int id) throws SQLException {
        String sql = "SELECT * FROM abonnement WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            Abonnement a = new Abonnement();
            a.setId(rs.getInt("id"));
            a.setType(rs.getString("type"));
            a.setPrix(rs.getDouble("prix"));
            a.setDescription(rs.getString("description"));
            a.setDuree(rs.getString("duree"));
            return a;
        }

        return null;
    }
}
