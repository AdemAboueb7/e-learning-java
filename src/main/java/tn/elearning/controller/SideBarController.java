package tn.elearning.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import tn.elearning.utils.NavigationUtil;
import tn.elearning.utils.UserSession;

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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/VoirArticlesParent.fxml"));
            Parent root = loader.load();
            articles.getScene().setRoot(root);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }



    @FXML
    void handleDeconnexionAction(ActionEvent event) {
        UserSession.getInstance().clear();  // Cette méthode efface l'utilisateur et l'ID de session

        // Rediriger vers la page de connexion (Signin.fxml)
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Signin.fxml"));
            Parent root = loader.load();
            // Mettre à jour la scène actuelle pour afficher la page de connexion
            sidebar.getScene().setRoot(root);  // "sidebar" est utilisé ici pour obtenir la scène actuelle
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void handleEvenementsAction(ActionEvent event) {

    }
    @FXML
    private void handleListeCoursAction(ActionEvent event) {
        try {
            // Navigate to the ListeDesCours screen
            NavigationUtil.navigateToListeCours(); // Assuming you have this method defined in the NavigationUtil
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateParent.fxml"));
            Parent root = loader.load();
            cours.getScene().setRoot(root);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }


    @FXML
    private void handleCoursAction(ActionEvent event) {
        try {
            NavigationUtil.navigateToCours();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
