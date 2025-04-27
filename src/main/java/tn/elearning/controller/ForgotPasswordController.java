package tn.elearning.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.elearning.services.ServiceUser;
import tn.elearning.utils.EmailSender;

import java.util.Random;

public class ForgotPasswordController {

    @FXML
    private TextField emailField;

    @FXML
    private Label statusLabel;

    private final ServiceUser serviceUser = new ServiceUser();

    private String generatedCode;
    private String userEmail;

    @FXML
    private void handleSendResetLink() {
        userEmail = emailField.getText().trim();

        if (userEmail.isEmpty()) {
            showError("Veuillez entrer votre email.");
            return;
        }

        try {
            // 1. Générer un code à 6 chiffres
            generatedCode = String.format("%06d", new Random().nextInt(999999));

            // 2. Envoyer le code par email
            String subject = "Votre code de réinitialisation";
            String body = "Votre code de réinitialisation est : " + generatedCode;
            EmailSender.sendEmail(userEmail, subject, body);

            // 3. Afficher succès
            showSuccess("Un email avec le code de réinitialisation a été envoyé.");

            // 4. Charger l'écran de vérification du code
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EnterCode.fxml"));
            Parent root = loader.load();

            // 5. Passer le code et l'email au nouveau contrôleur
            EnterCode controller = loader.getController();
            controller.setGeneratedCode(generatedCode);
            controller.setUserEmail(userEmail);

            // 6. Ouvrir la nouvelle fenêtre
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(new Scene(root));

        } catch (Exception e) {
            showError("Erreur lors de l'envoi de l'email.");
            e.printStackTrace();
        }
    }

    private void showError(String message) {
        statusLabel.setText(message);
        statusLabel.setStyle("-fx-text-fill: red;");
    }

    private void showSuccess(String message) {
        statusLabel.setText(message);
        statusLabel.setStyle("-fx-text-fill: green;");
    }
}
