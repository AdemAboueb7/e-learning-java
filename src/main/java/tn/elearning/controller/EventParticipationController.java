package controllers;

import entities.event;
import entities.participation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import services.EventService;
import services.ParticipationService;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class EventParticipationController implements Initializable {

    @FXML
    private ListView<event> eventListView;

    private final int userId = 1; // 🔒 À remplacer plus tard avec l'utilisateur connecté

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EventService eventService = new EventService();
        ParticipationService participationService = new ParticipationService();

        try {
            List<event> events = eventService.getAll();
            eventListView.getItems().addAll(events);

            eventListView.setCellFactory(listView -> new ListCell<>() {
                @Override
                protected void updateItem(event ev, boolean empty) {
                    super.updateItem(ev, empty);
                    if (empty || ev == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        // 🖼️ Image
                        ImageView imageView = new ImageView();
                        File file = new File(ev.getImage());
                        if (file.exists()) {
                            Image img = new Image(file.toURI().toString());
                            imageView.setImage(img);
                            imageView.setFitWidth(100);
                            imageView.setFitHeight(80);
                            imageView.setPreserveRatio(true);
                        }

                        // 📄 Infos événement
                        VBox vbox = new VBox(5,
                                new Label("Titre: " + ev.getTitre()),
                                new Label("Type: " + ev.getType()),
                                new Label("Prix: " + ev.getPrix() + " TND"),
                                new Label("Début: " + ev.getDate_debut()),
                                new Label("Fin: " + ev.getDate_fin())
                        );

                        // ✅ Bouton Participer
                        Button btnParticiper = new Button("Participer");

                        btnParticiper.setOnAction(e -> {
                            try {
                                if (!participationService.hasParticipated(ev.getId(), userId)) {
                                    participation p = new participation(ev.getId(), userId, new Date());
                                    participationService.addParticipation(p);

                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Participation enregistrée");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Vous avez participé à l'événement !");
                                    alert.showAndWait();
                                } else {
                                    Alert alert = new Alert(Alert.AlertType.WARNING);
                                    alert.setTitle("Déjà participé");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Vous avez déjà participé à cet événement.");
                                    alert.showAndWait();
                                }
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Erreur");
                                alert.setHeaderText("Erreur lors de la participation");
                                alert.setContentText("Veuillez réessayer.");
                                alert.showAndWait();
                            }
                        });

                        VBox buttonBox = new VBox(btnParticiper);
                        buttonBox.setAlignment(Pos.CENTER);

                        HBox hbox = new HBox(20, imageView, vbox, buttonBox);
                        hbox.setStyle("-fx-padding: 10; -fx-border-color: #ccc; -fx-border-width: 1;");
                        hbox.setAlignment(Pos.CENTER_LEFT);
                        HBox.setHgrow(vbox, Priority.ALWAYS);

                        setGraphic(hbox);
                    }
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur lors de la participation");
            alert.setContentText("Veuillez réessayer.");
            alert.showAndWait();
        }
    }
}
