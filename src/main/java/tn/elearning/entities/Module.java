package tn.elearning.entities;

import java.util.List;

public class Module {
    private int id;
    private String nom;
    private List<User> users; // Relation One-to-Many avec User
    private List<Chapitre> chapitres; // Relation One-to-Many avec Chapitre

    // Constructeurs
    public Module() {}

    public Module(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Chapitre> getChapitres() {
        return chapitres;
    }

    public void setChapitres(List<Chapitre> chapitres) {
        this.chapitres = chapitres;
    }

    @Override
    public String toString() {
        return "Module{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                '}';
    }
}
