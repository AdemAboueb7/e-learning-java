package tn.elearning.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.elearning.entities.quizz;
import tn.elearning.services.ServiceQuizz;

import java.io.IOException;

public class AddQuizzController {

    @FXML private TextField matiereField;
    @FXML private TextField chapitreField;
    @FXML private TextField difficulteField;
    @FXML private TextField bestgField;
    @FXML private TextField etatField;
    @FXML private TextField gainField;
    @FXML private Label errorLabel;

    private final ServiceQuizz service = new ServiceQuizz();

    @FXML
    public void handleAjouter() {
        String matiere = matiereField.getText().trim();
        String chapitreText = chapitreField.getText().trim();
        String difficulteText = difficulteField.getText().trim();
        String bestg = bestgField.getText().trim();
        String etat = etatField.getText().trim();
        String gain = gainField.getText().trim();

        // Vérification des champs vides
        if (matiere.isEmpty()) {
            errorLabel.setText("❌ La matière est obligatoire.");
            return;
        }

        if (chapitreText.isEmpty() || !chapitreText.matches("\\d+")) {
            errorLabel.setText("❌ Le chapitre doit être un nombre entier.");
            return;
        }

        if (difficulteText.isEmpty() || !difficulteText.matches("[1-5]")) {
            errorLabel.setText("❌ La difficulté doit être un nombre entre 1 et 5.");
            return;
        }

        if (bestg.isEmpty()) {
            errorLabel.setText("❌ Le champ BestG est requis.");
            return;
        }

        if (etat.isEmpty() || (!etat.equalsIgnoreCase("1") && !etat.equalsIgnoreCase("0"))) {
            errorLabel.setText("❌ L’état doit être 'actif=1' ou 'inactif=0'.");
            return;
        }

        if (gain.isEmpty() || !gain.matches("^[0-9]+( points)?$")) {
            errorLabel.setText("❌ Le gain doit être un nombre suivi éventuellement de 'points'.");
            return;
        }

        try {
            int chapitre = Integer.parseInt(chapitreText);
            int difficulte = Integer.parseInt(difficulteText);

            quizz q = new quizz(matiere, chapitre, bestg, difficulte, etat, gain);
            service.ajouter(q);
            errorLabel.setText("✅ Quizz ajouté avec succès !");

            // Nettoyage
            matiereField.clear();
            chapitreField.clear();
            difficulteField.clear();
            bestgField.clear();
            etatField.clear();
            gainField.clear();
        } catch (NumberFormatException e) {
            errorLabel.setText("❌ Erreur inattendue : données non numériques.");
        }
    }


    @FXML
    public void handleVoirListe() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ListeQuizzView.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Liste des Quizz");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void handleAjouterQuestion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AddQuestionView.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Ajouter une Question");
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
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
