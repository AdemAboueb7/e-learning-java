package tn.elearning.services;

import tn.elearning.entities.Article;
import tn.elearning.utils.DataSource;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ArticleService implements IServices<Article> {
    private Connection cnx;

    public ArticleService() {
        cnx = DataSource.getInstance().getConnection();
    }

    @Override
    public void ajouter(Article article) throws SQLException {
        String sql = "INSERT INTO article (title, content, category, user_id, created_at) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setString(1, article.getTitle());
            ps.setString(2, article.getContent());
            ps.setString(3, article.getCategory());
            ps.setInt(4, article.getUserId());
            ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            ps.executeUpdate();
        }
    }

    @Override
    public void supprimer(Article article) throws SQLException {
        String sql = "DELETE FROM article WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setInt(1, article.getId());
            ps.executeUpdate();
        }
    }

    public void deleteArticle(int id) throws SQLException {
        String sql = "DELETE FROM article WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public void modifier(Article article) throws SQLException {
        String sql = "UPDATE article SET title = ?, content = ?, category = ? WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setString(1, article.getTitle());
            ps.setString(2, article.getContent());
            ps.setString(3, article.getCategory());
            ps.setInt(4, article.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public List<Article> recuperer() throws SQLException {
        List<Article> articles = new ArrayList<>();
        String sql = "SELECT * FROM article ORDER BY created_at DESC";

        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Article article = new Article();
                article.setId(rs.getInt("id"));
                article.setTitle(rs.getString("title"));
                article.setContent(rs.getString("content"));
                article.setCategory(rs.getString("category"));
                article.setUserId(rs.getInt("user_id"));

                Timestamp createdAt = rs.getTimestamp("created_at");
                if (createdAt != null) {
                    article.setCreatedAt(createdAt.toLocalDateTime());
                }

                articles.add(article);
            }
        }
        return articles;
    }

    @Override
    public Article recupererParId(int id) throws SQLException {
        return null;
    }

    public Article getArticleById(int id) throws SQLException {
        String sql = "SELECT * FROM article WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Article article = new Article();
                article.setId(rs.getInt("id"));
                article.setTitle(rs.getString("title"));
                article.setContent(rs.getString("content"));
                article.setCategory(rs.getString("category"));
                article.setUserId(rs.getInt("user_id"));

                Timestamp createdAt = rs.getTimestamp("created_at");
                if (createdAt != null) {
                    article.setCreatedAt(createdAt.toLocalDateTime());
                }

                return article;
            }
        }
        return null;
    }
}
