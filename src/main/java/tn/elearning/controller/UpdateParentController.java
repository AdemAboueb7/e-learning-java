package tn.elearning.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import tn.elearning.entities.User;
import tn.elearning.services.ServiceUser;
import tn.elearning.utils.UserSession;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Color;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
            if (currentUser.getImageContent() != null && currentUser.getImageContent().length > 0) {
                // Convertir l'image en Image et l'afficher dans le cercle
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(currentUser.getImageContent());
                Image profileImage = new Image(byteArrayInputStream);
                profileCircle.setFill(new ImagePattern(profileImage));
            } else {
                // Si aucune image, afficher une couleur ou une image par défaut
                profileCircle.setFill(Color.LIGHTGRAY); // Ou une image par défaut
            }
        }
    }

    @FXML
    private void handleUpdateProfile() {
        if (currentUser == null) {
            System.err.println("Impossible de mettre à jour, aucun utilisateur chargé.");
            return;
        }

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
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image de profil");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", ".png", ".jpg", ".jpeg", ".gif")
        );
        File file = fileChooser.showOpenDialog(profileCircle.getScene().getWindow());

        if (file != null) {
            try {
                // Lire le fichier image
                byte[] imageBytes = Files.readAllBytes(file.toPath());

                // Afficher l'image dans le cercle
                Image image = new Image(new ByteArrayInputStream(imageBytes));
                profileCircle.setFill(new ImagePattern(image));

                // Mettre à jour dans l'objet currentUser
                currentUser.setImageName(file.getName());
                currentUser.setImageContent(imageBytes);

                // Mise à jour dans la base de données
                serviceUser.updateUserImage(currentUser.getId(), currentUser.getImageName(), currentUser.getImageContent());


                System.out.println("✅ Image de profil mise à jour !");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
