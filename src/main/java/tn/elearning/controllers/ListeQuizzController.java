package tn.elearning.controllers;

import tn.elearning.entities.quizz;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.geometry.Insets;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import tn.elearning.services.ServiceQuizz;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;
import java.util.ArrayList;

public class ListeQuizzController implements Initializable {

    @FXML
    private FlowPane quizFlowPane;

    @FXML
    private Button btnAddQuiz;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadQuizzes();
    }

    private void loadQuizzes() {
        List<quizz> quizzes = fetchAllQuizzes();
        quizFlowPane.getChildren().clear();
        for (quizz quiz : quizzes) {
            Node card = createQuizCard(quiz);
            quizFlowPane.getChildren().add(card);
        }
    }

    private Node createQuizCard(quizz quiz) {
        VBox card = new VBox();
        card.setSpacing(8);
        card.setPadding(new Insets(10));
        card.setPrefWidth(250);
        card.setMaxWidth(250);
        card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 8; " +
                "-fx-border-radius: 8; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5,0.5, 0, 0);");

        Label titleLabel = new Label(quiz.getMatiere());
        titleLabel.setStyle("-fx-font-size: 14pt; -fx-font-weight: bold;");

        Button editBtn = new Button("Modifier");
        Button deleteBtn = new Button("Supprimer");

        editBtn.setOnAction(e -> handleEditQuiz(quiz));
        deleteBtn.setOnAction(e -> handleDeleteQuiz(quiz));

        card.getChildren().addAll(titleLabel, editBtn, deleteBtn);
        return card;
    }

    @FXML
    private void handleAddQuizz(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AddQuizzView.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Ajouter un Quizz");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            loadQuizzes();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleEditQuiz(quizz quiz) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ModifierQuizzView.fxml"));
            Scene scene = new Scene(loader.load());
            ModifierQuizzController controller = loader.getController();
            controller.setQuizz(quiz);
            Stage stage = new Stage();
            stage.setTitle("Modifier Quizz");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            loadQuizzes();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleDeleteQuiz(quizz quiz) {
        quizFlowPane.getChildren().removeIf(node -> {
            if (node instanceof VBox) {
                Label lbl = (Label) ((VBox) node).getChildren().get(0);
                return lbl.getText().equals(quiz.getMatiere());
            }
            return false;
        });
    }

    private List<quizz> fetchAllQuizzes() {
        return new ServiceQuizz().recuperer();
    }

}