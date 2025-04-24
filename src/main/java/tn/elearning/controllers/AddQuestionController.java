package tn.elearning.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import tn.elearning.entities.quizz;
import tn.elearning.services.ServiceQuizz;
import tn.elearning.services.ServiceQuestion;
import tn.elearning.services.ServiceSuggestion;
import tn.elearning.entities.question;
import tn.elearning.entities.Suggestion;

public class AddQuestionController {
    @FXML private ComboBox<quizz> quizzComboBox;
    @FXML private TextField questionField, sugg1, sugg2, sugg3, sugg4, indexBonneReponse;
    @FXML private Label errorLabel;

    private final ServiceQuestion serviceQuestion = new ServiceQuestion();
    private final ServiceSuggestion serviceSuggestion = new ServiceSuggestion();
    private final ServiceQuizz serviceQuizz = new ServiceQuizz();

    @FXML
    public void initialize() {
        // Utilise la méthode 'recuperer()' au lieu de 'getAll()'
        quizzComboBox.getItems().addAll(serviceQuizz.recuperer());
    }

    @FXML
    public void handleAjouter() {
        String texteQuestion = questionField.getText().trim();
        quizz selectedQuizz = quizzComboBox.getValue();

        if (texteQuestion.isEmpty() || selectedQuizz == null ||
                sugg1.getText().isEmpty() || sugg2.getText().isEmpty() ||
                sugg3.getText().isEmpty() || sugg4.getText().isEmpty() ||
                indexBonneReponse.getText().isEmpty()) {
            errorLabel.setText("❌ Tous les champs sont obligatoires.");
            return;
        }

        try {
            int bonneReponse = Integer.parseInt(indexBonneReponse.getText());
            if (bonneReponse < 1 || bonneReponse > 4) throw new NumberFormatException();

            question q = new question(texteQuestion, selectedQuizz);
            serviceQuestion.ajouter(q);

            String[] suggestions = {sugg1.getText(), sugg2.getText(), sugg3.getText(), sugg4.getText()};
            for (int i = 0; i < suggestions.length; i++) {
                boolean estCorrecte = (i + 1 == bonneReponse);
                serviceSuggestion.ajouter(new Suggestion(q, suggestions[i], estCorrecte));
            }

            errorLabel.setText("✅ Question ajoutée avec succès !");
            questionField.clear(); sugg1.clear(); sugg2.clear(); sugg3.clear(); sugg4.clear(); indexBonneReponse.clear();

        } catch (NumberFormatException e) {
            errorLabel.setText("❌ La bonne réponse doit être un nombre entre 1 et 4.");
        }
    }

    @FXML
    public void handleVoirQuestions() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ListQuestionView.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Liste des Questions");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
