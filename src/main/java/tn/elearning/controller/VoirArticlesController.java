package tn.elearning.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.elearning.entities.Article;
import tn.elearning.entities.Comment;
import tn.elearning.services.ArticleService;
import tn.elearning.services.CommentService;
import tn.elearning.utils.NavigationUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class VoirArticlesController implements Initializable {

    @FXML private FlowPane articlesContainer;
    @FXML private TextField searchField;
    @FXML private ScrollPane articlesPane;
    @FXML private VBox statisticsContainer;

    // Statistics components
    @FXML private Label totalArticlesLabel;
    @FXML private Label totalCommentsLabel;
    @FXML private Label avgCommentsLabel;
    @FXML private PieChart categoriesChart;
    @FXML private LineChart<String, Number> timelineChart;
    @FXML private TableView<Article> topArticlesTable;
    @FXML private TableColumn<Article, String> titleColumn;
    @FXML private TableColumn<Article, String> categoryColumn;
    @FXML private TableColumn<Article, Integer> commentsColumn;
    @FXML private TableColumn<Article, LocalDateTime> dateColumn;

    private final ArticleService articleService = new ArticleService();
    private final CommentService commentService = new CommentService();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.FRENCH);
    private List<Article> allArticles;
    private List<Comment> allComments;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Load initial data first
            loadData();

            // Initialize table columns
            titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
            dateColumn.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

            // Format date column
            dateColumn.setCellFactory(column -> new TableCell<Article, LocalDateTime>() {
                @Override
                protected void updateItem(LocalDateTime date, boolean empty) {
                    super.updateItem(date, empty);
                    if (empty || date == null) {
                        setText(null);
                    } else {
                        setText(date.format(formatter));
                    }
                }
            });

            // Format comments column
            commentsColumn.setCellValueFactory(cellData -> {
                Article article = cellData.getValue();
                if (article != null && allComments != null) {
                    long commentCount = allComments.stream()
                            .filter(c -> c.getArticle() != null && c.getArticle().getId() == article.getId())
                            .count();
                    return new javafx.beans.property.SimpleIntegerProperty((int) commentCount).asObject();
                }
                return new javafx.beans.property.SimpleIntegerProperty(0).asObject();
            });

            // Initialize search functionality
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue == null || newValue.isEmpty()) {
                    displayArticles(allArticles);
                } else {
                    String searchTerm = newValue.toLowerCase();
                    List<Article> filteredArticles = allArticles.stream()
                            .filter(article ->
                                    article.getTitle().toLowerCase().contains(searchTerm) ||
                                            article.getCategory().toLowerCase().contains(searchTerm) ||
                                            article.getContent().toLowerCase().contains(searchTerm))
                            .collect(Collectors.toList());
                    displayArticles(filteredArticles);
                }
            });

            // Initialize visibility: articlesPane visible, statisticsContainer hidden by default
            articlesPane.setVisible(true);
            statisticsContainer.setVisible(false);

            // Update statistics after everything is initialized
            updateStatistics();

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger les articles : " + e.getMessage());
        }
    }

    private void loadData() throws SQLException {
        allArticles = articleService.recuperer();
        allComments = commentService.recuperer();
        displayArticles(allArticles);
    }

    private void updateStatistics() {
        if (allArticles == null || allComments == null) return;

        // Update summary statistics
        int totalArticles = allArticles.size();
        int totalComments = allComments.size();
        double avgComments = totalArticles > 0 ? (double) totalComments / totalArticles : 0;

        totalArticlesLabel.setText(String.valueOf(totalArticles));
        totalCommentsLabel.setText(String.valueOf(totalComments));
        avgCommentsLabel.setText(String.format("%.1f", avgComments));

        // Update categories chart
        Map<String, Long> categoryCounts = allArticles.stream()
                .collect(Collectors.groupingBy(Article::getCategory, Collectors.counting()));

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        categoryCounts.forEach((category, count) ->
                pieChartData.add(new PieChart.Data(category, count))
        );
        categoriesChart.setData(pieChartData);

        // Update timeline chart
        Map<String, Long> monthlyArticles = allArticles.stream()
                .collect(Collectors.groupingBy(
                        article -> article.getCreatedAt().format(DateTimeFormatter.ofPattern("MMM yyyy")),
                        Collectors.counting()
                ));

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Articles publiés");
        monthlyArticles.forEach((month, count) ->
                series.getData().add(new XYChart.Data<>(month, count))
        );
        timelineChart.getData().clear();
        timelineChart.getData().add(series);

        // Update top articles table
        Map<Article, Long> articleCommentCounts = allArticles.stream()
                .collect(Collectors.toMap(
                        article -> article,
                        article -> allComments.stream()
                                .filter(comment -> comment.getArticle() != null && comment.getArticle().getId() == article.getId())
                                .count()
                ));

        List<Article> topArticles = articleCommentCounts.entrySet().stream()
                .sorted(Map.Entry.<Article, Long>comparingByValue().reversed())
                .limit(10)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        // Format date column
        dateColumn.setCellFactory(column -> new TableCell<Article, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime date, boolean empty) {
                super.updateItem(date, empty);
                if (empty || date == null) {
                    setText(null);
                } else {
                    setText(date.format(formatter));
                }
            }
        });

        // Format comments column to show the actual comment count
        commentsColumn.setCellValueFactory(cellData -> {
            Article article = cellData.getValue();
            long commentCount = articleCommentCounts.getOrDefault(article, 0L);
            return new javafx.beans.property.SimpleIntegerProperty((int) commentCount).asObject();
        });

        topArticlesTable.setItems(FXCollections.observableArrayList(topArticles));
    }

    @FXML
    private void toggleStatistics() {
        boolean showStats = !statisticsContainer.isVisible();
        statisticsContainer.setVisible(showStats);
        articlesPane.setVisible(!showStats);

        if (showStats) {
            updateStatistics();
        }
    }

    private void displayArticles(List<Article> articles) {
        articlesContainer.getChildren().clear();

        if (articles.isEmpty()) {
            Label noArticlesLabel = new Label("Aucun article trouvé.");
            noArticlesLabel.getStyleClass().add("no-content-label");
            articlesContainer.getChildren().add(noArticlesLabel);
            return;
        }

        for (Article article : articles) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ArticleCard.fxml"));
                VBox articleCard = loader.load();

                articleCard.setCursor(Cursor.HAND);
                articleCard.setOnMouseClicked(event -> openArticleDetail(article));

                Label titleLabel = (Label) articleCard.lookup("#titleLabel");
                Label categoryLabel = (Label) articleCard.lookup("#categoryLabel");

                if (titleLabel != null) titleLabel.setText(article.getTitle());
                if (categoryLabel != null) categoryLabel.setText(article.getCategory());

                articlesContainer.getChildren().add(articleCard);
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger la carte d'article : " + e.getMessage());
            }
        }
    }

    @FXML
    void navigateToAddArticle(ActionEvent event) {
        try {
            ensureStageIsSet();
            NavigationUtil.navigateToAddArticle();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur de Navigation", "Impossible de naviguer vers l'ajout d'article : " + e.getMessage());
        }
    }

    private void openArticleDetail(Article article) {
        try {
            ensureStageIsSet();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ArticleDetail.fxml"));
            Parent root = loader.load();

            ArticleDetailController controller = loader.getController();
            if (controller != null) {
                controller.setArticle(article);

                Stage mainStage = NavigationUtil.getMainStage();
                if (mainStage != null) {
                    Scene scene = new Scene(root);
                    scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
                    mainStage.setScene(scene);
                    mainStage.setTitle(article.getTitle());
                } else {
                    System.err.println("Main stage not found in NavigationUtil for ArticleDetail navigation.");
                    showAlert(Alert.AlertType.ERROR, "Erreur Interne", "Impossible d'afficher les détails de l'article.");
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger le contrôleur de l'article.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur de Chargement", "Impossible d'ouvrir les détails de l'article : " + e.getMessage());
        }
    }

    private void ensureStageIsSet() {
        if (NavigationUtil.getMainStage() == null && searchField != null && searchField.getScene() != null) {
            Stage currentStage = (Stage) searchField.getScene().getWindow();
            NavigationUtil.setMainStage(currentStage);
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}