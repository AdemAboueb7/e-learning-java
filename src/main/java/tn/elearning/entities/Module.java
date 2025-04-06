package tn.elearning.entities;



import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "modules")
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(nullable = false)
    private String nom;
    private String description;

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL)
    private List<Chapitre> chapitres = new ArrayList<>();

    @OneToMany(mappedBy = "idMatiere")
    private List<User> users = new ArrayList<>();

    public Module() {
        this.chapitres = new ArrayList<>();
        this.users = new ArrayList<>();
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

    public List<Chapitre> getChapitres() {
        return chapitres;
    }

    public void setChapitres(List<Chapitre> chapitres) {
        this.chapitres = chapitres;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addChapitre(Chapitre chapitre) {
        chapitres.add(chapitre);
        chapitre.setModule(this);
    }

    public void removeChapitre(Chapitre chapitre) {
        chapitres.remove(chapitre);
        chapitre.setModule(null);
    }

    @Override
    public String toString() {
        return "Module{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", chapitres=" + chapitres +
                ", users=" + users +
                '}';
    }
}