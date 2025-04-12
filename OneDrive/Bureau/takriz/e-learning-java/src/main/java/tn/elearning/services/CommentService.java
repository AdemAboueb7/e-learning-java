package tn.elearning.services;

import tn.elearning.entities.Article;
import tn.elearning.entities.Comment;
import tn.elearning.tools.MyDataBase;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CommentService implements IServices<Comment> {
    Connection cnx;

    public CommentService() {
        cnx = MyDataBase.getInstance().getCnx();
    }

    @Override
    public void ajouter(Comment comment) throws SQLException {
        String sql = "INSERT INTO comment (content, created_at, user_id, article_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, comment.getContenu());
            ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(3, comment.getUserId());
            ps.setInt(4, comment.getArticle().getId());

            ps.executeUpdate();

            // Récupérer l'ID généré
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                comment.setId(rs.getInt(1));
            }
            System.out.println("Commentaire ajouté avec succès");
        }
    }

    @Override
    public void supprimer(Comment comment) throws SQLException {
        String sql = "DELETE FROM comment WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setInt(1, comment.getId());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Commentaire supprimé avec succès");
            } else {
                System.out.println("Aucun commentaire trouvé avec cet ID");
            }
        }
    }

    @Override
    public void modifier(int id, String nom) throws SQLException {
        String sql = "UPDATE comment SET content = ? WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setString(1, nom);
            ps.setInt(2, id);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Commentaire modifié avec succès");
            } else {
                System.out.println("Aucun commentaire trouvé avec cet ID");
            }
        }
    }

    @Override
    public List<Comment> recuperer() throws SQLException {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT * FROM comment";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while(rs.next()) {
            Comment comment = new Comment();
            comment.setId(rs.getInt("id"));
            comment.setContenu(rs.getString("content"));
            comment.setUserId(rs.getInt("user_id"));

            Timestamp createdAt = rs.getTimestamp("created_at");
            if (createdAt != null) {
                comment.setCreatedAt(createdAt.toLocalDateTime());
            }

            // Récupérer l'article associé
            int articleId = rs.getInt("article_id");
            ArticleService articleService = new ArticleService();
            Article article = articleService.getById(articleId);
            comment.setArticle(article);

            comments.add(comment);
        }

        return comments;
    }

    // Méthode utilitaire pour récupérer un commentaire par son ID
    public Comment getById(int id) throws SQLException {
        String sql = "SELECT * FROM comment WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Comment comment = new Comment();
                comment.setId(rs.getInt("id"));
                comment.setContenu(rs.getString("content"));
                comment.setUserId(rs.getInt("user_id"));

                Timestamp createdAt = rs.getTimestamp("created_at");
                if (createdAt != null) {
                    comment.setCreatedAt(createdAt.toLocalDateTime());
                }

                // Récupérer l'article associé
                int articleId = rs.getInt("article_id");
                ArticleService articleService = new ArticleService();
                Article article = articleService.getById(articleId);
                comment.setArticle(article);

                return comment;
            }
        }
        return null;
    }
}
