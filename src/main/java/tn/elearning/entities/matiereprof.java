package tn.elearning.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "matiereprof")
public class matiereprof {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idmatiereprof")
    private Integer id;

    @NotBlank
    @Column(name = "nommatiereprof", nullable = false)
    private String nom;

    // Constructeurs
    public matiereprof() {}

    public matiereprof(String nom) {
        this.nom = nom;
    }

    // Getters / Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return nom;
    }
}
