package tn.elearning.services;

import tn.elearning.entities.Article;
import tn.elearning.entities.Comment;
import tn.elearning.utils.DataSource;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CommentService implements IServices<Comment> {
    private Connection cnx;

    public CommentService() {
        cnx = DataSource.getInstance().getConnection();
    }

    @Override
    public List<Comment> recuperer() throws SQLException {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT * FROM comment ORDER BY created_at DESC";

        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
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
                Article article = articleService.getArticleById(articleId);
                comment.setArticle(article);

                comments.add(comment);
            }
        }
        return comments;
    }

    @Override
    public Comment recupererParId(int id) throws SQLException {
        return null;
    }

    @Override
    public void ajouter(Comment comment) throws SQLException {
        String sql = "INSERT INTO comment (content, user_id, article_id, created_at) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setString(1, comment.getContenu());
            ps.setInt(2, comment.getUserId());
            ps.setInt(3, comment.getArticle().getId());
            ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            ps.executeUpdate();
        }
    }

    @Override
    public void supprimer(Comment comment) throws SQLException {
        String sql = "DELETE FROM comment WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setInt(1, comment.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void modifier(Comment comment) throws SQLException {
        String sql = "UPDATE comment SET content = ? WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setString(1, comment.getContenu());
            ps.setInt(2, comment.getId());
            ps.executeUpdate();
        }
    }

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
                Article article = articleService.getArticleById(articleId);
                comment.setArticle(article);

                return comment;
            }
        }
        return null;
    }
}
