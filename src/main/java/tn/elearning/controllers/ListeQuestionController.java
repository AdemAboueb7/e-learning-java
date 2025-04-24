package tn.elearning.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.elearning.entities.question;
import tn.elearning.entities.quizz;
import tn.elearning.services.ServiceQuestion;

import java.io.IOException;
import java.util.List;

public class ListeQuestionController {

    @FXML private TableView<question> tableView;
    @FXML private TableColumn<question, Integer> colId;
    @FXML private TableColumn<question, String> colQuestion;
    @FXML private TableColumn<question, String> colQuizz;
    @FXML private TableColumn<question, Void> colActions;

    private final ServiceQuestion service = new ServiceQuestion();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colQuestion.setCellValueFactory(new PropertyValueFactory<>("question"));
        colQuizz.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getQuizz() != null ? cellData.getValue().getQuizz().getMatiere() : "N/A"
        ));

        // Boutons Modifier, Supprimer et Voir Suggestions
        colActions.setCellFactory(param -> new TableCell<>() {
            final Button btnEdit = new Button("✏️ Modifier");
            final Button btnDelete = new Button("🗑️ Supprimer");
            final Button btnSuggestions = new Button("📋 Suggestions");
            final HBox pane = new HBox(10, btnEdit, btnDelete, btnSuggestions);

            {
                btnEdit.setOnAction(event -> {
                    question q = getTableView().getItems().get(getIndex());
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ModifierQuestionView.fxml"));
                        VBox root = loader.load();

                        ModifierQuestionController controller = loader.getController();
                        controller.setQuestion(q);

                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.setTitle("Modifier Question");
                        stage.show();

                        ((Stage) tableView.getScene().getWindow()).close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                btnDelete.setOnAction(event -> {
                    question q = getTableView().getItems().get(getIndex());
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Supprimer cette question ?", ButtonType.YES, ButtonType.NO);
                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.YES) {
                            service.supprimer(q.getId());
                            tableView.getItems().remove(q);
                        }
                    });
                });

                btnSuggestions.setOnAction(e -> {
                    question q = getTableView().getItems().get(getIndex());
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ListeSuggestionView.fxml"));
                        VBox root = loader.load();

                        ListeSuggestionController ctrl = loader.getController();
                        ctrl.setQuestion(q);

                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.setTitle("Suggestions de la Question");
                        stage.show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : pane);
            }
        });

        List<question> questions = service.getAll();
        ObservableList<question> observableList = FXCollections.observableArrayList(questions);
        tableView.setItems(observableList);
    }

    @FXML
    public void retourAjout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AddQuestionView.fxml"));
            VBox root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Ajouter une Question");
            stage.show();

            Stage currentStage = (Stage) tableView.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
