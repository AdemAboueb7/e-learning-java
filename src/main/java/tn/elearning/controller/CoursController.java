package tn.elearning.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableCell;
import javafx.util.Callback;
import javafx.stage.FileChooser;
import tn.elearning.entities.Chapitre;
import tn.elearning.entities.Cours;
import tn.elearning.services.ChapitreDAO;
import tn.elearning.services.CoursDAO;

import java.io.File;
import java.sql.SQLException;

public class CoursController {

    @FXML private TextField coursNameField;
    @FXML private TextArea coursDescriptionField;
    @FXML private TableView<Cours> coursTable;
    @FXML private TableColumn<Cours, String> colNom;
    @FXML private TableColumn<Cours, String> colDescription;
    @FXML private TableColumn<Cours, String> colChapitreNom;
    @FXML private TableColumn<Cours, Void> colDownload;
    @FXML private ComboBox<Chapitre> chapitreComboBox;
    @FXML private Label uploadedFileNameLabel;

    private File selectedFile;
    private final ChapitreDAO chapitreDAO = new ChapitreDAO();
    private final CoursDAO coursDAO = new CoursDAO();
    private final ObservableList<Cours> coursList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        try {
            chapitreComboBox.setItems(FXCollections.observableArrayList(chapitreDAO.getAllChapitres()));
        } catch (Exception e) {
            e.printStackTrace();
            chapitreComboBox.setItems(FXCollections.observableArrayList());
        }

        colNom.setCellValueFactory(new PropertyValueFactory<>("titre"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colChapitreNom.setCellValueFactory(cellData -> {
            try {
                Chapitre chapitre = chapitreDAO.getChapitreById(cellData.getValue().getChapitreId());
                return new SimpleStringProperty(chapitre != null ? chapitre.getNom() : "Inconnu");
            } catch (SQLException e) {
                return new SimpleStringProperty("Erreur");
            }
        });

        colDownload.setCellFactory(getDownloadButtonCellFactory());

        coursTable.setItems(coursList);
        loadCours();
    }

    private Callback<TableColumn<Cours, Void>, TableCell<Cours, Void>> getDownloadButtonCellFactory() {
        return param -> new TableCell<>() {
            private final Button btn = new Button("Télécharger");

            {
                btn.setOnAction(event -> {
                    Cours cours = getTableView().getItems().get(getIndex());
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Save File");
                    fileChooser.setInitialFileName("course_" + cours.getId() + ".pdf");
                    File file = fileChooser.showSaveDialog(null);
                    if (file != null) {
                        try {
                            coursDAO.downloadFile(cours.getId(), file.getAbsolutePath());
                            showAlert("Succès", "Fichier téléchargé avec succès !");
                        } catch (Exception e) {
                            showAlert("Erreur", "Échec du téléchargement : " + e.getMessage());
                        }
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        };
    }

    @FXML
    private void uploadFile() {
        FileChooser fileChooser = new FileChooser();
        selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            uploadedFileNameLabel.setText(selectedFile.getName());
        }
    }

    @FXML
    private void addCours() {
        try {
            if (!validateInputs()) return;

            Chapitre selectedChapitre = chapitreComboBox.getValue();

            Cours cours = new Cours();
            cours.setChapitreId(selectedChapitre.getId());
            cours.setTitre(coursNameField.getText().trim());
            cours.setDescription(coursDescriptionField.getText().trim());
            cours.setUpdatedAt(java.time.LocalDateTime.now().toString());

            int coursId = coursDAO.createCours(cours);
            cours.setId(coursId);

            coursDAO.uploadFile(coursId, selectedFile);

            coursList.add(cours);
            showAlert("Succès", "Cours ajouté avec succès !");

            clearForm();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible d'ajouter le cours : " + e.getMessage());
        }
    }

    @FXML
    private void updateCours() {
        Cours selectedCours = coursTable.getSelectionModel().getSelectedItem();
        if (selectedCours != null) {
            try {
                if (coursNameField.getText().trim().isEmpty() || coursDescriptionField.getText().trim().isEmpty()) {
                    showAlert("Erreur", "Veuillez remplir tous les champs.");
                    return;
                }

                selectedCours.setTitre(coursNameField.getText().trim());
                selectedCours.setDescription(coursDescriptionField.getText().trim());
                coursDAO.updateCours(selectedCours);
                loadCours();
                showAlert("Succès", "Cours mis à jour avec succès !");
            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Erreur", "Échec de la mise à jour.");
            }
        } else {
            showAlert("Erreur", "Veuillez sélectionner un cours à modifier.");
        }
    }

    @FXML
    private void deleteCours() {
        Cours selectedCours = coursTable.getSelectionModel().getSelectedItem();
        if (selectedCours != null) {
            try {
                coursDAO.deleteCours(selectedCours.getId());
                loadCours();
                showAlert("Succès", "Cours supprimé !");
            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Erreur", "Échec de la suppression.");
            }
        } else {
            showAlert("Erreur", "Veuillez sélectionner un cours à supprimer.");
        }
    }

    private void loadCours() {
        try {
            coursList.setAll(coursDAO.getAllCours());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearForm() {
        coursNameField.clear();
        coursDescriptionField.clear();
        chapitreComboBox.setValue(null);
        uploadedFileNameLabel.setText("Aucun fichier sélectionné");
        selectedFile = null;
    }

    private boolean validateInputs() {
        StringBuilder errors = new StringBuilder();

        if (coursNameField.getText().trim().isEmpty()) {
            errors.append("- Le nom du cours est requis.\n");
        }

        if (coursDescriptionField.getText().trim().isEmpty()) {
            errors.append("- La description du cours est requise.\n");
        }

        if (chapitreComboBox.getValue() == null) {
            errors.append("- Veuillez sélectionner un chapitre.\n");
        }

        if (selectedFile == null) {
            errors.append("- Veuillez sélectionner un fichier à uploader.\n");
        }

        if (errors.length() > 0) {
            showAlert("Champs manquants", errors.toString());
            return false;
        }

        return true;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
