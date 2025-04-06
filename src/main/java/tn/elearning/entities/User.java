package tn.elearning.entities;

import java.util.List;

public class User {
    private int id;
    private String email;
    private String nom;
    private String phonenumber;
    private String matiere;
    private Integer experience;
    private String reason;
    private String password;
    private String roles;
    private String work;
    private String adress;
    private String pref;
    private boolean isActive;
    private Module module; // Relation avec Module (idmatiere_id)
    private List<Article> articles; // Relation One-to-Many avec Article
    private List<Comment> comments; // Relation One-to-Many avec Comment
    private List<Participation> participations; // Relation One-to-Many avec Participation
    private List<Paiement> paiements; // Relation One-to-Many avec Paiement

    // Constructeurs
    public User() {}

    public User(int id, String email, String nom, String phonenumber, String matiere, 
                Integer experience, String reason, String password, String roles, 
                String work, String adress, String pref, boolean isActive) {
        this.id = id;
        this.email = email;
        this.nom = nom;
        this.phonenumber = phonenumber;
        this.matiere = matiere;
        this.experience = experience;
        this.reason = reason;
        this.password = password;
        this.roles = roles;
        this.work = work;
        this.adress = adress;
        this.pref = pref;
        this.isActive = isActive;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
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

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
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

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
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
                ", phonenumber='" + phonenumber + '\'' +
                ", matiere='" + matiere + '\'' +
                ", experience=" + experience +
                ", reason='" + reason + '\'' +
                ", roles='" + roles + '\'' +
                ", work='" + work + '\'' +
                ", adress='" + adress + '\'' +
                ", pref='" + pref + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
