package tn.elearning.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class PageAccueilController {

    @FXML
    private Button btnAdmin;

    @FXML
    private Button btnUser;

    @FXML
    private void handleAdmin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ListeQuizzView.fxml"));
            Parent root = loader.load();  // ✅ Changed VBox → Parent
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Espace Admin");
            stage.show();
            btnAdmin.getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUser() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/BaseLayout.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Espace Utilisateur");

            // Set preferred window size
            Scene scene = new Scene(root, 1000, 700); // Width x Height (adjust as needed)
            stage.setScene(scene);
            stage.show();

            // Optionally close the current window
            btnUser.getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

