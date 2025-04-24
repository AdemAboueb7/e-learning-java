package tn.elearning.controller;

import tn.elearning.services.ModuleDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.elearning.entities.Chapitre;
import tn.elearning.services.ChapitreDAO;
import tn.elearning.entities.Module;

import java.sql.SQLException;
import java.util.List;

public class ChapitreController {

    @FXML
    private TextField chapitreNameField;

    @FXML
    private TextArea chapitreDescriptionField;

    @FXML
    private ComboBox<Module> moduleComboBox;

    @FXML
    private TableView<Chapitre> chapitreTable;

    @FXML
    private TableColumn<Chapitre, String> colNom;

    @FXML
    private TableColumn<Chapitre, String> colDescription;

    private ObservableList<Chapitre> chapitreList = FXCollections.observableArrayList();
    private ChapitreDAO chapitreDAO = new ChapitreDAO();
    private ModuleDAO moduleDAO = new ModuleDAO();

    @FXML
    public void initialize() {
        colNom.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        colDescription.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        chapitreTable.setItems(chapitreList);

        loadChapitres();
        loadModules();
    }

    private void loadModules() {
        try {
            List<Module> modules = moduleDAO.getAllModules();
            ObservableList<Module> moduleObservableList = FXCollections.observableArrayList(modules);
            moduleComboBox.setItems(moduleObservableList);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur lors du chargement des modules.");
        }
    }

    @FXML
    private void addChapitre() {
        String nom = chapitreNameField.getText().trim();
        String description = chapitreDescriptionField.getText().trim();
        Module selectedModule = moduleComboBox.getSelectionModel().getSelectedItem();

        if (nom.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Nom du chapitre est requis.");
            return;
        }

        if (description.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Description du chapitre est requise.");
            return;
        }

        if (selectedModule == null) {
            showAlert(Alert.AlertType.WARNING, "Veuillez sélectionner un module.");
            return;
        }

        Chapitre chapitre = new Chapitre();
        chapitre.setNom(nom);
        chapitre.setDescription(description);
        chapitre.setModuleId(selectedModule.getId());

        try {
            chapitreDAO.createChapitre(chapitre);
            loadChapitres();
            clearFields();
            showAlert(Alert.AlertType.INFORMATION, "Chapitre ajouté avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur lors de l'ajout du chapitre.");
        }
    }

    @FXML
    private void updateChapitre() {
        Chapitre selectedChapitre = chapitreTable.getSelectionModel().getSelectedItem();

        if (selectedChapitre == null) {
            showAlert(Alert.AlertType.WARNING, "Veuillez sélectionner un chapitre à modifier.");
            return;
        }

        String nom = chapitreNameField.getText().trim();
        String description = chapitreDescriptionField.getText().trim();

        if (nom.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Nom du chapitre est requis.");
            return;
        }

        if (description.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Description du chapitre est requise.");
            return;
        }

        selectedChapitre.setNom(nom);
        selectedChapitre.setDescription(description);

        try {
            chapitreDAO.updateChapitre(selectedChapitre);
            loadChapitres();
            clearFields();
            showAlert(Alert.AlertType.INFORMATION, "Chapitre modifié avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur lors de la modification du chapitre.");
        }
    }

    @FXML
    private void deleteChapitre() {
        Chapitre selectedChapitre = chapitreTable.getSelectionModel().getSelectedItem();

        if (selectedChapitre == null) {
            showAlert(Alert.AlertType.WARNING, "Veuillez sélectionner un chapitre à supprimer.");
            return;
        }

        try {
            chapitreDAO.deleteChapitre(selectedChapitre.getId());
            loadChapitres();
            clearFields();
            showAlert(Alert.AlertType.INFORMATION, "Chapitre supprimé avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur lors de la suppression du chapitre.");
        }
    }

    private void loadChapitres() {
        try {
            chapitreList.clear();
            chapitreList.addAll(chapitreDAO.getAllChapitres());
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur lors du chargement des chapitres.");
        }
    }

    private void clearFields() {
        chapitreNameField.clear();
        chapitreDescriptionField.clear();
        moduleComboBox.getSelectionModel().clearSelection();
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
