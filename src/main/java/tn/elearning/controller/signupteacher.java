package tn.elearning.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import tn.elearning.entities.User;
import tn.elearning.services.ServiceUser;
import org.mindrot.jbcrypt.BCrypt;  // Importation de BCrypt

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;

public class signupteacher {

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
    void signup(ActionEvent event) {
        // Récupération des valeurs saisies par l'utilisateur
        String email = emailclient.getText().trim();
        String nom = nomclient.getText().trim();
        String mdp = mdpclient.getText().trim();
        String phone = numclient.getText().trim();
        String adresse = adresseclient.getText().trim();

        // Vérification des champs
        if (nom.isEmpty() || email.isEmpty() || mdp.isEmpty() || phone.isEmpty() || adresse.isEmpty()) {
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
        user.setActive(true);
        user.setRoles(Collections.singletonList("ROLE_TEACHER"));

        // Hachage du mot de passe avec BCrypt avant de le sauvegarder
        String hashedPassword = BCrypt.hashpw(mdp, BCrypt.gensalt(12));  // Hachage du mot de passe
        user.setPassword(hashedPassword);  // Assigner le mot de passe haché à l'utilisateur

        // Appel du service pour ajouter l'utilisateur dans la base de données
        ServiceUser service = new ServiceUser();
        try {
            service.ajouter(user);
            showAlert("Succès", "Utilisateur ajouté avec succès avec le rôle TEACHER !");
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/signin.fxml"));
                Parent root = loader.load();
                mdpclient.getScene().setRoot(root);

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } catch (SQLException e) {
            showAlert("Erreur SQL", e.getMessage());
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
