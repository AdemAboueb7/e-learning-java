package tn.elearning.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
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
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ArticleDetailController implements Initializable {

    private final ArticleService articleService = new ArticleService();
    private final CommentService commentService = new CommentService();
    private Article currentArticle;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
    private final DateTimeFormatter commentFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy 'at' HH:mm");

    @FXML
    private Label titleLabel;

    @FXML
    private Label categoryLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label contentLabel;

    @FXML
    private HBox relatedArticlesContainer;
    
    @FXML
    private VBox commentsContainer;
    
    @FXML
    private TextArea commentTextArea;
    
    @FXML
    private Button submitCommentBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialization will happen when setArticle is called
    }

    public void setArticle(Article article) {
        this.currentArticle = article;
        displayArticleDetails();
        loadRelatedArticles();
        loadComments();
    }

    private void displayArticleDetails() {
        if (currentArticle == null) return;

        titleLabel.setText(currentArticle.getTitle());
        categoryLabel.setText(currentArticle.getCategory());
        contentLabel.setText(currentArticle.getContent());

        if (currentArticle.getCreatedAt() != null) {
            dateLabel.setText("Published on: " + currentArticle.getCreatedAt().format(formatter));
        } else {
            dateLabel.setText("Published date unavailable");
        }
    }

    private void loadRelatedArticles() {
        try {
            // Get all articles with the same category
            List<Article> allArticles = articleService.recuperer();
            
            List<Article> relatedArticles = allArticles.stream()
                    .filter(article -> article.getCategory() != null &&
                            article.getCategory().equals(currentArticle.getCategory()) &&
                            article.getId() != currentArticle.getId())
                    .limit(3)
                    .collect(Collectors.toList());
            
            displayRelatedArticles(relatedArticles);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void loadComments() {
        try {
            List<Comment> allComments = commentService.recuperer();
            
            // Filter comments for current article
            List<Comment> articleComments = allComments.stream()
                    .filter(comment -> comment.getArticle() != null && 
                            comment.getArticle().getId() == currentArticle.getId())
                    .sorted(Comparator.comparing(Comment::getCreatedAt).reversed())
                    .collect(Collectors.toList());
            
            displayComments(articleComments);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Could not load comments: " + e.getMessage());
        }
    }
    
    private void displayComments(List<Comment> comments) {
        commentsContainer.getChildren().clear();
        
        if (comments.isEmpty()) {
            Label noCommentsLabel = new Label("No comments yet. Be the first to comment!");
            commentsContainer.getChildren().add(noCommentsLabel);
            return;
        }
        
        for (Comment comment : comments) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/CommentCard.fxml"));
                VBox commentCard = loader.load();
                
                // Configure card elements
                Label userLabel = (Label) commentCard.lookup("#userLabel");
                Label dateLabel = (Label) commentCard.lookup("#dateLabel");
                Label contentLabel = (Label) commentCard.lookup("#contentLabel");
                
                userLabel.setText("User #" + comment.getUserId());
                
                if (comment.getCreatedAt() != null) {
                    dateLabel.setText(comment.getCreatedAt().format(commentFormatter));
                } else {
                    dateLabel.setText("Posted recently");
                }
                
                contentLabel.setText(comment.getContenu());
                
                commentsContainer.getChildren().add(commentCard);
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "Could not load comment card: " + e.getMessage());
            }
        }
    }

    private void displayRelatedArticles(List<Article> articles) {
        relatedArticlesContainer.getChildren().clear();
        
        if (articles.isEmpty()) {
            Label noRelatedLabel = new Label("No related articles found.");
            relatedArticlesContainer.getChildren().add(noRelatedLabel);
            return;
        }
        
        for (Article article : articles) {
            try {
                // Create mini cards for related articles
                VBox miniCard = createMiniArticleCard(article);
                relatedArticlesContainer.getChildren().add(miniCard);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private VBox createMiniArticleCard(Article article) throws IOException {
        VBox card = new VBox();
        card.getStyleClass().add("article-card");
        card.setPrefWidth(200);
        card.setPrefHeight(100);
        card.setOnMouseClicked(event -> {
            // Navigate to the article detail view
            try {
                NavigationUtil.navigateToArticleDetail(article);
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not navigate to article: " + e.getMessage());
            }
        });
        
        Label titleLabel = new Label(article.getTitle());
        titleLabel.getStyleClass().add("card-title");
        titleLabel.setWrapText(true);
        
        Label categoryLabel = new Label(article.getCategory());
        categoryLabel.getStyleClass().add("card-category");
        
        card.getChildren().addAll(titleLabel, categoryLabel);
        
        return card;
    }
    
    @FXML
    void submitComment(ActionEvent event) {
        if (commentTextArea.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Empty Comment", "Please write something before submitting.");
            return;
        }
        
        try {
            Comment newComment = new Comment();
            newComment.setContenu(commentTextArea.getText().trim());
            newComment.setArticle(currentArticle);
            newComment.setUserId(1); // Using a default user ID for now
            
            commentService.ajouter(newComment);
            
            // Clear the textarea
            commentTextArea.clear();
            
            // Reload comments to show the new one
            loadComments();
            
            showAlert(Alert.AlertType.INFORMATION, "Comment Added", "Your comment has been added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Could not add comment: " + e.getMessage());
        }
    }
    
    @FXML
    void goBack(ActionEvent event) {
        try {
            ensureStageIsSet();
            NavigationUtil.navigateToViewArticles();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not navigate back: " + e.getMessage());
        }
    }
    
    private void ensureStageIsSet() {
        if (NavigationUtil.getMainStage() == null && titleLabel != null && titleLabel.getScene() != null) {
            Stage currentStage = (Stage) titleLabel.getScene().getWindow();
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