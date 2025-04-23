package tn.elearning.entities;




import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "cours")
public class Cours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(nullable = false)
    private String titre;

    @Column(name = "contenu_fichier")
    private String contenuFichier;

    @ManyToOne
    @JoinColumn(name = "chapitre_id", nullable = false)
    private Chapitre chapitre;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    private String description;

    public Cours() {
        this.updatedAt = LocalDateTime.now();
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

    public String getContenuFichier() {
        return contenuFichier;
    }

    public void setContenuFichier(String contenuFichier) {
        this.contenuFichier = contenuFichier;
    }

    public Chapitre getChapitre() {
        return chapitre;
    }

    public void setChapitre(Chapitre chapitre) {
        this.chapitre = chapitre;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Cours{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", contenuFichier='" + contenuFichier + '\'' +
                ", chapitre=" + chapitre +
                ", updatedAt=" + updatedAt +
                ", description='" + description + '\'' +
                '}';
    }
}