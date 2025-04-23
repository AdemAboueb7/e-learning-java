package tn.elearning.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
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
import java.util.*;
import java.util.stream.Collectors;

public class VoirArticlesParentController implements Initializable {

    private final ArticleService articleService = new ArticleService();
    private List<Article> allArticles = new ArrayList<>();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");

    @FXML
    private FlowPane articlesContainer;

    @FXML
    private TextField searchField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadArticles();
        setupSearch();
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
                                (article.getCategory() != null && article.getCategory().toLowerCase().contains(searchText.toLowerCase())) ||
                                (article.getContent() != null && article.getContent().toLowerCase().contains(searchText.toLowerCase())))
                .collect(Collectors.toList());

        displayArticles(filtered);
    }

    private void loadArticles() {
        try {
            allArticles = articleService.recuperer();
            allArticles.sort(Comparator.comparing(Article::getCreatedAt,
                    Comparator.nullsLast(Comparator.reverseOrder())));
            loadCommentsForArticles();
            displayArticles(allArticles);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger les articles : " + e.getMessage());
        }
    }

    private void loadCommentsForArticles() {
        try {
            CommentService commentService = new CommentService();
            List<Comment> allComments = commentService.recuperer();
            Map<Integer, List<Comment>> commentsByArticle = allComments.stream()
                    .filter(comment -> comment != null && comment.getArticle() != null)
                    .collect(Collectors.groupingBy(comment -> comment.getArticle().getId()));

            for (Article article : allArticles) {
                List<Comment> articleComments = commentsByArticle.getOrDefault(article.getId(), Collections.emptyList());
                article.setComments(articleComments);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement des commentaires : " + e.getMessage());
        }
    }

    private void displayArticles(List<Article> articles) {
        articlesContainer.getChildren().clear();

        if (articles.isEmpty()) {
            Label noArticlesLabel = new Label("Aucun article trouvé.");
            noArticlesLabel.getStyleClass().add("section-title");
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
                Label contentLabel = (Label) articleCard.lookup("#contentLabel");
                Label dateLabel = (Label) articleCard.lookup("#dateLabel");

                if (titleLabel != null) titleLabel.setText(article.getTitle());
                if (categoryLabel != null) categoryLabel.setText(article.getCategory());
                if (contentLabel != null) {
                    String content = article.getContent();
                    contentLabel.setText(content != null && content.length() > 150 ? content.substring(0, 150) + "..." : content);
                }
                if (dateLabel != null && article.getCreatedAt() != null) {
                    String dateText = article.getCreatedAt().format(formatter);
                    if (article.getComments() != null && !article.getComments().isEmpty()) {
                        int commentCount = article.getComments().size();
                        dateText += " • " + commentCount + " " + (commentCount == 1 ? "commentaire" : "commentaires");
                    }
                    dateLabel.setText(dateText);
                }

                articlesContainer.getChildren().add(articleCard);
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger la carte d'article : " + e.getMessage());
            }
        }
    }

    private void openArticleDetail(Article article) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ArticleDetail.fxml"));
            Parent root = loader.load();

            ArticleDetailController controller = loader.getController();
            controller.setArticle(article);

            Stage stage = NavigationUtil.getMainStage();
            if (stage != null) {
                stage.setScene(new Scene(root));
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible d'afficher les détails de l'article.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible d'ouvrir les détails de l'article : " + e.getMessage());
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