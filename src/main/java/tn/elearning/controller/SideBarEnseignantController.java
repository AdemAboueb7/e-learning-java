package tn.elearning.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import tn.elearning.utils.NavigationUtil;

import java.io.IOException;

public class SideBarEnseignantController {

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

    }

    @FXML
    void handleDeconnexionAction(ActionEvent event) {

    }

    @FXML
    void handleEvenementsAction(ActionEvent event) {

    }

    @FXML
    void handleProfilAction(ActionEvent event) {

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

}
