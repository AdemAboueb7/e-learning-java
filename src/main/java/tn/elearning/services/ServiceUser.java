package tn.elearning.services;

import tn.elearning.entities.Module;
import tn.elearning.entities.User;
import tn.elearning.tools.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
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
        String sql = "DELETE FROM user WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setInt(1, user.getId());
        ps.executeUpdate();
        System.out.println("User supprimé !");
    }

    @Override
    public void modifier(User user) throws SQLException {

    }

    public void modifierprofil(User user) throws SQLException {
        String sql = "UPDATE user SET email = ?, nom = ?, phonenumber = ? WHERE id = ?";

        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setString(1, user.getEmail());
        ps.setString(2, user.getNom());
        ps.setString(3, user.getPhoneNumber());
        ps.setInt(4, user.getId());

        ps.executeUpdate();
        System.out.println("User updated successfully!");
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
        String sql = "SELECT * FROM user";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setEmail(rs.getString("email"));
            user.setNom(rs.getString("nom"));
            user.setPhoneNumber(rs.getString("phonenumber"));
            user.setMatiere(rs.getString("matiere"));
            user.setExperience(rs.getObject("experience") != null ? rs.getInt("experience") : null);
            user.setReason(rs.getString("reason"));
            user.setPassword(rs.getString("password"));
            user.setWork(rs.getString("work"));
            user.setAddress(rs.getString("adress"));
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

    public User recupererParId(int id) throws SQLException {
        String sql = "SELECT * FROM user WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setEmail(rs.getString("email"));
            user.setNom(rs.getString("nom"));
            user.setPhoneNumber(rs.getString("phonenumber"));
            user.setMatiere(rs.getString("matiere"));
            user.setExperience(rs.getObject("experience") != null ? rs.getInt("experience") : null);
            user.setReason(rs.getString("reason"));
            user.setPassword(rs.getString("password"));
            user.setWork(rs.getString("work"));
            user.setAddress(rs.getString("adress"));
            user.setPref(rs.getString("pref"));
            user.setActive(rs.getBoolean("is_active"));
            return user;
        }
        return null;
        }

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, email, nom, phoneNumber, roles, is_active FROM user";
        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setNom(rs.getString("nom"));
                user.setPhoneNumber(rs.getString("phoneNumber"));
                user.setActive(rs.getBoolean("is_active"));

                // Traitement des rôles
                String rolesJson = rs.getString("roles");
                if (rolesJson != null && !rolesJson.isEmpty()) {
                    rolesJson = rolesJson.replace("[", "").replace("]", "").replace("\"", "");
                    List<String> roles = Arrays.asList(rolesJson.split(","));
                    user.setRoles(roles);
                }

                users.add(user);
            }
        }
        return users;
    }
    public User findByEmailAndPassword(String email, String password) throws SQLException {
        String query = "SELECT * FROM user WHERE email = ? AND password = ?";
        PreparedStatement ps = cnx.prepareStatement(query); // ⚠️ pas `connection`, mais `cnx` comme défini
        ps.setString(1, email);
        ps.setString(2, password);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setNom(rs.getString("nom"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            // Tu peux aussi set d'autres champs si tu veux
            return user;
        }
        return null;
    }
    public void updateUserStatus(int userId, boolean isActive) throws SQLException {
        String sql = "UPDATE user SET is_active = ? WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setBoolean(1, isActive);
            ps.setInt(2, userId);
            ps.executeUpdate();
        }
    }

}

