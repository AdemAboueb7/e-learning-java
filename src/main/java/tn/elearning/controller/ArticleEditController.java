package tn.elearning.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;
import tn.elearning.entities.Article;
import tn.elearning.services.ArticleService;
import tn.elearning.utils.NavigationUtil;
import javafx.application.Platform;
import javafx.concurrent.Worker;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

public class ArticleEditController {
    @FXML private TextField titleField;
    @FXML private ComboBox<String> categoryComboBox;
    @FXML private WebView webView;
    @FXML private CheckBox publishedCheckBox;

    private Article currentArticle;
    private ArticleService articleService;
    private WebEngine webEngine;

    public void initialize() {
        articleService = new ArticleService();
        
        // Initialize WebView
        webEngine = webView.getEngine();
        URL editorUrl = getClass().getResource("/html/editor.html");
        if (editorUrl != null) {
            System.out.println("Loading editor from: " + editorUrl.toExternalForm());
            webEngine.load(editorUrl.toExternalForm());
            
            // Add a listener for the page load
            webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
                if (newState == Worker.State.SUCCEEDED) {
                    System.out.println("Editor loaded successfully");
                    // If we have a pending article to set, set it now
                    if (currentArticle != null) {
                        Platform.runLater(() -> {
                            try {
                                webEngine.executeScript("setContent('" + currentArticle.getContent().replace("'", "\\'") + "')");
                            } catch (Exception e) {
                                System.err.println("Error setting content: " + e.getMessage());
                                e.printStackTrace();
                            }
                        });
                    }
                }
            });
        } else {
            System.err.println("Could not find editor.html resource");
        }
        
        // Updated categories for children 6-12 with encouragement/tracking themes
        categoryComboBox.getItems().clear();
        categoryComboBox.getItems().addAll(Arrays.asList(
            "Aventures Mathématiques", 
            "Explorateurs Scientifiques", 
            "Lecture Amusante", 
            "Écriture Créative", 
            "Explorateurs du Monde", // Géo/Histoire
            "Studio d'Art", 
            "Créateurs de Musique", 
            "Boosteurs de Cerveau", // Puzzles/Logique
            "Sorciers du Code", // Codage de base
            "Superstars de la Sécurité", 
            "Héros en Bonne Santé", 
            "Mon Progrès & Récompenses" // Lié aux fonctionnalités
        ));
    }

    public void setArticle(Article article) {
        this.currentArticle = article;
        titleField.setText(article.getTitle());
        categoryComboBox.setValue(article.getCategory());
        publishedCheckBox.setSelected(article.isPublished());
        
        // Only try to set content if the page is already loaded
        if (webEngine.getLoadWorker().getState() == Worker.State.SUCCEEDED) {
            Platform.runLater(() -> {
                try {
                    webEngine.executeScript("setContent('" + article.getContent().replace("'", "\\'") + "')");
                } catch (Exception e) {
                    System.err.println("Error setting content: " + e.getMessage());
                    e.printStackTrace();
                }
            });
        }
        // If not loaded, the content will be set when the page loads (see initialize())
    }

    @FXML
    private void saveArticle() {
        if (validateForm()) {
            try {
                currentArticle.setTitle(titleField.getText());
                currentArticle.setCategory(categoryComboBox.getValue());
                String content = (String) webEngine.executeScript("getContent()");
                currentArticle.setContent(content);
                currentArticle.setPublished(publishedCheckBox.isSelected());
                
                articleService.modifier(currentArticle);
                // Navigate back to list view instead of closing window
                NavigationUtil.navigateToViewArticles();
            } catch (Exception e) {
                 // Handle potential IOException from navigation
                e.printStackTrace();
                showError("Erreur", "Erreur lors de la sauvegarde ou de la navigation : " + e.getMessage()); 
            }
        }
    }

    @FXML
    private void cancel() {
        // Navigate back to list view instead of closing window
         try {
             NavigationUtil.navigateToViewArticles();
         } catch (IOException e) {
             e.printStackTrace();
             showError("Erreur de Navigation", "Impossible de retourner à la liste des articles.");
         }
    }

    private boolean validateForm() {
        if (titleField.getText().trim().isEmpty()) {
            showError("Erreur de Validation", "Le titre est requis");
            return false;
        }
        if (categoryComboBox.getValue() == null) {
            showError("Erreur de Validation", "La catégorie est requise");
            return false;
        }
        
        String content = (String) webEngine.executeScript("getContent()");
        if (content == null || content.trim().isEmpty()) {
            showError("Erreur de Validation", "Le contenu est requis");
            return false;
        }
        return true;
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
} 