package tn.elearning.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import tn.elearning.services.ServiceUser;

import java.io.IOException;
import java.sql.SQLException;

public class EnterCode {

    @FXML
    private TextField codeField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private Label statusLabel;

    private String generatedCode;
    private String userEmail;

    private final ServiceUser serviceUser = new ServiceUser();

    public void setGeneratedCode(String code) {
        this.generatedCode = code;
    }

    public void setUserEmail(String email) {
        this.userEmail = email;
    }

    @FXML
    private void handleValidate(MouseEvent event) {
        String enteredCode = codeField.getText();
        String newPassword = newPasswordField.getText();

        if (enteredCode.equals(generatedCode)) {
            // Mettre à jour le mot de passe
            try {
                serviceUser.updatePassword(userEmail, newPassword);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            statusLabel.setText("Mot de passe réinitialisé !");
            statusLabel.setStyle("-fx-text-fill: green;");
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/signin.fxml"));
                Parent root = loader.load();
                codeField.getScene().setRoot(root);

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

        } else {
            statusLabel.setText("Code incorrect !");
            statusLabel.setStyle("-fx-text-fill: red;");
        }
    }
}
