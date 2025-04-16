package tn.elearning.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import tn.elearning.entities.User;
import tn.elearning.services.ServiceUser;

import java.sql.SQLException;
import java.util.Collections;

public class SignupController {

    @FXML
    private TextField adresseclient;

    @FXML
    private TextField emailclient;

    @FXML
    private TextField mdpclient;

    @FXML
    private TextField nomclient;

    @FXML
    private TextField numclient;

    @FXML
    private TextField professionclient;

    @FXML
    void signup(ActionEvent event) {
        User user=new User();
        user.setEmail(emailclient.getText());
        user.setNom(nomclient.getText());
        user.setPhoneNumber(numclient.getText());
        user.setPassword(mdpclient.getText());
        user.setAddress(adresseclient.getText());
        user.setWork(professionclient.getText());
        user.setActive(true);
        user.setRoles(Collections.singletonList("ROLE_PARENT"));
        ServiceUser service = new ServiceUser();
        try {
            service.ajouter(user);
            System.out.println("Utilisateur ajouté avec succès avec le rôle PARENT !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

}

