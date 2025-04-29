package tn.elearning.controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
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
                        VBox vbox = new VBox(5);
                        Label chapitreLabel = new Label(chapitre.getNom());
                        chapitreLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

                        vbox.getChildren().add(chapitreLabel);

                        // Fetch courses for the current chapter
                        ObservableList<Cours> courses = FXCollections.observableArrayList();
                        try {
                            courses.addAll(coursDAO.getCoursByChapitre(chapitre.getId()));
                        } catch (SQLException e) {
                            e.printStackTrace();
                            showAlert("Erreur", "Erreur lors de la récupération des cours : " + e.getMessage());
                        }

                        // Loop through courses and create UI components for each
                        for (Cours course : courses) {
                            HBox courseBox = new HBox(10);
                            courseBox.setAlignment(Pos.CENTER_LEFT);

                            Label courseLabel = new Label(course.getTitre() + " - " + course.getDescription());
                            Button downloadButton = new Button("Télécharger");

                            // Download action
                            downloadButton.setOnAction(event -> downloadCourse(course));
                            final Label averageRatingLabel = new Label("Avg Rating: " + String.format("%.1f", course.getAverageRating()) + " ★");
                            // Create a HBox to hold stars
                            HBox starsBox = new HBox(2);
                            starsBox.setAlignment(Pos.CENTER_LEFT);

                            // Create 5 stars
                            for (int i = 1; i <= 5; i++) {
                                Button star = new Button("☆"); // Empty star
                                star.getStyleClass().add("star-button");

                                final int starValue = i;
                                star.setOnAction(event -> {
                                    updateStars(starsBox, starValue);

                                    // Use the new addRating method
                                    course.addRating(starValue);

                                    // Update the average rating display immediately
                                    averageRatingLabel.setText("Avg Rating: " + String.format("%.1f", course.getAverageRating()) + " ★");

                                    // Update the database
                                    try {
                                        coursDAO.updateCourseRating(course);
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                        showAlert("Erreur", "Erreur lors de la mise à jour de la note : " + e.getMessage());
                                    }
                                });

                                starsBox.getChildren().add(star);
                            }

                            // Display the average rating
                            courseBox.getChildren().addAll(courseLabel, starsBox, averageRatingLabel, downloadButton);
                            vbox.getChildren().add(courseBox);
                        }

                        setGraphic(vbox);
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Erreur lors de l'initialisation : " + e.getMessage());
        }
    }

    private void downloadCourse(Cours course) {
        byte[] contenuFichierBytes = course.getContenuFichier();
        if (contenuFichierBytes != null) {
            try {
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
                fileChooser.setTitle("Save File");
                fileChooser.setInitialFileName(course.getTitre() + ".pdf");

                File fileToSave = fileChooser.showSaveDialog(null);
                if (fileToSave != null) {
                    try (FileOutputStream fileOutputStream = new FileOutputStream(fileToSave)) {
                        fileOutputStream.write(contenuFichierBytes);
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

    private void updateStars(HBox starsBox, int rating) {
        for (int i = 0; i < starsBox.getChildren().size(); i++) {
            Button star = (Button) starsBox.getChildren().get(i);
            star.getStyleClass().removeAll("filled"); // remove existing "filled"

            if (i < rating) {
                if (!star.getStyleClass().contains("filled")) {
                    star.getStyleClass().add("filled");
                }
            }
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
