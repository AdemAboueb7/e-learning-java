package controllers;

import entities.event;  // Your event entity (note: the class name is "event" per your code)
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import services.EventService;


public class EventListController implements Initializable {

    @FXML
    private ListView<event> eventListView;

    @FXML
    private Button addEventButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventListView.getItems().clear();

        List<event> events = loadEvents();
        eventListView.getItems().addAll(events);

        eventListView.setCellFactory(new Callback<ListView<event>, ListCell<event>>() {
            @Override
            public ListCell<event> call(ListView<event> listView) {
                return new ListCell<event>() {
                    @Override
                    protected void updateItem(event ev, boolean empty) {
                        super.updateItem(ev, empty);
                        if (empty || ev == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            // 📷 Image
                            ImageView imageView = new ImageView();
                            try {
                                File file = new File(ev.getImage());
                                if (file.exists()) {
                                    Image img = new Image(file.toURI().toString());
                                    imageView.setImage(img);
                                    imageView.setFitWidth(100);
                                    imageView.setFitHeight(80);
                                    imageView.setPreserveRatio(true);
                                }
                            } catch (Exception e) {
                                System.out.println("Error loading image: " + e.getMessage());
                            }

                            // 📅 Dates
                            String dateDebutStr = ev.getDate_debut();
                            String dateFinStr = ev.getDate_fin();

                            // 📄 Text info
                            VBox vbox = new VBox(5);
                            Label idLabel = new Label("ID: " + ev.getId());
                            Label titleLabel = new Label("Titre: " + ev.getTitre());
                            Label descriptionLabel = new Label("Description: " + ev.getDescription());
                            Label type = new Label("Type: " + ev.getType());
                            Label priceLabel = new Label("Prix: " + ev.getPrix() + " TND");
                            Label dateDebutLabel = new Label("Début: " + dateDebutStr);
                            Label dateFinLabel = new Label("Fin: " + dateFinStr);
                            vbox.getChildren().addAll(idLabel, titleLabel, descriptionLabel, type, priceLabel, dateDebutLabel, dateFinLabel);

                            // 🔘 Buttons
                            Button updateButton = new Button("Modifier");
                            Button deleteButton = new Button("Supprimer");

                            updateButton.setOnAction(e -> {
                                System.out.println("Update clicked for event ID: " + ev.getId());
                                openUpdatePage(ev);
                            });

                            deleteButton.setOnAction(e -> {
                                System.out.println("Delete clicked for event ID: " + ev.getId());
                                deleteEvent(ev);
                            });

                            VBox buttonBox = new VBox(5, updateButton, deleteButton);
                            buttonBox.setAlignment(Pos.CENTER_RIGHT);

                            // 🧱 HBox layout
                            HBox hbox = new HBox(20, imageView, vbox, buttonBox);
                            hbox.setStyle("-fx-padding: 10; -fx-border-color: #ccc; -fx-border-width: 1; -fx-border-radius: 5;");
                            hbox.setAlignment(Pos.CENTER_LEFT);
                            HBox.setHgrow(vbox, Priority.ALWAYS);

                            setGraphic(hbox);
                        }
                    }

                };
            }
        });
    }


    /**
     * Loads sample event data.
     * Replace this method with your actual data-loading logic.
     */
    private List<event> loadEvents() {

        EventService eventService = new EventService();
        try {
            List<event> ev = eventService.getAll();
            System.out.println(ev);
            return eventService.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>(); // return empty list on error
        }
    }

    @FXML
    public void addEvent(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EventCrud.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteEvent(event ev) {
        EventService service = new EventService();
        try {
            service.delete(ev.getId()); // assumes you have a delete(event e) method in EventService
            eventListView.getItems().remove(ev); // update UI
            System.out.println("Event with ID " + ev.getId() + " has been deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
            // Optionally show alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Suppression échouée");
            alert.setContentText("Impossible de supprimer l'événement.");
            alert.showAndWait();
        }
    }

    public void openUpdatePage(event ev) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EventUpdate.fxml")); // ✅ SET location
            Parent root = loader.load();


            EventUpdateController controller = loader.getController();
            controller.setEventData(ev);

            Stage stage = (Stage) eventListView.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}