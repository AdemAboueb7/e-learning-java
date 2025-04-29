package tn.elearning.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Signup.fxml"));
            Stage stage = (Stage) signupButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Méthode pour le bouton "Rejoignez-nous"
    @FXML
    private void handleSignupTeacher(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Signupteacher.fxml"));
            Stage stage = (Stage) signupTeacherButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Méthode pour le bouton "Se connecter"
    @FXML
    private void handleSignin(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Signin.fxml"));
            Stage stage = (Stage) signinButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
