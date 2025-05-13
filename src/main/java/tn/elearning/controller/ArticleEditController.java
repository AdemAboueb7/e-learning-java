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
import netscape.javascript.JSObject;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Base64;

public class ArticleEditController {
    @FXML private TextField titleField;
    @FXML private ComboBox<String> categoryComboBox;
    @FXML private WebView webView;
    @FXML private CheckBox publishedCheckBox;

    private Article currentArticle;
    private ArticleService articleService;
    private WebEngine webEngine;
    private boolean editorLoaded = false;

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
                    editorLoaded = true;
                    
                    // If we have a pending article to set, set it now
                    if (currentArticle != null) {
                        Platform.runLater(this::setEditorContent);
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
        if (editorLoaded) {
            Platform.runLater(this::setEditorContent);
        }
        // If not loaded, the content will be set when the page loads (see initialize())
    }
    
    private void setEditorContent() {
        try {
            if (currentArticle != null && currentArticle.getContent() != null) {
                // Method 1: Use a safer approach with Base64 encoding to avoid escaping issues
                String base64Content = Base64.getEncoder().encodeToString(
                    currentArticle.getContent().getBytes("UTF-8"));
                
                // Decode the content in JavaScript and set it in CKEditor
                webEngine.executeScript(
                    "setContent(decodeURIComponent(escape(window.atob('" + base64Content + "'))))");
                
                // Log success
                System.out.println("Content set in editor successfully");
            } else {
                System.out.println("No content to set in editor");
            }
        } catch (Exception e) {
            System.err.println("Error setting content in editor: " + e.getMessage());
            e.printStackTrace();
            
            // Fallback method if the first approach fails
            try {
                String content = currentArticle.getContent()
                    .replace("\\", "\\\\")
                    .replace("'", "\\'")
                    .replace("\n", "\\n")
                    .replace("\r", "\\r");
                
                webEngine.executeScript("setContent('" + content + "')");
                System.out.println("Content set using fallback method");
            } catch (Exception ex) {
                System.err.println("Fallback method also failed: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
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
                
                // Show success message
                showSuccess("Article Modifié", "L'article a été modifié avec succès.");
                
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
    
    private void showSuccess(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
} 