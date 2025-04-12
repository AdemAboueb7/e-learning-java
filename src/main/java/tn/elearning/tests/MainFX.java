package tn.elearning.tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/AjouterAbonnement.fxml"));
    Parent root = fxmlLoader.load();
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.setTitle("AjouterAbonnement");
    primaryStage.show();
        Stage secondStage = new Stage();
        FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("/AfficherAbonnements.fxml"));
        Parent root2 = fxmlLoader2.load();
        Scene scene2 = new Scene(root2);

        secondStage.setScene(scene2);
        secondStage.setTitle("AfficherAbonnements");
        secondStage.show();

    }
}
