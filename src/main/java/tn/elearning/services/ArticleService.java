package tn.elearning.services;

import tn.elearning.entities.Article;
import tn.elearning.tools.MyDataBase;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ArticleService implements IServices<Article> {
    Connection cnx;

    public ArticleService() {
        cnx = MyDataBase.getInstance().getCnx();
    }

    @Override
    public void ajouter(Article article) throws SQLException {
        if (cnx == null || cnx.isClosed()) {
            cnx = MyDataBase.getInstance().getCnx();
            if (cnx == null) {
                throw new SQLException("Database connection is null");
            }
        }
        
        String sql = "INSERT INTO article (title, content, category, user_id, image, created_at) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, article.getTitle());
            ps.setString(2, article.getContent());
            ps.setString(3, article.getCategory());
            ps.setInt(4, article.getUserId());
            ps.setString(5, article.getImage() != null ? article.getImage() : "default.jpg");
            ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));

            System.out.println("Executing SQL: " + sql);
            System.out.println("Parameters: " + article.getTitle() + ", " + article.getContent() + ", " + 
                              article.getCategory() + ", " + article.getUserId() + ", " + article.getImage());
            
            int rowsAffected = ps.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);

            // Récupérer l'ID généré
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                article.setId(rs.getInt(1));
                System.out.println("Generated ID: " + article.getId());
            }
            System.out.println("Article ajouté avec succès");
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void supprimer(Article article) throws SQLException {
        String sql = "DELETE FROM article WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setInt(1, article.getId());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Article supprimé avec succès");
            } else {
                System.out.println("Aucun article trouvé avec cet ID");
            }
        }
    }

    @Override
    public void modifier(int id, String nom) throws SQLException {
        String sql = "UPDATE article SET title = ? WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setString(1, nom);
            ps.setInt(2, id);
            
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Article modifié avec succès");
            } else {
                System.out.println("Aucun article trouvé avec cet ID");
            }
        }
    }

    @Override
    public List<Article> recuperer() throws SQLException {
        List<Article> articles = new ArrayList<>();
        String sql = "SELECT * FROM article";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);
        
        while(rs.next()) {
            Article article = new Article();
            article.setId(rs.getInt("id"));
            article.setTitle(rs.getString("title"));
            article.setContent(rs.getString("content"));
            article.setCategory(rs.getString("category"));
            article.setUserId(rs.getInt("user_id"));
            article.setImage(rs.getString("image"));
            
            Timestamp createdAt = rs.getTimestamp("created_at");
            if (createdAt != null) {
                article.setCreatedAt(createdAt.toLocalDateTime());
            }
            
            articles.add(article);
        }
        
        return articles;
    }
    
    // Méthode utilitaire pour récupérer un article par son ID
    public Article getById(int id) throws SQLException {
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
                article.setImage(rs.getString("image"));
                
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
