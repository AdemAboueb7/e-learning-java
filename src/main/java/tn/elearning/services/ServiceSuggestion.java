package tn.elearning.services;

import tn.elearning.entities.Suggestion;
import tn.elearning.entities.question;
import tn.elearning.tools.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceSuggestion {

    private final Connection con;

    public ServiceSuggestion() {
        con = MyDataBase.getInstance().getConnection();
    }

    public void ajouter(Suggestion s) {
        String sql = "INSERT INTO suggestion (question_id, contenu, est_correcte) VALUES (?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, s.getQuestion().getId());
            ps.setString(2, s.getContenu());
            ps.setBoolean(3, s.getEstCorrecte());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Suggestion> getByQuestionId(int questionId) {
        List<Suggestion> list = new ArrayList<>();
        String sql = "SELECT * FROM suggestion WHERE question_id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, questionId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Suggestion s = new Suggestion();
                s.setId(rs.getInt("id"));
                s.setContenu(rs.getString("contenu"));
                s.setEstCorrecte(rs.getBoolean("est_correcte"));
                s.setQuestion(new question());
                list.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Suggestion> recupererParQuestion(int questionId) {
        List<Suggestion> list = new ArrayList<>();
        String sql = "SELECT * FROM suggestion WHERE question_id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, questionId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Suggestion s = new Suggestion();
                s.setId(rs.getInt("id"));
                s.setContenu(rs.getString("contenu"));
                s.setEstCorrecte(rs.getBoolean("est_correcte"));

                question q = new question();
                q.setId(questionId);
                s.setQuestion(q);

                list.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void supprimer(int id) {
        String sql = "DELETE FROM suggestion WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modifier(Suggestion s) {
        String sql = "UPDATE suggestion SET contenu = ?, est_correcte = ? WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, s.getContenu());
            ps.setBoolean(2, s.getEstCorrecte());
            ps.setInt(3, s.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}