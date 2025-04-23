package tn.elearning.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.elearning.entities.User;
import tn.elearning.services.ServiceUser;
import tn.elearning.utils.UserSession;

import java.io.IOException;
import java.sql.SQLException;

public class SigninController {

    @FXML
    private TextField emailclient;

    @FXML
    private TextField mdpclient;

    private final ServiceUser serviceUser = new ServiceUser();

    @FXML
    void signinonclick(ActionEvent event) {
        String email = emailclient.getText();
        String password = mdpclient.getText();

        try {
            User user = serviceUser.findByEmailAndPassword(email, password);
            if (user != null) {
                // Connexion réussie
                UserSession.getInstance().setUser(user);
                System.out.println("Utilisateur connecté : " + user.getNom());

                showAlert("Succès", "Connexion réussie!", AlertType.INFORMATION);

                // Récupérer le rôle depuis l'objet user et le nettoyer
                String roleRaw = user.getRoles().toString();
                System.out.println("Role brut détecté : " + roleRaw);
                
                // Nettoyer le format JSON pour extraire juste le rôle
                String role = roleRaw.replace("[", "").replace("]", "").replace("\"", "");
                System.out.println("Role nettoyé : " + role);

                // Choisir la page à charger selon le rôle
                String fxmlToLoad;
                if (role.equals("ROLE_ADMIN")) {
                    fxmlToLoad = "/VoirArticles.fxml";
                    System.out.println("Tentative de chargement de la vue admin : " + fxmlToLoad);
                } else if (role.equals("ROLE_TEACHER")) {
                    fxmlToLoad = "/VoirArticlesTeacher.fxml";
                    System.out.println("Tentative de chargement de la vue enseignant : " + fxmlToLoad);
                } else if (role.equals("ROLE_Parent")) {
                    fxmlToLoad = "/VoirArticlesParent.fxml";
                    System.out.println("Tentative de chargement de la vue parent : " + fxmlToLoad);
                } else {
                    // Par défaut, rediriger vers la vue parent
                    fxmlToLoad = "/VoirArticlesParent.fxml";
                    System.out.println("Rôle non reconnu, chargement de la vue par défaut : " + fxmlToLoad);
                }

                try {
                    // Charger la page
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlToLoad));
                    if (loader.getLocation() == null) {
                        throw new IOException("Impossible de trouver le fichier FXML: " + fxmlToLoad);
                    }
                    Scene scene = new Scene(loader.load());
                    Stage stage = (Stage) emailclient.getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                    showAlert("Erreur", "Erreur lors du chargement de la page: " + e.getMessage(), AlertType.ERROR);
                }
            } else {
                // Email ou mot de passe incorrect
                showAlert("Erreur", "Email ou mot de passe incorrect", AlertType.ERROR);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erreur", "Une erreur s'est produite lors de la connexion", AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
