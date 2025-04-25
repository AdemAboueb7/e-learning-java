package tn.elearning.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class Chapitre {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty nom = new SimpleStringProperty();
    private final IntegerProperty moduleId = new SimpleIntegerProperty();
    private final StringProperty moduleNom = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();

    // Getters and Setters for the properties
    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getNom() {
        return nom.get();
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }

    public StringProperty nomProperty() {
        return nom;
    }

    public int getModuleId() {
        return moduleId.get();
    }

    public void setModuleId(int moduleId) {
        this.moduleId.set(moduleId);
    }

    public IntegerProperty moduleIdProperty() {
        return moduleId;
    }

    public String getModuleNom() {
        return moduleNom.get();
    }

    public void setModuleNom(String moduleNom) {
        this.moduleNom.set(moduleNom);
    }

    public StringProperty moduleNomProperty() {
        return moduleNom;
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
    @Override
    public String toString() {
        return getNom();  // This ensures the ComboBox shows the chapter name.
    }

}
