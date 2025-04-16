package tn.elearning.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import tn.elearning.entities.User;
import tn.elearning.services.ServiceUser;

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

        ServiceUser serviceUser = new ServiceUser();
        try {
            User user = serviceUser.findByEmailAndPassword(email, password);
            if (user != null) {
                System.out.println("Connexion réussie : " + user.getNom());
                // TODO : Rediriger vers une autre interface
            } else {
                System.out.println("Email ou mot de passe incorrect.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
