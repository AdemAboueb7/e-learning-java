package tn.elearning.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "question")
public class question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(nullable = false)
    private String question;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "quizz_id", nullable = false)
    private quizz quizz;

    // Constructeurs
    public question() {}

    public question(String question, quizz quizz) {
        this.question = question;
        this.quizz = quizz;
    }

    // Getters et Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public quizz getQuizz() {
        return quizz;
    }

    public void setQuizz(quizz quizz) {
        this.quizz = quizz;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", quizz=" + (quizz != null ? quizz.getId() : null) +
                '}';
    }
}
