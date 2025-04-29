package tn.elearning.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class AcceuilController {

    @FXML
    private Button signupButton;

    @FXML
    private Button signupTeacherButton;

    @FXML
    private Button signinButton;

    // Méthode pour le bouton "Créer un compte"
    @FXML
    private void handleSignup(ActionEvent event) {
        loadFXML("/Signup.fxml", event);
    }

    // Méthode pour le bouton "Devenir formateur"
    @FXML
    private void handleSignupTeacher(ActionEvent event) {
        loadFXML("/Signupteacher.fxml", event);
    }

    // Méthode pour le bouton "Se connecter"
    @FXML
    private void handleSignin(ActionEvent event) {
        loadFXML("/Signin.fxml", event);
    }

    // Méthode pour le menu "Quitter"
    @FXML
    private void handleQuit(ActionEvent event) {
        Stage stage = (Stage) signinButton.getScene().getWindow();
        stage.close();
    }

    // Méthode pour le menu "À propos"
    @FXML
    private void handleAbout(ActionEvent event) {
        try {
            // Chargez une nouvelle fenêtre ou une alerte avec les informations
            Parent root = FXMLLoader.load(getClass().getResource("/About.fxml"));
            Stage stage = new Stage();
            stage.setTitle("À propos");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Méthode utilitaire pour charger les FXML
    private void loadFXML(String fxmlPath, ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));

            // Adapté pour un bouton ou n'importe quel Node
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}