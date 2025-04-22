package tn.elearning.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import tn.elearning.entities.Article;
import tn.elearning.services.ArticleService;
import tn.elearning.utils.NavigationUtil;

import java.io.IOException;
import java.util.Arrays;

public class ArticleEditController {
    @FXML private TextField titleField;
    @FXML private ComboBox<String> categoryComboBox;
    @FXML private TextArea contentArea;
    @FXML private CheckBox publishedCheckBox;

    private Article currentArticle;
    private ArticleService articleService;

    public void initialize() {
        articleService = new ArticleService();
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
        contentArea.setText(article.getContent());
        publishedCheckBox.setSelected(article.isPublished());
    }

    @FXML
    private void saveArticle() {
        if (validateForm()) {
            try {
                currentArticle.setTitle(titleField.getText());
                currentArticle.setCategory(categoryComboBox.getValue());
                currentArticle.setContent(contentArea.getText());
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
        if (contentArea.getText().trim().isEmpty()) {
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