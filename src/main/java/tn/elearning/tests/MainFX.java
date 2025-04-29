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
        try {

            tn.elearning.utils.NavigationUtil.setMainStage(primaryStage);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/suiviadmin.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Se Connecter");

            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace(); // Better than just printing the message
        }
    }
}
