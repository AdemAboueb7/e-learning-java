package tn.elearning.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.elearning.entities.User;
import tn.elearning.entities.matiereprof;
import tn.elearning.services.ServiceMatiereProf;
import tn.elearning.services.ServiceUser;
import org.mindrot.jbcrypt.BCrypt;  // Importation de BCrypt

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class signupteacher {
    @FXML
    public TextField experience;
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
    private ComboBox<matiereprof> matiereComboBox;

    @FXML
    private TextField numclient;

    @FXML
    void signup(ActionEvent event) {
        // Récupération des valeurs saisies par l'utilisateur
        String email = emailclient.getText().trim();
        String nom = nomclient.getText().trim();
        String mdp = mdpclient.getText().trim();
        String phone = numclient.getText().trim();
        String exp = experience.getText().trim();
        String adresse = adresseclient.getText().trim();


        // Vérification des champs
        if (nom.isEmpty() || email.isEmpty() || mdp.isEmpty() || phone.isEmpty() || adresse.isEmpty() || exp.isEmpty()) {
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
        user.setExperience(Integer.valueOf(exp));
        user.setRoles(Collections.singletonList("ROLE_TEACHER"));
        matiereprof selectedMatiere = matiereComboBox.getValue();
        if (selectedMatiere == null) {
            showAlert("Erreur", "Veuillez sélectionner une matière.");
            return;
        }
        user.setMatiereProf(selectedMatiere);

        // Hachage du mot de passe avec BCrypt avant de le sauvegarder
        String hashedPassword = BCrypt.hashpw(mdp, BCrypt.gensalt(12));  // Hachage du mot de passe
        user.setPassword(hashedPassword);  // Assigner le mot de passe haché à l'utilisateur

        // Appel du service pour ajouter l'utilisateur dans la base de données
        ServiceUser service = new ServiceUser();
        try {
            service.ajouter(user);  // Ajouter l'utilisateur avec le mot de passe haché
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
    @FXML
    public void initialize() {
        try {
            List<matiereprof> matieres = new ServiceMatiereProf().getAll();  // Implémente cette méthode si nécessaire
            matiereComboBox.getItems().addAll(matieres);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible de charger les matières.");
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
