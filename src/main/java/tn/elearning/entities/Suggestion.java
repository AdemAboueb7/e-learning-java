package tn.elearning.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "suggestion")
public class Suggestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private question question;

    @NotBlank
    @Column(nullable = false)
    private String contenu;

    @NotNull
    @Column(name = "est_correcte", nullable = false)
    private Boolean estCorrecte;

    // Constructeurs
    public Suggestion() {}

    public Suggestion(question question, String contenu, Boolean estCorrecte) {
        this.question = question;
        this.contenu = contenu;
        this.estCorrecte = estCorrecte;
    }

    // Getters et Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public question getQuestion() {
        return question;
    }

    public void setQuestion(question question) {
        this.question = question;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Boolean getEstCorrecte() {
        return estCorrecte;
    }

    public void setEstCorrecte(Boolean estCorrecte) {
        this.estCorrecte = estCorrecte;
    }
}
