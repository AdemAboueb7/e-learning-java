package tn.elearning.entities;

import java.util.List;

public class Abonnement {
    private int id;
    private String type;
    private double prix;
    private String duree;
    private String description;
    private List<Paiement> paiements; // Relation One-to-Many avec Paiement

    // Constructeurs
    public Abonnement() {}

    public Abonnement(int id, String type, double prix, String duree, String description) {
        this.id = id;
        this.type = type;
        this.prix = prix;
        this.duree = duree;
        this.description = description;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Paiement> getPaiements() {
        return paiements;
    }

    public void setPaiements(List<Paiement> paiements) {
        this.paiements = paiements;
    }

    @Override
    public String toString() {
        return "Abonnement{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", prix=" + prix +
                ", duree='" + duree + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
