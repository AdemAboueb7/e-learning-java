package tn.elearning.entities;




import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank
    private String nom;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String matiere;
    private Integer experience;
    private String reason;

    @NotBlank
    private String password;

    @ElementCollection
    @CollectionTable(name = "user_roles")
    private List<String> roles = new ArrayList<>();

    private String work;
    private String address;
    private String pref;

    @Column(name = "is_active")
    private boolean isActive = false;

    @ManyToOne
    @JoinColumn(name = "id_matiere")
    private Module idMatiere;



    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Participation> participations = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Paiement> paiements = new ArrayList<>();

    public User() {
    }


    public User(Integer id,String nom,String email,String phoneNumber,String password,String matiere,int experience,String reason,String work,String address,String pref,List<String> roles) {
        this.id = id;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.matiere = matiere;
        this.experience = experience;
        this.reason = reason;
        this.work = work;
        this.address = address;
        this.pref = pref;
        this.roles=roles;
        this.nom=nom;

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPref() {
        return pref;
    }

    public void setPref(String pref) {
        this.pref = pref;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Module getIdMatiere() {
        return idMatiere;
    }

    public void setIdMatiere(Module idMatiere) {
        this.idMatiere = idMatiere;
    }





    public List<Participation> getParticipations() {
        return participations;
    }

    public void setParticipations(List<Participation> participations) {
        this.participations = participations;
    }

    public List<Paiement> getPaiements() {
        return paiements;
    }

    public void setPaiements(List<Paiement> paiements) {
        this.paiements = paiements;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", nom='" + nom + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", matiere='" + matiere + '\'' +
                ", experience=" + experience +
                ", reason='" + reason + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                ", work='" + work + '\'' +
                ", address='" + address + '\'' +
                ", pref='" + pref + '\'' +
                ", isActive=" + isActive +
                ", idMatiere=" + idMatiere +
                ", participations=" + participations +
                ", paiements=" + paiements +
                '}';
    }
}