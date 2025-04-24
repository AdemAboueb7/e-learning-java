package tn.elearning.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import tn.elearning.entities.User;
import tn.elearning.services.ServiceUser;

import java.sql.SQLException;
import java.util.Collections;

public class SignupController {

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

        // ✅ Création de l'utilisateur
        User user = new User();
        user.setEmail(email);
        user.setNom(nom);
        user.setPhoneNumber(phone);
        user.setPassword(mdp);
        user.setAddress(adresse);
        user.setWork(profession);
        user.setActive(true);
        user.setRoles(Collections.singletonList("ROLE_PARENT"));

        ServiceUser service = new ServiceUser();
        try {
            service.ajouter(user);
            showAlert("Succès", "Utilisateur ajouté avec succès avec le rôle PARENT !");
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

