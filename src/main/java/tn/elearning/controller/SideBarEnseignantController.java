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

public class SideBarEnseignantController {

    public Button coursBtn;
    public Button moduleBtn;
    public Button chapitreBtn;
    public Button dashboardBtn;
    @FXML
    private Button articles;

    @FXML
    private Button evenements;

    @FXML
    private Button profil;

    @FXML
    private VBox sidebar;

    @FXML
    private Button cours;

    @FXML
    void handleArticlesAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/VoirArticlesTeacher.fxml"));
            Parent root = loader.load();
            profil.getScene().setRoot(root);

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
    void handleProfilAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/updateenseignant.fxml"));
            Parent root = loader.load();
            profil.getScene().setRoot(root);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    void handleusersAction(ActionEvent event) {

    }
    @FXML
    private void handleCoursAction(ActionEvent event) {
        try {
            NavigationUtil.navigateToCours();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleModuleAction(ActionEvent event) {
        try {
            NavigationUtil.navigateToModule();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleChapitreAction(ActionEvent event) {
        try {
            NavigationUtil.navigateToChapitre();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleDashboardAction(ActionEvent event) {
        try {
            NavigationUtil.navigateToDashboard();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

}