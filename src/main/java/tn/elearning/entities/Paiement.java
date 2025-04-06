package tn.elearning.entities;

import java.time.LocalDate;

public class Paiement {
    private int id;
    private Abonnement abonnement; // Relation Many-to-One avec Abonnement
    private User user; // Relation Many-to-One avec User
    private String nom;
    private String email;
    private String typeCarte;
    private String numCarte;
    private LocalDate dateExpiration;
    private int cvv;
    private double montant;
    private LocalDate datePaiement;

    // Constructeurs
    public Paiement() {}

    public Paiement(int id, Abonnement abonnement, User user, String nom, String email, 
                   String typeCarte, String numCarte, LocalDate dateExpiration, 
                   int cvv, double montant, LocalDate datePaiement) {
        this.id = id;
        this.abonnement = abonnement;
        this.user = user;
        this.nom = nom;
        this.email = email;
        this.typeCarte = typeCarte;
        this.numCarte = numCarte;
        this.dateExpiration = dateExpiration;
        this.cvv = cvv;
        this.montant = montant;
        this.datePaiement = datePaiement;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Abonnement getAbonnement() {
        return abonnement;
    }

    public void setAbonnement(Abonnement abonnement) {
        this.abonnement = abonnement;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTypeCarte() {
        return typeCarte;
    }

    public void setTypeCarte(String typeCarte) {
        this.typeCarte = typeCarte;
    }

    public String getNumCarte() {
        return numCarte;
    }

    public void setNumCarte(String numCarte) {
        this.numCarte = numCarte;
    }

    public LocalDate getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(LocalDate dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public LocalDate getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(LocalDate datePaiement) {
        this.datePaiement = datePaiement;
    }

    @Override
    public String toString() {
        return "Paiement{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", typeCarte='" + typeCarte + '\'' +
                ", numCarte='" + numCarte + '\'' +
                ", dateExpiration=" + dateExpiration +
                ", cvv=" + cvv +
                ", montant=" + montant +
                ", datePaiement=" + datePaiement +
                '}';
    }
}
