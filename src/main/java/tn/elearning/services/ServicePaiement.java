package tn.elearning.services;

import tn.elearning.entities.Abonnement;
import tn.elearning.entities.Paiement;
import tn.elearning.entities.User;
import tn.elearning.tools.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicePaiement implements IServices<Paiement> {

    Connection cnx = MyDataBase.getInstance().getCnx();

    @Override
    public void ajouter(Paiement paiement) throws SQLException {
        String sql = "insert into paiement(montant,id_abonnement_id,date_paiement,userid_id) values(?,?,?,?)";
        PreparedStatement ps = this.cnx.prepareStatement(sql);
        ps.setDouble(1, paiement.getMontant());
        ps.setInt(2, paiement.getAbonnement().getId());
        ps.setTimestamp(3, Timestamp.valueOf(paiement.getDate()));
        ps.setInt(4, paiement.getUser().getId());

        ps.executeUpdate();
        System.out.println("paiement ajouté");

    }


    @Override
    public void supprimer(Paiement paiement) throws SQLException {
        String sql = "delete from personne where id=" + paiement.getId();
        Statement st = this.cnx.createStatement();
        st.executeUpdate(sql);
        System.out.println("Paiement supprimé");
    }


    @Override
    public void modifier(Paiement p) throws SQLException {
        String sql = "update paiement set montant=120.5 where id =" + p.getId();
        Statement st = this.cnx.createStatement();
        st.executeUpdate(sql);
        System.out.println("Paiement modifié");

    }

    @Override
    public List<Paiement> recuperer() throws SQLException {
        String sql = "select * from paiement";
        Statement st = this.cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);
        List<Paiement> paiements = new ArrayList();

        while (rs.next()) {
            Paiement p = new Paiement();
            p.setId(rs.getInt("id"));
            p.setMontant(rs.getDouble("montant"));
            Timestamp timestamp = rs.getTimestamp("date_paiement");
            p.setDate(timestamp.toLocalDateTime());
            Abonnement a = new Abonnement();
            a.setId(rs.getInt("id_abonnement_id"));
            p.setAbonnement(a);
            User u = new User();
            u.setId(rs.getInt("userid_id"));
            p.setUser(u);
            paiements.add(p);
        }

        return paiements;
    }


    @Override
    public Paiement recupererParId(int id) throws SQLException {
        String sql = "SELECT * FROM paiement WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        Paiement paiement = new Paiement();
        if (rs.next()) {

            paiement.setId(rs.getInt("id"));
            paiement.setMontant(rs.getDouble("montant"));

            Timestamp timestamp = rs.getTimestamp("date_paiement");
            paiement.setDate(timestamp.toLocalDateTime());
        }
        Abonnement a = new Abonnement();
        a.setId(rs.getInt("id_abonnement_id"));
        paiement.setAbonnement(a);
        User u = new User();
        u.setId(rs.getInt("userid_id"));
        paiement.setUser(u);
        return paiement;
    }




}

