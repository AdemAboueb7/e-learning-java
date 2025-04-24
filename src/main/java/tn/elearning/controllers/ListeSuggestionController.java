package tn.elearning.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.elearning.entities.Suggestion;
import tn.elearning.entities.question;
import tn.elearning.services.ServiceSuggestion;

import java.io.IOException;
import java.util.List;

public class ListeSuggestionController {

    @FXML private TableView<Suggestion> tableView;
    @FXML private TableColumn<Suggestion, Integer> colId;
    @FXML private TableColumn<Suggestion, String> colContenu;
    @FXML private TableColumn<Suggestion, Boolean> colCorrecte;
    @FXML private TableColumn<Suggestion, Void> colActions;

    private final ServiceSuggestion service = new ServiceSuggestion();
    private question question;

    public void setQuestion(question q) {
        this.question = q;
        List<Suggestion> list = service.recupererParQuestion(q.getId());
        tableView.setItems(FXCollections.observableArrayList(list));
    }

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colContenu.setCellValueFactory(new PropertyValueFactory<>("contenu"));
        colCorrecte.setCellValueFactory(new PropertyValueFactory<>("estCorrecte"));

        colActions.setCellFactory(col -> new TableCell<>() {
            final Button btnEdit = new Button("✏️");
            final Button btnDelete = new Button("🗑️");
            final HBox pane = new HBox(5, btnEdit, btnDelete);

            {
                btnEdit.setOnAction(e -> {
                    Suggestion s = getTableView().getItems().get(getIndex());
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ModifierSuggestionView.fxml"));
                        VBox root = loader.load();

                        ModifierSuggestionController controller = loader.getController();
                        controller.setSuggestion(s);

                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.setTitle("Modifier Suggestion");
                        stage.show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });

                btnDelete.setOnAction(e -> {
                    Suggestion s = getTableView().getItems().get(getIndex());
                    service.supprimer(s.getId());
                    getTableView().getItems().remove(s);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : pane);
            }
        });
    }
}
