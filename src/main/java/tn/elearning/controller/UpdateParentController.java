package tn.elearning.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import tn.elearning.entities.User;
import tn.elearning.services.ServiceUser;
import tn.elearning.utils.UserSession;

import java.sql.SQLException;

public class UpdateParentController {

    @FXML
    private Button changeImageButton, updateButton;
    @FXML
    private TextField emailField, fullNameField, phoneField;
    @FXML
    private Hyperlink forgotPasswordLink;
    @FXML
    private Circle profileCircle;

    private ServiceUser serviceUser;
    private User currentUser;

    @FXML
    public void initialize() {
        serviceUser = new ServiceUser(); // ✅ Initialisation correcte ici

        // Récupérer l'utilisateur connecté
        User user = UserSession.getInstance().getUser();
        if (user != null) {
            int userId = user.getId();
            try {
                currentUser = serviceUser.recupererParId(userId);
                System.out.println("Utilisateur connecté : " + currentUser.getNom() + " | ID = " + currentUser.getId());
                loadCurrentUserProfile(); // Charger les infos utilisateur
            } catch (SQLException e) {
                System.err.println("Erreur de récupération de l'utilisateur : " + e.getMessage());
            }
        } else {
            System.err.println("Aucun utilisateur connecté !");
        }
    }

    private void loadCurrentUserProfile() {
        if (currentUser != null) {
            fullNameField.setText(currentUser.getNom());
            emailField.setText(currentUser.getEmail());
            phoneField.setText(currentUser.getPhoneNumber());
            // TODO : Charger l'image de profil si nécessaire
        }
    }

    @FXML
    private void handleUpdateProfile() {
        if (currentUser == null) {
            System.err.println("Impossible de mettre à jour, aucun utilisateur chargé.");
            return;
        }

        // Récupérer les nouvelles données depuis les champs
        String fullName = fullNameField.getText();
        String email = emailField.getText();
        String phoneNumber = phoneField.getText();

        // Mettre à jour les infos de l'utilisateur actuel
        currentUser.setNom(fullName);
        currentUser.setEmail(email);
        currentUser.setPhoneNumber(phoneNumber);

        try {
            serviceUser.modifierprofil(currentUser);
            System.out.println("✅ Profil mis à jour avec succès !");
        } catch (Exception e) {
            System.err.println("❌ Erreur de mise à jour du profil : " + e.getMessage());
        }
    }

    @FXML
    private void handleChangeProfileImage() {
        System.out.println("🖼️ Changer l'image de profil - fonctionnalité à implémenter");
        // TODO : Ajouter un FileChooser pour sélectionner une nouvelle image
    }
}

