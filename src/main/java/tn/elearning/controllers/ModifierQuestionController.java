package tn.elearning.controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import tn.elearning.entities.question;
import tn.elearning.services.ServiceQuestion;

import java.io.IOException;

public class ModifierQuestionController {

    @FXML private TextField questionField;
    @FXML private Label errorLabel;

    private final ServiceQuestion service = new ServiceQuestion();
    private question selectedQuestion;

    public void setQuestion(question q) {
        this.selectedQuestion = q;
        questionField.setText(q.getQuestion());
    }

    @FXML
    public void handleModifier() {
        String newText = questionField.getText().trim();
        if (newText.isEmpty()) {
            errorLabel.setText("❌ La question ne peut pas être vide.");
            return;
        }

        selectedQuestion.setQuestion(newText);
        service.modifier(selectedQuestion);
        errorLabel.setText("✅ Question modifiée !");
    }

    @FXML
    public void handleRetour() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ListeQuestionView.fxml"));
            VBox root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Liste des Questions");
            stage.show();

            // Fermer la fenêtre actuelle
            ((Stage) questionField.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
