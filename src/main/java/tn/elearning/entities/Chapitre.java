package tn.elearning.entities;




import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chapitres")
public class Chapitre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(nullable = false)
    private String nom;

    @ManyToOne
    @JoinColumn(name = "module_id", nullable = false)
    private Module module;
    private String description;

    @OneToMany(mappedBy = "chapitre", cascade = CascadeType.ALL)
    private List<Cours> cours = new ArrayList<>();

    public Chapitre() {
        this.cours = new ArrayList<>();
    }

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

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public List<Cours> getCours() {
        return cours;
    }

    public void setCours(List<Cours> cours) {
        this.cours = cours;
    }

    public void addCours(Cours cours) {
        this.cours.add(cours);
        cours.setChapitre(this);
    }

    public void removeCours(Cours cours) {
        this.cours.remove(cours);
        cours.setChapitre(null);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Chapitre{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", module=" + module +
                ", description='" + description + '\'' +
                ", cours=" + cours +
                '}';
    }
}