package tn.elearning.services;

import tn.elearning.entities.question;
import tn.elearning.entities.quizz;
import tn.elearning.tools.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceQuestion {

    private final Connection con;

    public ServiceQuestion() {
        this.con = MyDataBase.getInstance().getConnection();
    }

    public void ajouter(question q) {
        String sql = "INSERT INTO question (question, idq_id) VALUES (?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, q.getQuestion());
            ps.setInt(2, q.getQuizz().getId());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                q.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            System.out.println("❌ Erreur ajout question : " + e.getMessage());
        }
    }

    public List<question> recupererParQuizzId(int quizzId) {
        List<question> questions = new ArrayList<>();
        String sql = "SELECT * FROM question WHERE idq_id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, quizzId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                question q = new question();
                q.setId(rs.getInt("id"));
                q.setQuestion(rs.getString("question"));

                quizz quiz = new quizz();
                quiz.setId(quizzId);
                q.setQuizz(quiz);

                questions.add(q);
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur récupération questions : " + e.getMessage());
        }
        return questions;
    }

    public void supprimer(int id) {
        String sql = "DELETE FROM question WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("🗑️ Question supprimée !");
        } catch (SQLException e) {
            System.err.println("❌ Erreur suppression question : " + e.getMessage());
        }
    }

    public void modifier(question q) {
        String sql = "UPDATE question SET question = ? WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, q.getQuestion());
            ps.setInt(2, q.getId());
            ps.executeUpdate();
            System.out.println("✏️ Question modifiée !");
        } catch (SQLException e) {
            System.err.println("❌ Erreur modification question : " + e.getMessage());
        }
    }

    public List<question> getAll() {
        List<question> questions = new ArrayList<>();
        try {
            String sql = "SELECT * FROM question";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                question q = new question();
                q.setId(rs.getInt("id"));
                q.setQuestion(rs.getString("question"));

                quizz quiz = new quizz();
                quiz.setId(rs.getInt("idq_id"));
                q.setQuizz(quiz);

                questions.add(q);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }
}
