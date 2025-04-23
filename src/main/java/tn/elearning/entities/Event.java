package tn.elearning.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(nullable = false)
    private String titre;

    @NotBlank
    @Column(nullable = false)
    private String description;

    @NotBlank
    @Column(nullable = false)
    private String image;

    @NotNull
    @Column(name = "date_debut", nullable = false)
    private LocalDateTime dateDebut;

    @NotNull
    @Column(name = "date_fin", nullable = false)
    private LocalDateTime dateFin;

    @NotBlank
    @Column(nullable = false)
    private String type;

    @Column(nullable = true)
    private String localisation;

    @NotNull
    @Positive
    @Column(nullable = false)
    private Double prix;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<Participation> participations = new ArrayList<>();

    // Constructeurs
    public Event() {
        this.participations = new ArrayList<>();
    }

    // Getters et Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public LocalDateTime getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDateTime getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDateTime dateFin) {
        this.dateFin = dateFin;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public List<Participation> getParticipations() {
        return participations;
    }

    public void setParticipations(List<Participation> participations) {
        this.participations = participations;
    }

    public void addParticipation(Participation participation) {
        participations.add(participation);
        participation.setEvent(this);
    }

    public void removeParticipation(Participation participation) {
        participations.remove(participation);
        participation.setEvent(null);
    }
}

