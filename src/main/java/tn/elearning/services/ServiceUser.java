package tn.elearning.services;


import tn.elearning.entities.Module;
import tn.elearning.entities.User;
import tn.elearning.tools.MyDataBase;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ServiceUser implements IServices<User> {
    private final Connection cnx;

    public ServiceUser() {

        cnx = MyDataBase.getInstance().getCnx();
    }

    @Override
    public void ajouter(User user) throws SQLException {
        String sql = "INSERT INTO user (email, nom, phonenumber, matiere, experience, reason, password, work, adress, pref, is_active, roles, idmatiereprof) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setString(1, user.getEmail());
        ps.setString(2, user.getNom());
        ps.setString(3, user.getPhoneNumber());
        ps.setString(4, user.getMatiere());
        ps.setString(12, String.join(",", user.getRoles()));
        ps.setInt(13, user.getMatiereProf().getId());

        // Si l'expérience est nulle, on insère une valeur NULL dans la base de données
        if (user.getExperience() != null) {
            ps.setInt(5, user.getExperience());
        } else {
            ps.setNull(5, Types.INTEGER);
        }

        ps.setString(6, user.getReason());

        // Utilisez le mot de passe haché (assurez-vous que vous avez haché avant de passer à cette méthode)
        ps.setString(7, user.getPassword()); // Ici vous passez le mot de passe haché

        ps.setString(8, user.getWork());
        ps.setString(9, user.getAddress());
        ps.setString(10, user.getPref());
        ps.setBoolean(11, user.isActive());

        // Exécution de l'insertion
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

            // Récupérer l'image de profil si elle existe (champ 'contenuimage')
            byte[] imageContent = rs.getBytes("contenuimage");
            user.setImageContent(imageContent);

            return user;
        }
        return null;  // Retourner null si aucun utilisateur n'est trouvé
    }

    public   List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, email, nom, phoneNumber, roles, is_active, experience FROM user";
        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setNom(rs.getString("nom"));
                user.setPhoneNumber(rs.getString("phoneNumber"));
                user.setActive(rs.getBoolean("is_active"));
                user.setExperience(rs.getObject("experience") != null ? rs.getInt("experience") : null);

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
        PreparedStatement ps = cnx.prepareStatement(query);
        ps.setString(1, email);
        ps.setString(2, password);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setNom(rs.getString("nom"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            String rolesStr = rs.getString("roles");
            rolesStr = rolesStr.replace("[", "").replace("]", "").replace("\"", "");
            List<String> roles = Arrays.asList(rolesStr.split(","));
            user.setRoles(roles);
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
    public String generateResetToken(String email) {
        String token = UUID.randomUUID().toString();
        return token;
    }
    public void updateUserImage(int userId, String imageName, byte[] imageContent) throws SQLException {
        String sql = "UPDATE user SET nomimage = ?, contenuimage = ? WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setString(1, imageName);           // image_name -> nomimage
            ps.setBytes(2, imageContent);         // image_content -> contenuimage
            ps.setInt(3, userId);                 // id de l'utilisateur

            ps.executeUpdate();
            System.out.println("Image de l'utilisateur mise à jour !");
        }
    }
    public void saveResetToken(String email, String token) throws SQLException {
        String query = "UPDATE user SET reset_token = ?, token_expiration = ? WHERE email = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            // Token valable 24 heures
            Timestamp expiration = new Timestamp(System.currentTimeMillis() + 24 * 60 * 60 * 1000);

            ps.setString(1, token);
            ps.setTimestamp(2, expiration);
            ps.setString(3, email);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated == 0) {
                throw new SQLException("Aucun utilisateur trouvé avec cet email: " + email);
            }
        }
    }

    // Vérifie si un token est valide
    public boolean isResetTokenValid(String email, String token) throws SQLException {
        String query = "SELECT 1 FROM user WHERE email = ? AND reset_token = ? AND token_expiration > NOW()";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, email);
            ps.setString(2, token);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }

    // Réinitialise le mot de passe avec un token valide
    public boolean resetPasswordWithToken(String token, String newPassword) throws SQLException {
        // 1. Vérifier d'abord si le token est valide
        String checkQuery = "SELECT email FROM user WHERE reset_token = ? AND token_expiration > NOW()";
        try (PreparedStatement checkPs = cnx.prepareStatement(checkQuery)) {
            checkPs.setString(1, token);
            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {
                String email = rs.getString("email");

                // 2. Mettre à jour le mot de passe
                String updateQuery = "UPDATE user SET password = ?, reset_token = NULL, token_expiration = NULL WHERE email = ?";
                try (PreparedStatement updatePs = cnx.prepareStatement(updateQuery)) {
                    updatePs.setString(1, newPassword);
                    updatePs.setString(2, email);
                    return updatePs.executeUpdate() > 0;
                }
            }
        }
        return false;
    }
    public String generateAndSaveResetToken(String email) throws SQLException {
        // 1. Génère un token unique
        String token = UUID.randomUUID().toString();

        // 2. Sauvegarde le token dans la base de données
        saveResetToken(email, token);

        // 3. Retourne le token généré (pour l'envoyer par email)
        return token;
    }
    public void updatePassword(String email, String newPassword) throws SQLException {
        // 1. Hasher le mot de passe avant de le sauvegarder
        String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

        // 2. Mettre à jour le mot de passe dans la base de données
        String sql = "UPDATE user SET password = ? WHERE email = ?";
        try (PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setString(1, hashedPassword);
            ps.setString(2, email);
            ps.executeUpdate();
            System.out.println("Mot de passe mis à jour pour l'email: " + email);
        }
    }
    public int getTeachersCountByMatiere(String matiere) throws SQLException {
        String sql = "SELECT COUNT(*) FROM user u " +
                "JOIN matiereprof mp ON u.idmatiereprof = mp.idmatiereprof " +
                "WHERE mp.nommatiereprof = ? AND u.roles LIKE '%ROLE_TEACHER%'";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setString(1, matiere);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1); // Récupérer le nombre d'enseignants pour cette matière
        }

        return 0;
    }
    public User findByEmail(String email) throws SQLException {
        String query = "SELECT * FROM user WHERE email = ?";
        PreparedStatement ps = cnx.prepareStatement(query);
        ps.setString(1, email);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setNom(rs.getString("nom"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            String rolesStr = rs.getString("roles");
            rolesStr = rolesStr.replace("[", "").replace("]", "").replace("\"", "");
            List<String> roles = Arrays.asList(rolesStr.split(","));
            user.setRoles(roles);
            return user;
        }
        return null;  // Si l'utilisateur n'est pas trouvé
    }





}

