package tn.elearning.services;

import tn.elearning.entities.quizz;
import tn.elearning.tools.MyDataBase;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceQuizz implements IServices<quizz> {

    private final Connection con;

    public ServiceQuizz() {
        this.con = MyDataBase.getInstance().getConnection();
    }

    @Override
    public void ajouter(quizz q) {
        String sql = "INSERT INTO quizz (matiere, chapitre, bestg, difficulte, etat, gain) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, q.getMatiere());
            ps.setInt(2, q.getChapitre());
            ps.setString(3, q.getBestg());
            ps.setInt(4, q.getDifficulte());
            ps.setString(5, q.getEtat());
            ps.setString(6, q.getGain());
            ps.executeUpdate();
            System.out.println("✅ Quizz ajouté avec succès.");
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de l'ajout du quizz : " + e.getMessage());
        }
    }

    @Override
    public void supprimer(quizz q) {
        String sql = "DELETE FROM quizz WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, q.getId());
            int rows = ps.executeUpdate();
            System.out.println(rows > 0 ? "✅ Quizz supprimé." : "Aucun quizz trouvé.");
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la suppression : " + e.getMessage());
        }
    }

    @Override
    public void modifier(int id, String nom) {
        String sql = "UPDATE quizz SET matiere = ? WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nom);
            ps.setInt(2, id);
            int rows = ps.executeUpdate();
            System.out.println(rows > 0 ? "✅ Quizz modifié." : "Aucun quizz trouvé.");
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la modification : " + e.getMessage());
        }
    }

    @Override
    public List<quizz> recuperer() {
        List<quizz> list = new ArrayList<>();
        String sql = "SELECT * FROM quizz";
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                quizz q = new quizz();
                q.setId(rs.getInt("id"));
                q.setMatiere(rs.getString("matiere"));
                q.setChapitre(rs.getInt("chapitre"));
                q.setBestg(rs.getString("bestg"));
                q.setDifficulte(rs.getInt("difficulte"));
                q.setEtat(rs.getString("etat"));
                q.setGain(rs.getString("gain"));
                list.add(q);
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la récupération : " + e.getMessage());
        }
        return list;
    }
}
