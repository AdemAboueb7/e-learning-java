package tn.elearning.entities;





import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

@Entity
@Table(name = "paiements")
public class Paiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Positive
    private Double montant;

    @NotNull
    private LocalDateTime date_paiement;
    private String stripe_session_id;
    private String status;

    @ManyToOne
    @JoinColumn(name = "id_abonnement", nullable = false)
    private Abonnement abonnement;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Paiement() {
        this.date_paiement = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    @Override
    public String toString() {
        return "Paiement{" +
                "id=" + id +
                ", montant=" + montant +
                ", date_paiement=" + date_paiement +
                ", stripe_session_id='" + stripe_session_id + '\'' +
                ", status='" + status + '\'' +
                ", abonnement=" + abonnement +
                ", user=" + user +
                '}';
    }
}