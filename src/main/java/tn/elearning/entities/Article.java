package tn.elearning.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "articles")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(nullable = false)
    private String titre;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String contenu;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP) // ou DATE si vous voulez seulement la date
    private LocalDateTime createdAt;

    private String image;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    public Article() {
        this.createdAt = LocalDateTime.now();
        this.comments = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setArticle(this);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setArticle(null);
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", contenu='" + contenu + '\'' +
                ", createdAt=" + createdAt +
                ", image='" + image + '\'' +
                ", userId=" + userId +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
