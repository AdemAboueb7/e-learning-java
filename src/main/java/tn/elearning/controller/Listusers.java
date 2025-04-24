package tn.elearning.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import tn.elearning.entities.User;
import tn.elearning.services.ServiceUser;

import java.net.URL;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import java.sql.SQLException;

public class Listusers implements Initializable {

    @FXML private TableView<User> tableid;
    @FXML private TableColumn<User, String> nomColumn;
    @FXML private TableColumn<User, String> emailColumn;
    @FXML private TableColumn<User, String> telColumn;
    @FXML private TableColumn<User, String> roleColumn;
    @FXML private TableColumn<User, Void> statusColumn;
    @FXML private TextField searchField;
    @FXML private ComboBox<String> roleFilterComboBox;

    private final ServiceUser serviceUser = new ServiceUser();
    private ObservableList<User> masterData;
    private FilteredList<User> filteredData;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configureColumns();
        loadUsers();
        setupRoleFilter();
    }

    private void configureColumns() {
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        telColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        roleColumn.setCellValueFactory(cellData -> {
            User user = cellData.getValue();
            return new SimpleStringProperty(
                    user.getRoles() != null && !user.getRoles().isEmpty()
                            ? String.join(", ", user.getRoles())
                            : "Aucun rôle"
            );
        });

        statusColumn.setCellFactory(column -> new TableCell<User, Void>() {
            private final RadioButton activeBtn = new RadioButton("Actif");
            private final RadioButton inactiveBtn = new RadioButton("Inactif");
            private final ToggleGroup toggleGroup = new ToggleGroup();
            private final HBox hbox = new HBox(10, activeBtn, inactiveBtn);
            private User currentUser;

            {
                hbox.setAlignment(Pos.CENTER);
                activeBtn.setToggleGroup(toggleGroup);
                inactiveBtn.setToggleGroup(toggleGroup);

                activeBtn.setOnAction(event -> {
                    if (currentUser != null) {
                        currentUser.setActive(true);
                        updateUserStatus(currentUser);
                    }
                });

                inactiveBtn.setOnAction(event -> {
                    if (currentUser != null) {
                        currentUser.setActive(false);
                        updateUserStatus(currentUser);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                    currentUser = null;
                } else {
                    currentUser = getTableRow().getItem();
                    if (currentUser.isActive()) {
                        activeBtn.setSelected(true);
                    } else {
                        inactiveBtn.setSelected(true);
                    }
                    setGraphic(hbox);
                }
            }
        });
    }

    private void loadUsers() {
        try {
            masterData = FXCollections.observableArrayList(serviceUser.getAllUsers());
            filteredData = new FilteredList<>(masterData, p -> true);
            tableid.setItems(filteredData);
        } catch (SQLException e) {
            showErrorAlert("Erreur de chargement", e.getMessage());
        }
    }

    private void setupRoleFilter() {
        // Seulement les rôles spécifiques que nous voulons
        List<String> allowedRoles = Arrays.asList(
                "Tous les rôles",
                "ROLE_ADMIN",
                "ROLE_Parent",
                "ROLE_TEACHER"
        );

        roleFilterComboBox.setItems(FXCollections.observableArrayList(allowedRoles));
        roleFilterComboBox.getSelectionModel().selectFirst();

        // Filtrage combiné recherche texte + rôle
        searchField.textProperty().addListener((obs, oldVal, newVal) -> applyCombinedFilters());
        roleFilterComboBox.valueProperty().addListener((obs, oldVal, newVal) -> applyCombinedFilters());
    }

    private void applyCombinedFilters() {
        String searchText = searchField.getText().toLowerCase();
        String selectedRole = roleFilterComboBox.getValue();

        filteredData.setPredicate(user -> {
            // Filtre par rôle
            boolean roleMatches = selectedRole == null ||
                    "Tous les rôles".equals(selectedRole) ||
                    (user.getRoles() != null && user.getRoles().contains(selectedRole));

            // Filtre par texte
            boolean textMatches = searchText.isEmpty() ||
                    (user.getNom() != null && user.getNom().toLowerCase().contains(searchText)) ||
                    (user.getEmail() != null && user.getEmail().toLowerCase().contains(searchText));

            return roleMatches && textMatches;
        });
    }

    private void updateUserStatus(User user) {
        try {
            serviceUser.updateUserStatus(user.getId(), user.isActive());
        } catch (SQLException e) {
            showErrorAlert("Erreur de mise à jour", e.getMessage());
            refreshTable();
        }
    }

    private void refreshTable() {
        try {
            masterData.setAll(serviceUser.getAllUsers());
            applyCombinedFilters(); // Réappliquer les filtres après rafraîchissement
        } catch (SQLException e) {
            showErrorAlert("Erreur de rafraîchissement", e.getMessage());
        }
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}