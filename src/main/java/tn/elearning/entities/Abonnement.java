package tn.elearning.entities;



import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "abonnements")
public class Abonnement {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(nullable = false)
    private String type;

    @NotNull
    @Positive
    private Double prix;

    @Column(length = 1000)
    private String description;


    private String duree;

    @OneToMany(mappedBy = "abonnement", cascade = CascadeType.ALL)
    private List<Paiement> paiements = new ArrayList<>();

    public Abonnement() {
        this.paiements = new ArrayList<>();
    }
    public Abonnement(String type, Double prix, String description, String duree) {
        this.type = type;
        this.prix = prix;
        this.description = description;
        this.duree = duree;
        this.paiements = new ArrayList<>();
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public List<Paiement> getPaiements() {
        return paiements;
    }

    public void setPaiements(List<Paiement> paiements) {
        this.paiements = paiements;
    }

    public void addPaiement(Paiement paiement) {
        paiements.add(paiement);
        paiement.setAbonnement(this);
    }

    public void removePaiement(Paiement paiement) {
        paiements.remove(paiement);
        paiement.setAbonnement(null);
    }
    @Override
    public String toString() {
        return "Abonnement{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", prix=" + prix +
                ", description='" + description + '\'' +
                ", duree=" + duree +
                '}';
    }

}