package tn.elearning.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/VoirArticlesTeacher.fxml"));
            Parent root = loader.load();
            cours.getScene().setRoot(root);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

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

}
