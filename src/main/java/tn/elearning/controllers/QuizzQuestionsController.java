// ✅ QuizzQuestionsController.java
package tn.elearning.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import tn.elearning.entities.question;
import tn.elearning.entities.quizz;
import tn.elearning.entities.Suggestion;
import tn.elearning.services.ServiceQuestion;
import tn.elearning.services.ServiceSuggestion;

import java.net.URL;
import java.util.*;

public class QuizzQuestionsController implements Initializable {

    @FXML private VBox questionsContainer;
    @FXML private Label labelQuizzTitle;
    @FXML private TextField emailField; // Champ de saisie de l'email (à ajouter dans le FXML)

    private quizz selectedQuizz;
    private final ServiceQuestion serviceQuestion = new ServiceQuestion();
    private final ServiceSuggestion serviceSuggestion = new ServiceSuggestion();
    private final Map<Integer, ToggleGroup> questionToggleGroups = new HashMap<>();

    public void setQuizz(quizz q) {
        this.selectedQuizz = q;
        labelQuizzTitle.setText("Quizz : " + q.getMatiere());

        List<question> questions = serviceQuestion.recupererParQuizzId(q.getId());

        for (question qst : questions) {
            Label qLabel = new Label(qst.getQuestion());
            qLabel.setStyle("-fx-font-weight: bold;");

            ToggleGroup group = new ToggleGroup();
            VBox qBox = new VBox(5, qLabel);

            for (Suggestion sugg : serviceSuggestion.recupererParQuestion(qst.getId())) {
                RadioButton rb = new RadioButton(sugg.getContenu());
                rb.setUserData(sugg);
                rb.setToggleGroup(group);
                qBox.getChildren().add(rb);
            }

            questionsContainer.getChildren().add(qBox);
            questionToggleGroups.put(qst.getId(), group);
        }
    }

    @FXML
    public void handleSoumettre() {
        int score = 0;
        for (Map.Entry<Integer, ToggleGroup> entry : questionToggleGroups.entrySet()) {
            Toggle selected = entry.getValue().getSelectedToggle();
            if (selected != null) {
                Suggestion s = (Suggestion) selected.getUserData();
                if (s.getEstCorrecte()) score++;
            }
        }

        String message = "Votre score : " + score + " / " + questionToggleGroups.size();
        Alert result = new Alert(Alert.AlertType.INFORMATION);
        result.setTitle("Résultat du Quizz");
        result.setHeaderText(message);
        result.showAndWait();

        String recipientEmail = emailField.getText();
        if (!recipientEmail.isEmpty()) {
            System.out.println("📧 (Simulation) Résultat envoyé à : " + recipientEmail);
            System.out.println("Contenu : " + message);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Rien à faire ici
    }
}
