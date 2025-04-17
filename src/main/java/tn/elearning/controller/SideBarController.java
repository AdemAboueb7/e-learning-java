package tn.elearning.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class SideBarController {

    @FXML
    private Button articles;

    @FXML
    private Button cours;

    @FXML
    private Button evenements;

    @FXML
    private Button paiements;

    @FXML
    private Button profil;

    @FXML
    private VBox sidebar;

    @FXML
    private Button suivi;

    @FXML
    void handleArticlesAction(ActionEvent event) {

    }

    @FXML
    void handleCoursAction(ActionEvent event) {

    }

    @FXML
    void handleDeconnexionAction(ActionEvent event) {

    }

    @FXML
    void handleEvenementsAction(ActionEvent event) {

    }

    @FXML
    void handlePaiementsAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cardAbonnements.fxml"));
            Parent root = loader.load();
            cours.getScene().setRoot(root);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    void handleProfilAction(ActionEvent event) {

    }

    @FXML
    void handleSuiviAction(ActionEvent event) {

    }

}
