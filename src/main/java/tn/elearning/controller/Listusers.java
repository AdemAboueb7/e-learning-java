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
    @FXML
    private TableColumn<User, Boolean> activeColumn;


    private final ServiceUser serviceUser = new ServiceUser();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        activeColumn.setCellFactory(col -> {
            return new javafx.scene.control.TableCell<User, Boolean>() {
                private final javafx.scene.control.ToggleButton toggleButton = new javafx.scene.control.ToggleButton();

                {
                    toggleButton.setOnAction(event -> {
                        User user = getTableView().getItems().get(getIndex());
                        boolean newStatus = !user.isActive();
                        user.setActive(newStatus);
                        toggleButton.setText(newStatus ? "Activé" : "Désactivé");

                        // 🔥 Mettre à jour dans la base de données
                        try {
                            serviceUser.updateUserStatus(user.getId(), newStatus);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    });
                }

                @Override
                protected void updateItem(Boolean active, boolean empty) {
                    super.updateItem(active, empty);

                    if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                        setGraphic(null);
                    } else {
                        User user = (User) getTableRow().getItem();
                        if (user != null) {
                            toggleButton.setText(user.isActive() ? "Activé" : "Désactivé");
                            toggleButton.setSelected(user.isActive());
                            setGraphic(toggleButton);
                        }
                    }
                }
            };
        });

// Configurer la colonne active
        activeColumn.setCellValueFactory(new PropertyValueFactory<>("active"));

        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        telColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("roles"));


        try {
            ObservableList<User> users = FXCollections.observableArrayList(serviceUser.getAllUsers());
            tableid.setItems(users);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

