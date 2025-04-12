package tn.elearning.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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

    private final ArticleService articleService = new ArticleService();
    private List<Article> allArticles = new ArrayList<>();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");

    @FXML
    private FlowPane articlesContainer;

    @FXML
    private HBox categoryPillsContainer;

    @FXML
    private TextField searchField;

    @FXML
    private Button allCategoryBtn;

    @FXML
    private Button latestBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadArticles();
        setupSearch();
        generateCategoryPills();
    }

    private void setupSearch() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterArticles(newValue);
        });
    }

    private void filterArticles(String searchText) {
        if (searchText == null || searchText.isEmpty()) {
            displayArticles(allArticles);
            return;
        }

        List<Article> filtered = allArticles.stream()
                .filter(article -> 
                    article.getTitle().toLowerCase().contains(searchText.toLowerCase()) ||
                    article.getCategory().toLowerCase().contains(searchText.toLowerCase()) ||
                    article.getContent().toLowerCase().contains(searchText.toLowerCase()))
                .collect(Collectors.toList());
        
        displayArticles(filtered);
    }

    private void loadArticles() {
        try {
            allArticles = articleService.recuperer();
            loadCommentsForArticles();
            displayArticles(allArticles);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Could not load articles: " + e.getMessage());
        }
    }

    private void loadCommentsForArticles() {
        try {
            CommentService commentService = new CommentService();
            List<Comment> allComments = commentService.recuperer();
            
            // Group comments by article ID
            Map<Integer, List<Comment>> commentsByArticle = allComments.stream()
                    .filter(comment -> comment.getArticle() != null)
                    .collect(Collectors.groupingBy(comment -> comment.getArticle().getId()));
            
            // Assign comments to each article
            for (Article article : allArticles) {
                List<Comment> articleComments = commentsByArticle.get(article.getId());
                if (articleComments != null) {
                    article.setComments(articleComments);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Just log the error but continue - comments count is not critical
            System.err.println("Error loading comments: " + e.getMessage());
        }
    }

    private void displayArticles(List<Article> articles) {
        articlesContainer.getChildren().clear();
        
        if (articles.isEmpty()) {
            Label noArticlesLabel = new Label("No articles found.");
            noArticlesLabel.getStyleClass().add("section-title");
            articlesContainer.getChildren().add(noArticlesLabel);
            return;
        }
        
        for (Article article : articles) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ArticleCard.fxml"));
                VBox articleCard = loader.load();
                
                // Configure card elements
                Label titleLabel = (Label) articleCard.lookup("#titleLabel");
                Label categoryLabel = (Label) articleCard.lookup("#categoryLabel");
                Label contentLabel = (Label) articleCard.lookup("#contentLabel");
                Label dateLabel = (Label) articleCard.lookup("#dateLabel");
                Button readMoreButton = (Button) articleCard.lookup("#readMoreButton");
                
                titleLabel.setText(article.getTitle());
                categoryLabel.setText(article.getCategory());
                
                // Truncate content for the card
                String content = article.getContent();
                contentLabel.setText(content.length() > 150 ? content.substring(0, 150) + "..." : content);
                
                // Format date and add comment count if available
                if (article.getCreatedAt() != null) {
                    String dateText = article.getCreatedAt().format(formatter);
                    
                    // Add comment count if article has comments
                    if (article.getComments() != null && !article.getComments().isEmpty()) {
                        int commentCount = article.getComments().size();
                        dateText += " • " + commentCount + " " + (commentCount == 1 ? "comment" : "comments");
                    }
                    
                    dateLabel.setText(dateText);
                } else {
                    dateLabel.setText("Date unavailable");
                }
                
                // Handle read more action
                readMoreButton.setOnAction(event -> openArticleDetail(article));
                
                articlesContainer.getChildren().add(articleCard);
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "Could not load article card: " + e.getMessage());
            }
        }
    }

    private void generateCategoryPills() {
        categoryPillsContainer.getChildren().clear();
        
        // Get unique categories
        Set<String> categories = allArticles.stream()
                .map(Article::getCategory)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        
        for (String category : categories) {
            Button categoryButton = new Button(category);
            categoryButton.getStyleClass().add("button-secondary");
            categoryButton.setOnAction(event -> filterByCategory(category));
            
            categoryPillsContainer.getChildren().add(categoryButton);
        }
    }

    @FXML
    void filterByAllCategories(ActionEvent event) {
        displayArticles(allArticles);
        highlightButton(allCategoryBtn);
    }

    @FXML
    void filterByLatest(ActionEvent event) {
        List<Article> latest = allArticles.stream()
                .sorted(Comparator.comparing(Article::getCreatedAt, Comparator.nullsLast(Comparator.reverseOrder())))
                .limit(10)
                .collect(Collectors.toList());
        
        displayArticles(latest);
        highlightButton(latestBtn);
    }
    
    private void filterByCategory(String category) {
        List<Article> filtered = allArticles.stream()
                .filter(article -> category.equals(article.getCategory()))
                .collect(Collectors.toList());
        
        displayArticles(filtered);
        
        // Reset button highlight
        allCategoryBtn.getStyleClass().clear();
        allCategoryBtn.getStyleClass().add("button-secondary");
        latestBtn.getStyleClass().clear();
        latestBtn.getStyleClass().add("button-secondary");
    }
    
    private void highlightButton(Button button) {
        // Reset all filter buttons
        allCategoryBtn.getStyleClass().clear();
        allCategoryBtn.getStyleClass().add("button-secondary");
        latestBtn.getStyleClass().clear();
        latestBtn.getStyleClass().add("button-secondary");
        
        // Highlight the selected button
        button.getStyleClass().clear();
        button.getStyleClass().add("button");
    }

    @FXML
    void refreshArticles() {
        loadArticles();
        generateCategoryPills();
        allCategoryBtn.getStyleClass().clear();
        allCategoryBtn.getStyleClass().add("button");
        latestBtn.getStyleClass().clear();
        latestBtn.getStyleClass().add("button-secondary");
        searchField.clear();
    }
    
    @FXML
    void navigateToAddArticle(ActionEvent event) {
        try {
            ensureStageIsSet();
            NavigationUtil.navigateToAddArticle();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not navigate to add article: " + e.getMessage());
        }
    }
    
    private void openArticleDetail(Article article) {
        try {
            ensureStageIsSet();
            NavigationUtil.navigateToArticleDetail(article);
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Could not open article details: " + e.getMessage());
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
        alert.setContentText(content);
        alert.showAndWait();
    }
} 