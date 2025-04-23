package tn.elearning.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.elearning.entities.Article;
import tn.elearning.services.ArticleService;
import tn.elearning.utils.NavigationUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ArticleManagerController implements Initializable {

    private final ArticleService articleService = new ArticleService();
    private Article selectedArticle;

    @FXML
    private TableView<Article> articlesTable;

    @FXML
    private TableColumn<Article, Integer> idColumn;

    @FXML
    private TableColumn<Article, String> titleColumn;

    @FXML
    private TableColumn<Article, String> categoryColumn;

    @FXML
    private TableColumn<Article, String> createdAtColumn;

    @FXML
    private TableColumn<Article, String> actionsColumn;

    @FXML
    private TextField titleField;

    @FXML
    private TextField categoryField;

    @FXML
    private TextArea contentArea;

    @FXML
    private TextField searchField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupTableColumns();
        loadArticles();

        // Add listener for table row selection
        articlesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedArticle = newSelection;
                displayArticleDetails(selectedArticle);
            }
        });

        // Add search functionality
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterArticles(newValue);
        });
    }

    private void setupTableColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        
        // Format the date
        createdAtColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getCreatedAt() != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                return new SimpleStringProperty(cellData.getValue().getCreatedAt().format(formatter));
            }
            return new SimpleStringProperty("N/A");
        });
        
        // Add view button to actions column
        actionsColumn.setCellFactory(col -> {
            TableCell<Article, String> cell = new TableCell<Article, String>() {
                private final Button viewButton = new Button("View");
                
                {
                    viewButton.getStyleClass().add("button-secondary");
                    viewButton.setStyle("-fx-font-size: 11px; -fx-padding: 5px 10px;");
                    viewButton.setOnAction(event -> {
                        Article article = getTableView().getItems().get(getIndex());
                        displayArticleDetails(article);
                    });
                }
                
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(viewButton);
                    }
                }
            };
            return cell;
        });
    }

    private void loadArticles() {
        try {
            List<Article> articles = articleService.recuperer();
            ObservableList<Article> articleList = FXCollections.observableArrayList(articles);
            articlesTable.setItems(articleList);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Could not load articles: " + e.getMessage());
        }
    }

    private void filterArticles(String searchText) {
        try {
            if (searchText == null || searchText.isEmpty()) {
                loadArticles();
                return;
            }
            
            List<Article> allArticles = articleService.recuperer();
            ObservableList<Article> filteredList = FXCollections.observableArrayList();
            
            for (Article article : allArticles) {
                if (article.getTitle().toLowerCase().contains(searchText.toLowerCase()) ||
                    article.getCategory().toLowerCase().contains(searchText.toLowerCase()) ||
                    article.getContent().toLowerCase().contains(searchText.toLowerCase())) {
                    filteredList.add(article);
                }
            }
            
            articlesTable.setItems(filteredList);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Search Error", "Error while searching: " + e.getMessage());
        }
    }

    private void displayArticleDetails(Article article) {
        if (article != null) {
            titleField.setText(article.getTitle());
            categoryField.setText(article.getCategory());
            contentArea.setText(article.getContent());
        }
    }

    @FXML
    void clearFields() {
        titleField.clear();
        categoryField.clear();
        contentArea.clear();
        selectedArticle = null;
        articlesTable.getSelectionModel().clearSelection();
    }

    @FXML
    void deleteArticle() {
        if (selectedArticle == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select an article to delete.");
            return;
        }

        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Confirm Delete");
        confirmDialog.setHeaderText("Delete Article");
        confirmDialog.setContentText("Are you sure you want to delete the article: " + selectedArticle.getTitle() + "?");

        Optional<ButtonType> result = confirmDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                articleService.supprimer(selectedArticle);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Article deleted successfully!");
                clearFields();
                loadArticles();
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Delete Error", "Could not delete article: " + e.getMessage());
            }
        }
    }

    @FXML
    public void refreshArticles() {
        loadArticles();
        clearFields();
    }

    @FXML
    void saveArticle() {
        if (selectedArticle == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select an article to update.");
            return;
        }

        if (titleField.getText().isEmpty() || categoryField.getText().isEmpty() || contentArea.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Invalid Input", "Please fill in all fields.");
            return;
        }

        try {
            // Update the selected article object with new values from the fields
            selectedArticle.setTitle(titleField.getText());
            selectedArticle.setCategory(categoryField.getText());
            selectedArticle.setContent(contentArea.getText());

            // Call the modifier method with the updated Article object
            articleService.modifier(selectedArticle);

            showAlert(Alert.AlertType.INFORMATION, "Success", "Article updated successfully!");
            loadArticles(); // Refresh the table view
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Update Error", "Could not update article: " + e.getMessage());
        }
    }

    @FXML
    void openAddArticle() {
        try {
            ensureStageIsSet();
            NavigationUtil.navigateToAddArticle();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Load Error", "Could not open add article form: " + e.getMessage());
        }
    }
    
    private void ensureStageIsSet() {
        if (NavigationUtil.getMainStage() == null && titleField != null && titleField.getScene() != null) {
            Stage currentStage = (Stage) titleField.getScene().getWindow();
            NavigationUtil.setMainStage(currentStage);
        }
    }
    
    // Helper method to show alerts
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
} 