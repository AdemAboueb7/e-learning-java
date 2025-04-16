package tn.elearning.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import tn.elearning.entities.User;
import tn.elearning.utils.UserSession;

import java.net.URL;
import java.util.ResourceBundle;

public class MenutestController implements Initializable {

    @FXML
    private Button abonnement;

    @FXML
    private Button users;

    @FXML
    private Button Quiz;

    @FXML
    private Button blog;

    @FXML
    private Button cours;

    @FXML
    private Button evenements;

    @FXML
    private Button paiement;

    @FXML
    private Label utilisateur;



    @FXML
    void GoBlog(ActionEvent event) {

    }

    @FXML
    void GoCours(ActionEvent event) {

    }

    @FXML
    void GoEvenements(ActionEvent event) {

    }

    @FXML
    void GoPaiement(ActionEvent event) {

    }

    @FXML
    void GoQuiz(ActionEvent event) {

    }
    @FXML
    void GoUsers(ActionEvent event) {

    }

    @FXML
    void GoAbonnement(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User user = UserSession.getInstance().getUser();
            if (user != null) {
                utilisateur.setText(user.getNom());
            } else {
                utilisateur.setText("Utilisateur inconnu");
            }
        }

    }

