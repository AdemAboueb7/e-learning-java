package tn.elearning;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class QuizzApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/PageAccueil.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 500); // taille ajustée
        scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());


        stage.setTitle("Ajouter un Quizz");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }

}
