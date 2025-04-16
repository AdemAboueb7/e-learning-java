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
            // Récupérer l'utilisateur depuis la base de données
            User user = serviceUser.findByEmailAndPassword(email, password);
            if (user != null) {
                // Connexion réussie
                System.out.println("Connexion réussie : " + user.getNom());

                // Stocker l'utilisateur dans la session
                UserSession.getInstance().setUser(user);

                // Vérifier si l'utilisateur est correctement stocké dans UserSession
                User sessionUser = UserSession.getInstance().getUser();
                if (sessionUser != null) {
                    System.out.println("Utilisateur connecté : " + sessionUser.getNom());
                } else {
                    System.out.println("Aucun utilisateur n'est connecté !");
                }

                // Afficher une alerte de succès
                showAlert("Succès", "Connexion réussie!", AlertType.INFORMATION);

                // Rediriger vers une nouvelle interface (par exemple Dashboard)
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/path/to/your/Dashboard.fxml"));
                Stage stage = (Stage) emailclient.getScene().getWindow();
                stage.setScene(new Scene(loader.load()));
                stage.show();

            } else {
                // Connexion échouée
                System.out.println("Email ou mot de passe incorrect.");
                showAlert("Erreur", "Email ou mot de passe incorrect", AlertType.ERROR);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erreur", "Une erreur s'est produite lors de la connexion", AlertType.ERROR);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour afficher des alertes
    private void showAlert(String title, String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}


