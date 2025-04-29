package tn.elearning.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;
import tn.elearning.entities.User;
import tn.elearning.services.ServiceUser;
import tn.elearning.utils.UserSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class SigninController {

    @FXML
    private TextField emailclient;

    @FXML
    private TextField mdpclient;

    @FXML
    private Label errorLabel;

    private final ServiceUser serviceUser = new ServiceUser();

    @FXML
    void signinonclick(ActionEvent event) {
        String email = emailclient.getText().trim();
        String password = mdpclient.getText().trim();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Champs vides", "Veuillez remplir tous les champs.", Alert.AlertType.WARNING);
            return;
        }

        try {
            // Chercher l'utilisateur par son email
            User user = serviceUser.findByEmail(email);

            if (user != null) {
                // Vérifier si le mot de passe entré correspond à celui haché stocké
                if (BCrypt.checkpw(password, user.getPassword())) {
                    System.out.println("Connexion réussie : " + user.getNom());

                    // Charger les rôles de l'utilisateur
                    List<String> roles = user.getRoles();
                    System.out.println("Rôles : " + roles);

                    // Stocker l'utilisateur dans la session
                    UserSession.getInstance().setUser(user);

                    // Déterminer la vue à charger selon les rôles de l'utilisateur
                    String fxmlPath;
                    if (roles.contains("ROLE_TEACHER")) {
                        fxmlPath = "/cours.fxml";
                    } else if (roles.contains("ROLE_PARENT")) {
                        fxmlPath = "/AfficherPaiements.fxml";
                    } else {
                        fxmlPath = "/listusers.fxml";
                    }

                    // Charger et afficher la scène appropriée
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
                    Parent root = loader.load();

                    // Vérifie si la scène existe avant de la modifier
                    if (emailclient.getScene() != null) {
                        Stage stage = (Stage) emailclient.getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.show();
                    } else {
                        System.err.println("Erreur : scène non trouvée pour le changement.");
                        showAlert("Erreur", "Une erreur s'est produite lors du changement de scène.", Alert.AlertType.ERROR);
                    }

                } else {
                    showAlert("Erreur", "Email ou mot de passe incorrect.", Alert.AlertType.ERROR);
                }

            } else {
                showAlert("Erreur", "Utilisateur non trouvé.", Alert.AlertType.ERROR);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erreur SQL", "Problème lors de la connexion à la base de données.", Alert.AlertType.ERROR);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible de charger la vue.", Alert.AlertType.ERROR);
        }
    }
    @FXML
    private void handleBack(ActionEvent event) {
        try {
            // Charger le fichier FXML de la page d'accueil
            Parent homePage = FXMLLoader.load(getClass().getResource("/Acceuil.fxml"));

            // Obtenir la fenêtre (Stage) actuelle
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            // Définir la nouvelle scène avec la page d'accueil
            stage.setScene(new Scene(homePage));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleForgotPassword(ActionEvent event) {
        try {
            // Charger la vue forgot-password.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/forgotpassword.fxml"));
            Parent root = loader.load();

            // Récupérer la scène actuelle
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Changer de scène
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            errorLabel.setText("Erreur lors du chargement de la page");
            e.printStackTrace();
        }
    }
}


