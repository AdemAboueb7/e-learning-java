package tn.elearning.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class Cours {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final IntegerProperty chapitreId = new SimpleIntegerProperty();
    private final StringProperty titre = new SimpleStringProperty();
    private byte[] contenuFichier;
    private final StringProperty updatedAt = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();

    // Getters and Setters for properties
    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public int getChapitreId() {
        return chapitreId.get();
    }

    public void setChapitreId(int chapitreId) {
        this.chapitreId.set(chapitreId);
    }

    public IntegerProperty chapitreIdProperty() {
        return chapitreId;
    }

    public String getTitre() {
        return titre.get();
    }
    private int ratingSum;
    private int ratingCount;

    public void setTitre(String titre) {
        this.titre.set(titre);
    }

    public StringProperty titreProperty() {
        return titre;
    }

    public byte[] getContenuFichier() {
        return contenuFichier;
    }

    public void setContenuFichier(byte[] contenuFichier) {
        this.contenuFichier = contenuFichier;
    }



    public String getUpdatedAt() {
        return updatedAt.get();
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt.set(updatedAt);
    }

    public StringProperty updatedAtProperty() {
        return updatedAt;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public StringProperty descriptionProperty() {
        return description;
    }
    public int getRatingSum() {
        return ratingSum;
    }
    public double getAverageRating() {
        return ratingCount > 0 ? (double) ratingSum / ratingCount : 0;
    }

    // Add this method to update rating
    public void addRating(int stars) {
        this.ratingSum += stars;
        this.ratingCount++;
    }
    public void setRatingSum(int ratingSum) {
        this.ratingSum = ratingSum;
    }

    // Getter and Setter for ratingCount
    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }
}
