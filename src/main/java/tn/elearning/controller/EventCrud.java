package controllers;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import entities.event;
import services.EventService;

public class EventCrud {

    // Use a typed ComboBox for event type
    @FXML
    private ComboBox<String> combo;

    @FXML
    private DatePicker datepicker1;

    @FXML
    private DatePicker datepicker2;

    @FXML
    private TextField eventdescriptiontf;

    @FXML
    private TextField eventpricetf;

    @FXML
    private TextField eventtitletf;

    @FXML
    private Label imgadd;

    // Variable to store the selected image path
    private String selectedeventImagePath = "";

    /**
     * This method is called when the "Add Event" button is clicked.
     * It validates the input, creates a new event, adds it via the EventService,
     * clears the form, and then navigates to the Event List view (EventList.fxml).
     */
    @FXML
    void addevent(ActionEvent actionEvent) {
        try {
            // Retrieve and trim input values
            String title = eventtitletf.getText().trim();
            String description = eventdescriptiontf.getText().trim();
            String priceText = eventpricetf.getText().trim();
            String type = combo.getValue();  // Get the selected type from the ComboBox

            // Retrieve the selected dates
            LocalDate startDate = datepicker1.getValue();
            LocalDate endDate = datepicker2.getValue();

            // Validate that both dates have been selected
            if (startDate == null || endDate == null) {
                showAlert("Validation Error", "Please select both Start Date and End Date.");
                return;
            }

            // Validate type selection
            if (type == null || type.isEmpty()) {
                showAlert("Validation Error", "Please choose a type.");
                return;
            }

            // Validate that title, description, and image path are provided
            if (title.isEmpty() || description.isEmpty() || selectedeventImagePath.isEmpty()) {
                showAlert("Validation Error", "Please fill in all required fields, including selecting an image.");
                return;
            }

            // Validate the price field
            int price;
            try {
                price = Integer.parseInt(priceText);
                if (price <= 0) {
                    showAlert("Validation Error", "Price must be a positive number.");
                    return;
                }
            } catch (NumberFormatException e) {
                showAlert("Validation Error", "Invalid price. Please enter a numeric value.");
                return;
            }

            // Create a new event object and set its attributes
            event newEvent = new event();
            newEvent.setTitre(title);
            newEvent.setDescription(description);
            newEvent.setImage(selectedeventImagePath);
            newEvent.setPrix(price);
            newEvent.setType(type);
            newEvent.setDate_debut(Date.valueOf(startDate));
            newEvent.setDate_fin(Date.valueOf(endDate));

            // Create the event using EventService (assumes create() method exists)
            EventService es = new EventService();
            es.create(newEvent);

            // Inform the user of the successful operation
            showAlert("Success", "Event added successfully!");

            // Clear the form fields
            eventtitletf.clear();
            eventdescriptiontf.clear();
            eventpricetf.clear();
            combo.getSelectionModel().clearSelection();
            datepicker1.setValue(null);
            datepicker2.setValue(null);
            selectedeventImagePath = "";

            // Navigate to the Event List view
            goToEventList();

        } catch (Exception e) {
            showAlert("Error", "An error occurred: " + e.getMessage());
        }
    }

    /**
     * This method opens a FileChooser to select an image.
     * The selected image path is stored and a confirmation is shown.
     */
    @FXML
    void chooseImageservice(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Event Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        Stage stage = (Stage) imgadd.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            selectedeventImagePath = selectedFile.getAbsolutePath();
            imgadd.setText("Image selected");  // Provide feedback in the label
        }
    }

    /**
     * Navigates to the Event List view.
     * This method is called both from the "Retourner à List" button action and after a new event is added.
     */
    @FXML
    void vieweventlist(ActionEvent actionEvent) {
        goToEventList();
    }

    /**
     * Helper method that loads EventList.fxml and sets it as the current scene.
     */
    private void goToEventList() {
        try {
            System.out.println("Navigating to Event List view...");
            // Adjust the path as needed.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EventList.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) eventtitletf.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();


            System.out.println("Navigation to Event List view successful.");
        } catch (IOException ex) {
            ex.printStackTrace();
            showAlert("Error", "Failed to load the Event List view: " + ex.getMessage());
        }
    }

    /**
     * Displays an alert dialog with the specified title and message.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * The initialize method is automatically called after all FXML fields are injected.
     * Here, we set up the ComboBox for event type.
     */
    @FXML
    void initialize() {
        // Initialize the ComboBox with event type options
        combo.getItems().addAll("en ligne", "présentiel");
        combo.setPromptText("Choisir un type");
    }
}
