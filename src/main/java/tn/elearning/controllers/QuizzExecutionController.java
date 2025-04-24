package tn.elearning.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.elearning.entities.Suggestion;
import tn.elearning.entities.question;
import tn.elearning.entities.quizz;
import tn.elearning.services.ServiceQuestion;
import tn.elearning.services.ServiceSuggestion;

import java.io.IOException;
import java.util.List;

public class QuizzExecutionController {
    @FXML private Label questionLabel;
    @FXML private VBox suggestionBox;
    @FXML private Button nextButton;
    private ToggleGroup suggestionGroup = new ToggleGroup();

    private List<question> questions;
    private int currentIndex = 0;
    private int score = 0;
    private quizz selectedQuizz;

    private final ServiceQuestion sq = new ServiceQuestion();
    private final ServiceSuggestion ss = new ServiceSuggestion();

    public void setQuizz(quizz q) {
        this.selectedQuizz = q;
        questions = sq.recupererParQuizzId(q.getId());
        showQuestion();
    }

    private void showQuestion() {
        if (currentIndex >= questions.size()) {
            showResult();
            return;
        }
        question current = questions.get(currentIndex);
        questionLabel.setText(current.getQuestion());
        suggestionBox.getChildren().clear();
        suggestionGroup = new ToggleGroup();

        List<Suggestion> suggestions = ss.recupererParQuestion(current.getId());
        for (Suggestion s : suggestions) {
            RadioButton rb = new RadioButton(s.getContenu());
            rb.setUserData(s);
            rb.setToggleGroup(suggestionGroup);
            suggestionBox.getChildren().add(rb);
        }
    }

    @FXML
    public void handleSuivant() {
        Toggle selected = suggestionGroup.getSelectedToggle();
        if (selected != null) {
            Suggestion s = (Suggestion) selected.getUserData();
            if (s.getEstCorrecte()) score++;
        }
        currentIndex++;
        showQuestion();
    }

    private void showResult() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ResultatView.fxml"));
            VBox root = loader.load();

            ResultatController ctrl = loader.getController();
            ctrl.setScore(score, questions.size());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Votre score");
            stage.show();

            ((Stage) questionLabel.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}