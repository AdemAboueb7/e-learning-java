package tn.elearning.entities;

import java.time.LocalDateTime;

public class Cours {
    private int id;
    private Chapitre chapitre; // Relation Many-to-One avec Chapitre
    private String titre;
    private String contenuFichier;
    private LocalDateTime updatedAt;

    // Constructeurs
    public Cours() {}

    public Cours(int id, Chapitre chapitre, String titre, String contenuFichier, LocalDateTime updatedAt) {
        this.id = id;
        this.chapitre = chapitre;
        this.titre = titre;
        this.contenuFichier = contenuFichier;
        this.updatedAt = updatedAt;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Chapitre getChapitre() {
        return chapitre;
    }

    public void setChapitre(Chapitre chapitre) {
        this.chapitre = chapitre;
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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Cours{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", contenuFichier='" + contenuFichier + '\'' +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
