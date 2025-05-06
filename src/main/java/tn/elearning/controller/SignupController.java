package tn.elearning.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.elearning.entities.User;
import tn.elearning.services.ServiceUser;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;

public class SignupController {
    @FXML
    private Hyperlink backLink;
    @FXML
    private Label errorLabel;

    @FXML
    private TextField adresseclient;

    @FXML
    private TextField emailclient;

    @FXML
    private TextField mdpclient;

    @FXML
    private TextField nomclient;

    @FXML
    private TextField numclient;

    @FXML
    private TextField professionclient;

    @FXML
    void signup(ActionEvent event) {
        String email = emailclient.getText().trim();
        String nom = nomclient.getText().trim();
        String mdp = mdpclient.getText().trim();
        String phone = numclient.getText().trim();
        String adresse = adresseclient.getText().trim();
        String profession = professionclient.getText().trim();

        if (nom.isEmpty() || email.isEmpty() || mdp.isEmpty() || phone.isEmpty() || adresse.isEmpty() || profession.isEmpty()) {
            showAlert("Erreur", "Tous les champs sont obligatoires !");
            return;
        }

        if (!email.matches("^\\S+@\\S+\\.\\S+$")) {
            showAlert("Erreur", "Email invalide !");
            return;
        }

        if (mdp.length() < 6) {
            showAlert("Erreur", "Le mot de passe doit contenir au moins 6 caractères.");
            return;
        }

        if (!phone.matches("\\d{8,15}")) {
            showAlert("Erreur", "Le numéro de téléphone doit contenir uniquement des chiffres (8 à 15 chiffres).");
            return;
        }

        // ✅ Création de l'utilisateur avec un mot de passe haché
        User user = new User();
        user.setEmail(email);
        user.setNom(nom);
        user.setPhoneNumber(phone);
        user.setAddress(adresse);
        user.setWork(profession);
        user.setActive(true);
        user.setRoles(Collections.singletonList("ROLE_PARENT"));

        // Hachage du mot de passe avec BCrypt avant de le sauvegarder
        String hashedPassword = BCrypt.hashpw(mdp, BCrypt.gensalt(12)); // Le "12" est le coût de calcul, vous pouvez ajuster si nécessaire
        user.setPassword(hashedPassword);

        // Appel du service pour ajouter l'utilisateur dans la base de données
        ServiceUser service = new ServiceUser();
        try {
            service.ajouterparent(user);
            showAlert("Succès", "Utilisateur ajouté avec succès avec le rôle PARENT !");
        } catch (SQLException e) {
            showAlert("Erreur SQL", e.getMessage());
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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}


