package tn.elearning.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.elearning.entities.User;
import tn.elearning.services.ServiceUser;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.SQLException;

public class Listusers implements Initializable {

    @FXML
    private TableView<User> tableid;

    @FXML
    private TableColumn<User, String> nomColumn;

    @FXML
    private TableColumn<User, String> emailColumn;

    @FXML
    private TableColumn<User, String> telColumn;

    @FXML
    private TableColumn<User, String> roleColumn;

    private final ServiceUser serviceUser = new ServiceUser();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Configurer les colonnes
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        telColumn.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        // Charger les utilisateurs
        try {
            ObservableList<User> users = FXCollections.observableArrayList(serviceUser.getAllUsers());
            tableid.setItems(users);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

