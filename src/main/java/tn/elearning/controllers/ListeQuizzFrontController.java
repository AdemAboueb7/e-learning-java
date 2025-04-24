package tn.elearning.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.elearning.entities.quizz;
import tn.elearning.services.ServiceQuizz;

import java.io.IOException;
import java.util.List;

public class ListeQuizzFrontController {

    @FXML
    private FlowPane quizzContainer;

    private final ServiceQuizz serviceQuizz = new ServiceQuizz();

    @FXML
    public void initialize() {
        List<quizz> quizzList = serviceQuizz.recuperer();

        for (quizz q : quizzList) {
            VBox card = createQuizzCard(q);
            quizzContainer.getChildren().add(card);
        }
    }

    private VBox createQuizzCard(quizz q) {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 15; -fx-border-color: #ccc; -fx-border-radius: 10; -fx-background-radius: 10;");
        card.setPrefWidth(200);

        Label title = new Label(q.getMatiere());
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Button btnStart = new Button("Démarrer");
        btnStart.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        btnStart.setOnAction(e -> handleStartQuizz(q));

        card.getChildren().addAll(title, btnStart);
        return card;
    }

    private void handleStartQuizz(quizz selectedQuizz) {
        if (selectedQuizz == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Aucun Quizz sélectionné");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un quizz pour commencer.");
            alert.showAndWait();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/QuizzQuestionsView.fxml"));
            VBox root = loader.load();

            QuizzQuestionsController controller = loader.getController();
            controller.setQuizz(selectedQuizz);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Questions du Quizz");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAccueil() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/PageAccueil.fxml"));
            VBox root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Accueil");
            stage.setScene(new Scene(root));
            stage.show();

            Stage currentStage = (Stage) quizzContainer.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
