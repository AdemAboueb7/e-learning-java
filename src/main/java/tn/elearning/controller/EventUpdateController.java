package controllers;

import entities.event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.EventService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class EventUpdateController {

    @FXML
    private TextField eventtitletf;

    @FXML
    private TextField eventdescriptiontf;

    @FXML
    private TextField eventpricetf;

    @FXML
    private ComboBox<String> combo;

    @FXML
    private DatePicker datepicker1;

    @FXML
    private DatePicker datepicker2;

    @FXML
    private Label imgadd;

    private String selectedImagePath;
    private event selectedEvent;

    public void setEventData(event ev) {
        this.selectedEvent = ev;

        eventtitletf.setText(ev.getTitre());
        eventdescriptiontf.setText(ev.getDescription());
        eventpricetf.setText(String.valueOf(ev.getPrix()));
        combo.getItems().addAll("en ligne", "présentiel");
        combo.setValue(ev.getType());

        datepicker1.setValue(LocalDate.parse(ev.getDate_debut())); // assuming format is "yyyy-MM-dd"

        datepicker2.setValue(LocalDate.parse(ev.getDate_debut())); // assuming format is "yyyy-MM-dd"

        imgadd.setText(ev.getImage());
        selectedImagePath = ev.getImage();
    }


    @FXML
    public void updateevent(ActionEvent actionEvent) {
        if (selectedEvent == null) {
            showAlert("Erreur", "Aucun événement sélectionné.");
            return;
        }

        try {
            selectedEvent.setTitre(eventtitletf.getText());
            selectedEvent.setDescription(eventdescriptiontf.getText());
            selectedEvent.setPrix(Integer.parseInt(eventpricetf.getText()));
            selectedEvent.setType(combo.getValue());
            selectedEvent.setDate_debut(java.sql.Date.valueOf(datepicker1.getValue()));
            selectedEvent.setDate_fin(java.sql.Date.valueOf(datepicker2.getValue()));
            selectedEvent.setImage(selectedImagePath);

            EventService service = new EventService();
            if(service.update(selectedEvent)){
                showAlert("Succès", "Événement mis à jour !");
                vieweventlist(null);
            }
            else
                showAlert("Erreur", "Erreur lors de la mise à jour.");



        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erreur", "Erreur lors de la mise à jour.");
        }
    }

    @FXML
    public void chooseImageservice(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            selectedImagePath = selectedFile.getAbsolutePath();
            imgadd.setText(selectedImagePath);
        }
    }

    @FXML
    public void vieweventlist(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EventList.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) eventtitletf.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
