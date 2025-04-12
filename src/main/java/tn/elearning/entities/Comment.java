package tn.elearning.entities;

import java.time.LocalDateTime;

public class Comment {
    private int id;
    private Article article;
    private int userId;
    private String contenu;
    private LocalDateTime createdAt;

    public Comment() {
        this.createdAt = LocalDateTime.now();
    }

    public Comment(int id, Article article, int userId, String contenu, LocalDateTime createdAt) {
        this.id = id;
        this.article = article;
        this.userId = userId;
        this.contenu = contenu;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", userId=" + userId +
                ", contenu='" + contenu + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
