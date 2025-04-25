package tn.elearning.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import tn.elearning.entities.Cours;
import tn.elearning.entities.Chapitre;
import tn.elearning.services.CoursDAO;
import tn.elearning.services.ChapitreDAO;

import java.io.*;
import java.sql.SQLException;

public class ListeDesCoursController {

    @FXML private ListView<Chapitre> chapitreListView;

    private final CoursDAO coursDAO = new CoursDAO();
    private final ChapitreDAO chapitreDAO = new ChapitreDAO();

    @FXML
    public void initialize() {
        try {
            // Fetch all chapters from the database
            ObservableList<Chapitre> chapitres = FXCollections.observableArrayList(chapitreDAO.getAllChapitres());
            chapitreListView.setItems(chapitres);

            // Set the cell factory for the ListView to display chapters and courses
            chapitreListView.setCellFactory(param -> new ListCell<Chapitre>() {
                @Override
                protected void updateItem(Chapitre chapitre, boolean empty) {
                    super.updateItem(chapitre, empty);

                    if (empty || chapitre == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        VBox vbox = new VBox(5); // Create a VBox to display chapter and courses
                        Label chapitreLabel = new Label(chapitre.getNom()); // Chapter name label
                        chapitreLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;"); // Style the chapter label
                        vbox.getChildren().add(chapitreLabel); // Add chapter label to VBox

                        // Fetch courses for the current chapter
                        ObservableList<Cours> courses = FXCollections.observableArrayList();
                        try {
                            // Fetch the courses for this chapter from the DAO
                            courses.addAll(coursDAO.getCoursByChapitre(chapitre.getId()));
                        } catch (SQLException e) {
                            e.printStackTrace();
                            showAlert("Erreur", "Une erreur est survenue lors de la récupération des cours : " + e.getMessage());
                        }

                        // Loop through courses and create UI components for each
                        for (Cours course : courses) {
                            HBox courseBox = new HBox(10); // Create an HBox for each course
                            Label courseLabel = new Label(course.getTitre() + " - " + course.getDescription()); // Course label
                            Button downloadButton = new Button("Télécharger"); // Download button for each course

                            // Set the download button action to download the course
                            downloadButton.setOnAction(event -> downloadCourse(course));

                            // Add course label and download button to HBox
                            courseBox.getChildren().addAll(courseLabel, downloadButton);
                            vbox.getChildren().add(courseBox); // Add HBox to VBox
                        }

                        setGraphic(vbox); // Set the VBox containing chapter and courses as the cell graphic
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Une erreur est survenue : " + e.getMessage());
        }
    }

    private void downloadCourse(Cours course) {
        // This method will handle the downloading of the course file
        byte[] contenuFichierBytes = course.getContenuFichier(); // Get the byte array from the course object
        if (contenuFichierBytes != null) {
            try {
                // Create a FileChooser to let the user select the download location
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
                fileChooser.setTitle("Save File");
                fileChooser.setInitialFileName(course.getTitre() + ".pdf");

                File fileToSave = fileChooser.showSaveDialog(null);
                if (fileToSave != null) {
                    try (FileOutputStream fileOutputStream = new FileOutputStream(fileToSave)) {
                        fileOutputStream.write(contenuFichierBytes); // Write byte array to the file
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Erreur", "Une erreur est survenue lors du téléchargement : " + e.getMessage());
            }
        } else {
            showAlert("Erreur", "Le fichier du cours n'est pas disponible.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
