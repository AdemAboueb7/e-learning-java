package tn.elearning.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import tn.elearning.utils.NavigationUtil;
import tn.elearning.utils.UserSession;

public class SideBarAdminController {

    @FXML
    private Button articles;
    @FXML
    private Button statistiques;

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
    private Button users;

    @FXML
    void handleArticlesAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/VoirArticles.fxml"));
            Parent root = loader.load();
            paiements.getScene().setRoot(root);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void handleDeconnexionAction(ActionEvent event) {
        UserSession.getInstance().clear();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Signin.fxml"));
            Parent root = loader.load();

            sidebar.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    void handlestatistiquesAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/suiviadmin.fxml"));
            Parent root = loader.load();
            paiements.getScene().setRoot(root);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    @FXML
    void handleEvenementsAction(ActionEvent event) {

    }

    @FXML
    void handlePaiementsAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherAbonnements.fxml"));
            Parent root = loader.load();
            paiements.getScene().setRoot(root);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void handleProfilAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/updateadmin.fxml"));
            Parent root = loader.load();
            paiements.getScene().setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @FXML
    void handleSuiviAction(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DashboardPaiement.fxml"));
            Parent root = loader.load();
            paiements.getScene().setRoot(root);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void handleusersAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/listusers.fxml"));
            Parent root = loader.load();
            paiements.getScene().setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
