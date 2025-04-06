package tn.elearning.entities;

import java.util.List;

public class Chapitre {
    private int id;
    private Module module; // Relation Many-to-One avec Module
    private String nom;
    private List<Cours> cours; // Relation One-to-Many avec Cours

    // Constructeurs
    public Chapitre() {}

    public Chapitre(int id, Module module, String nom) {
        this.id = id;
        this.module = module;
        this.nom = nom;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Cours> getCours() {
        return cours;
    }

    public void setCours(List<Cours> cours) {
        this.cours = cours;
    }

    @Override
    public String toString() {
        return "Chapitre{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                '}';
    }
}
