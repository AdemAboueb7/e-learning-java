package tn.elearning.services;

import tn.elearning.entities.Module;
import tn.elearning.entities.User;
import tn.elearning.tools.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceUser implements IServices<User> {
    private final Connection cnx;

    public ServiceUser() {
        cnx = MyDataBase.getInstance().getCnx();
    }

    @Override
    public void ajouter(User user) throws SQLException {
        String sql = "INSERT INTO user (email, nom, phonenumber, matiere, experience, reason, password, work, adress, pref, is_active,roles) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setString(1, user.getEmail());
        ps.setString(2, user.getNom());
        ps.setString(3, user.getPhoneNumber());
        ps.setString(4, user.getMatiere());
        ps.setString(12, String.join(",", user.getRoles()));


        if (user.getExperience() != null)
            ps.setInt(5, user.getExperience());
        else
            ps.setNull(5, Types.INTEGER);

        ps.setString(6, user.getReason());
        ps.setString(7, user.getPassword());
        ps.setString(8, user.getWork());
        ps.setString(9, user.getAddress());
        ps.setString(10, user.getPref());
        ps.setBoolean(11, user.isActive());



        ps.executeUpdate();
        System.out.println("User ajouté !");
    }

    @Override
    public void supprimer(User user) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setInt(1, user.getId());
        ps.executeUpdate();
        System.out.println("User supprimé !");
    }

    @Override
    public void modifier(User user) throws SQLException {

    }


    public void modifier(int id, String nom) throws SQLException {
        String sql = "UPDATE users SET nom = ? WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setString(1, nom);
        ps.setInt(2, id);
        ps.executeUpdate();
        System.out.println("User modifié !");
    }

    @Override
    public List<User> recuperer() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setEmail(rs.getString("email"));
            user.setNom(rs.getString("nom"));
            user.setPhoneNumber(rs.getString("phone_number"));
            user.setMatiere(rs.getString("matiere"));
            user.setExperience(rs.getObject("experience") != null ? rs.getInt("experience") : null);
            user.setReason(rs.getString("reason"));
            user.setPassword(rs.getString("password"));
            user.setWork(rs.getString("work"));
            user.setAddress(rs.getString("address"));
            user.setPref(rs.getString("pref"));
            user.setActive(rs.getBoolean("is_active"));

            int matiereId = rs.getInt("id_matiere");
            if (!rs.wasNull()) {
                Module module = new Module();
                module.setId(matiereId);
                user.setIdMatiere(module);
            }

            users.add(user);
        }

        return users;
    }

    @Override
    public User recupererParId(int id) throws SQLException {
        return null;
    }
}

