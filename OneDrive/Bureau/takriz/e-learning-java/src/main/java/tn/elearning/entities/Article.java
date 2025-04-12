package tn.elearning.entities;

import java.time.LocalDateTime;
import java.util.List;

public class Article {
    private int id;
    private String title;
    private String content;
    private String category;
    private LocalDateTime createdAt;
    private int userId; // Changement : utilisation de l'ID au lieu de l'objet User
    private String image;
    private LocalDateTime updatedAt;
    private List<Comment> comments;

    // Constructeurs
    public Article() {}

    public Article(int id, String title, String content, String category,
                   LocalDateTime createdAt, int userId, String image, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
        this.createdAt = createdAt;
        this.userId = userId;
        this.image = image;
        this.updatedAt = updatedAt;
    }

<<<<<<< HEAD
=======
    public Article(String category, String content, String title) {
        this.category = category;
        this.content = content;
        this.title = title;
    }

>>>>>>> 9443531 (Ajout des fichiers dans le dossier resources)
    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", category='" + category + '\'' +
                ", createdAt=" + createdAt +
                ", userId=" + userId +
                ", image='" + image + '\'' +
                ", updatedAt=" + updatedAt +
                '}';
    }
}