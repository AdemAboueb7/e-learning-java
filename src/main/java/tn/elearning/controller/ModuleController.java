package tn.elearning.controller;

import tn.elearning.entities.Module;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.elearning.services.ModuleDAO;

import java.sql.SQLException;

public class ModuleController {

    @FXML
    private TextField moduleNameField;
    @FXML
    private TextArea moduleDescriptionField;
    @FXML
    private TableView<Module> moduleTable;
    @FXML
    private TableColumn<Module, String> colNom;
    @FXML
    private TableColumn<Module, String> colDescription;

    private ObservableList<Module> moduleList = FXCollections.observableArrayList();
    private ModuleDAO moduleDAO = new ModuleDAO(); // Instantiate your DAO

    // Initialize method to load data
    @FXML
    public void initialize() {
        colNom.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        colDescription.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        moduleTable.setItems(moduleList);
        loadModules(); // Load modules from the database when the view is initialized
    }

    // Add Module
    @FXML
    private void addModule() {
        if (!validateInputs()) return; // Validate inputs

        String nom = moduleNameField.getText();
        String description = moduleDescriptionField.getText();
        Module module = new Module();
        module.setNom(nom);
        module.setDescription(description);

        try {
            moduleDAO.createModule(module); // Call createModule method from DAO
            loadModules(); // Reload the table data
            clearForm(); // Clear form after adding module
            showAlert("Succès", "Module ajouté avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible d'ajouter le module : " + e.getMessage());
        }
    }

    // Update Module
    @FXML
    private void updateModule() {
        Module selectedModule = moduleTable.getSelectionModel().getSelectedItem();
        if (selectedModule != null) {
            if (moduleNameField.getText().trim().isEmpty() || moduleDescriptionField.getText().trim().isEmpty()) {
                showAlert("Erreur", "Veuillez remplir tous les champs.");
                return;
            }

            selectedModule.setNom(moduleNameField.getText());
            selectedModule.setDescription(moduleDescriptionField.getText());

            try {
                moduleDAO.updateModule(selectedModule); // Call updateModule method from DAO
                loadModules(); // Reload the table data
                showAlert("Succès", "Module mis à jour avec succès !");
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Erreur", "Échec de la mise à jour.");
            }
        }
    }

    // Delete Module
    @FXML
    private void deleteModule() {
        Module selectedModule = moduleTable.getSelectionModel().getSelectedItem();
        if (selectedModule != null) {
            try {
                moduleDAO.deleteModule(selectedModule.getId()); // Call deleteModule method from DAO
                loadModules(); // Reload the table data
                showAlert("Succès", "Module supprimé !");
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Erreur", "Échec de la suppression.");
            }
        } else {
            showAlert("Erreur", "Veuillez sélectionner un module à supprimer.");
        }
    }

    // Load all modules into the TableView
    private void loadModules() {
        try {
            moduleList.clear(); // Clear existing items
            moduleList.addAll(moduleDAO.getAllModules()); // Fetch modules from the database
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Input validation method
    private boolean validateInputs() {
        StringBuilder errors = new StringBuilder();

        if (moduleNameField.getText().trim().isEmpty()) {
            errors.append("- Le nom du module est requis.\n");
        }

        if (moduleDescriptionField.getText().trim().isEmpty()) {
            errors.append("- La description du module est requise.\n");
        }

        if (errors.length() > 0) {
            showAlert("Champs manquants", errors.toString());
            return false;
        }

        return true;
    }

    // Method to show alert messages
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Clear the form fields
    private void clearForm() {
        moduleNameField.clear();
        moduleDescriptionField.clear();
    }
}
