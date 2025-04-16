package tn.elearning.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import tn.elearning.entities.Abonnement;
import tn.elearning.services.ServiceAbonnement;

import java.io.IOException;
import java.sql.SQLException;

public class AjouterAbonnementController {
    final ServiceAbonnement sa = new ServiceAbonnement();
    @FXML
    private Button ajout;

    @FXML
    private TextArea descabonnement;

    @FXML
    private TextField dureeabonnement;

    @FXML
    private TextField prixabonnement;

    @FXML
    private TextField typeabonnement;

    @FXML
    void ajouter(ActionEvent event) {
        if (!isValid()) {
            return; // Stoppe si les champs sont invalides
        }
        try {
            if (sa.existeAbonnement(typeabonnement.getText(), Double.parseDouble(prixabonnement.getText()), descabonnement.getText(), dureeabonnement.getText())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Duplication");
                alert.setHeaderText(null);
                alert.setContentText("Cet abonnement existe déjà !");
                alert.showAndWait();
                return;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            sa.ajouter(new Abonnement(typeabonnement.getText(),Double.parseDouble(prixabonnement.getText()),descabonnement.getText(),dureeabonnement.getText()));
            System.out.println("avec succes");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherAbonnements.fxml"));
            Parent root = loader.load();
            descabonnement.getScene().setRoot(root);
        } catch (SQLException | IOException e) {
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }

    }
    private boolean isValid() {
        if (typeabonnement.getText().trim().isEmpty()
                || prixabonnement.getText().trim().isEmpty()
                || descabonnement.getText().trim().isEmpty()
                || dureeabonnement.getText().trim().isEmpty()) {

            showAlert("Champ manquant", "Veuillez remplir tous les champs.");
            return false;
        }


        try {
            double prix=Double.parseDouble(prixabonnement.getText());
            if (prix < 0) {
                showAlert("Prix invalide", "Le prix ne peut pas être négatif.");
                return false;

        }} catch (NumberFormatException e) {
            showAlert("Prix invalide", "Le prix doit être un nombre valide.");
            return false;
        }

        return true;
    }
    private void showAlert(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }




}
