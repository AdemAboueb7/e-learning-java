package tn.elearning.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.sql.Date;

@Entity
@Table(name = "paiements")
public class Paiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Positive
    private Double montant;

    @NotNull
    private LocalDateTime date_paiement;
    private String stripe_session_id;


    @ManyToOne
    @JoinColumn(name = "id_abonnement", nullable = false)
    private Abonnement abonnement;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Paiement(Double montant,Abonnement abonnement, LocalDateTime date_paiement,User user) {
        this.montant = montant;
        this.abonnement = abonnement;
        this.date_paiement = date_paiement;
        this.user = user;


    }

    public Paiement() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public LocalDateTime getDate() {
        return date_paiement;
    }

    public void setDate(LocalDateTime date) {
        this.date_paiement = date;
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

    public String getStripe_session_id() {
        return stripe_session_id;
    }
    public void setStripe_session_id(String stripe_session_id) {
        this.stripe_session_id = stripe_session_id;
    }

    public Integer getIdAbonnement() {
        return abonnement.getId();
    }
    public String getTypeAbonnement() {
        if (abonnement != null) {
            return abonnement.getType(); // Assurez-vous que getType() existe dans la classe Abonnement
        }
        return "Type non défini";  // Si l'abonnement est null
    }


    @Override
    public String toString() {
        return "Paiement{" +
                "id=" + id +
                ", montant=" + montant +
                ", date_paiement=" + date_paiement +
                ", stripe_session_id='" + stripe_session_id + '\'' +
                ", abonnement=" + abonnement +
                ", user=" + user +
                '}';
    }
}