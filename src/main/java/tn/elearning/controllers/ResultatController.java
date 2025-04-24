package tn.elearning.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ResultatController {
    @FXML private Label resultatLabel;

    public void setScore(int score, int total) {
        resultatLabel.setText("Votre score : " + score + " / " + total);
    }

    @FXML
    public void handleRetourAccueil() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ListeQuizzView.fxml"));
            VBox root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Accueil Quizz");
            stage.show();

            ((Stage) resultatLabel.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
