package controllers;

import entities.event;
import services.EventService;
import services.ParticipationService;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class ListParticipationController implements Initializable {

    @FXML
    private ListView<event> participationListView;

    private final int userId = 1; // 🔒 À remplacer plus tard

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EventService eventService = new EventService();
        ParticipationService participationService = new ParticipationService();

        try {
            List<Integer> participatedEventIds = participationService.getParticipatedEventIdsByUser(userId);
            List<event> participatedEvents = new ArrayList<>();

            for (int eventId : participatedEventIds) {
                event ev = eventService.getEventById(eventId);
                if (ev != null) {
                    participatedEvents.add(ev);
                }
            }

            participationListView.getItems().addAll(participatedEvents);

            participationListView.setCellFactory(listView -> new ListCell<>() {
                @Override
                protected void updateItem(event ev, boolean empty) {
                    super.updateItem(ev, empty);
                    if (empty || ev == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        ImageView imageView = new ImageView();
                        File file = new File(ev.getImage());
                        if (file.exists()) {
                            Image img = new Image(file.toURI().toString());
                            imageView.setImage(img);
                            imageView.setFitWidth(100);
                            imageView.setFitHeight(80);
                            imageView.setPreserveRatio(true);
                        }

                        VBox vbox = new VBox(5,
                                new Label("Titre: " + ev.getTitre()),
                                new Label("Type: " + ev.getType()),
                                new Label("Début: " + ev.getDate_debut()),
                                new Label("Fin: " + ev.getDate_fin())
                        );

                        Button cancelBtn = new Button("Annuler Participation");
                        cancelBtn.setOnAction(e -> {
                            try {
                                participationService.cancelParticipation(ev.getId(), userId);
                                participationListView.getItems().remove(ev);

                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Participation annulée");
                                alert.setHeaderText(null);
                                alert.setContentText("Vous vous êtes retiré de l'événement.");
                                alert.showAndWait();
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                        });

                        VBox btnBox = new VBox(cancelBtn);
                        btnBox.setAlignment(Pos.CENTER);

                        HBox hbox = new HBox(20, imageView, vbox, btnBox);
                        hbox.setStyle("-fx-padding: 10; -fx-border-color: #ccc; -fx-border-width: 1;");
                        hbox.setAlignment(Pos.CENTER_LEFT);
                        HBox.setHgrow(vbox, Priority.ALWAYS);

                        setGraphic(hbox);
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
