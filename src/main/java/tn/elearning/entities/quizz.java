package tn.elearning.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "quizz")
public class quizz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(nullable = false)
    private String matiere;

    @NotNull
    @Column(nullable = false)
    private Integer chapitre;

    @Column(nullable = true)
    private String bestg;

    @NotNull
    @Column(nullable = false)
    private Integer difficulte;

    @Column(nullable = true)
    private String best;

    @Column(nullable = true)
    private String etat;

    @Column(nullable = true)
    private String gain;

    // Relation avec les questions
    @OneToMany(mappedBy = "quizz", cascade = CascadeType.ALL)
    private List<question> questions = new ArrayList<>();

    // Constructeurs
    public quizz() {}

    public quizz(String matiere, Integer chapitre, Integer difficulte) {
        this.matiere = matiere;
        this.chapitre = chapitre;
        this.difficulte = difficulte;
    }

    // Getters et Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public Integer getChapitre() {
        return chapitre;
    }

    public void setChapitre(Integer chapitre) {
        this.chapitre = chapitre;
    }

    public String getBestg() {
        return bestg;
    }

    public void setBestg(String bestg) {
        this.bestg = bestg;
    }

    public Integer getDifficulte() {
        return difficulte;
    }

    public void setDifficulte(Integer difficulte) {
        this.difficulte = difficulte;
    }

    public String getBest() {
        return best;
    }

    public void setBest(String best) {
        this.best = best;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getGain() {
        return gain;
    }

    public void setGain(String gain) {
        this.gain = gain;
    }

    public List<question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<question> questions) {
        this.questions = questions;
    }

    public void addQuestion(question question) {
        questions.add(question);

    }

    public void removeQuestion(question question) {
        questions.remove(question);
        question.setQuizz(null);
    }
}
