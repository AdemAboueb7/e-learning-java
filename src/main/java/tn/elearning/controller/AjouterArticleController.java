package tn.elearning.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;
import javafx.stage.Stage;
import tn.elearning.entities.Article;
import tn.elearning.services.ArticleService;
import tn.elearning.utils.NavigationUtil;
import javafx.concurrent.Worker.State;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;

public class AjouterArticleController {

    private final ArticleService as = new ArticleService();

    @FXML
    private ComboBox<String> categoryComboBox;

    @FXML
    private WebView webView;

    @FXML
    private TextField titleTF;

    private WebEngine webEngine;

    @FXML
    public void initialize() {
        // Initialize WebView with necessary permissions
        webView.setContextMenuEnabled(true);
        webEngine = webView.getEngine();
        
        // Enable JavaScript
        webEngine.setJavaScriptEnabled(true);
        
        // Load the editor
        URL editorUrl = getClass().getResource("/html/editor.html");
        if (editorUrl != null) {
            System.out.println("Loading editor from: " + editorUrl.toExternalForm());
            webEngine.load(editorUrl.toExternalForm());
            
            // Add a listener to handle loading errors
            webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
                if (newState == State.FAILED) {
                    System.err.println("Failed to load editor: " + webEngine.getLoadWorker().getException());
                } else if (newState == State.SUCCEEDED) {
                    System.out.println("Editor loaded successfully");
                }
            });
        } else {
            System.err.println("Could not find editor.html resource");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Editor Loading Error");
            alert.setContentText("Could not find the editor resource file.");
            alert.showAndWait();
        }

        // Remplir les catégories pour enfants 6-12
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

    @FXML
    void ajouter(ActionEvent event) {
        try {
            // Validation détaillée
            String title = titleTF.getText().trim();
            String selectedCategory = categoryComboBox.getValue();
            
            StringBuilder errors = new StringBuilder();
            
            // Validation du titre
            if (title.isEmpty()) {
                errors.append("Le titre est obligatoire.\n");
            } else if (title.length() < 3) {
                errors.append("Le titre doit contenir au moins 3 caractères.\n");
            } else if (title.length() > 100) {
                errors.append("Le titre ne doit pas dépasser 100 caractères.\n");
            }
            
            // Validation de la catégorie
            if (selectedCategory == null || selectedCategory.trim().isEmpty()) {
                errors.append("La catégorie est obligatoire.\n");
            }
            
            // Get content from CKEditor
            String content = (String) webEngine.executeScript("getContent()");
            
            // Validation du contenu
            if (content == null || content.trim().isEmpty()) {
                errors.append("Le contenu est obligatoire.\n");
            } else if (content.length() < 10) {
                errors.append("Le contenu doit contenir au moins 10 caractères.\n");
            } else if (content.length() > 50000) {
                errors.append("Le contenu ne doit pas dépasser 50000 caractères.\n");
            }
            
            // S'il y a des erreurs, les afficher
            if (errors.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de Validation");
                alert.setHeaderText("Veuillez corriger les erreurs suivantes :");
                alert.setContentText(errors.toString());
                alert.showAndWait();
                return;
            }

            // Si tout est valide, créer et sauvegarder l'article
            Article article = new Article(selectedCategory, content, title);
            article.setUserId(1); // ID utilisateur par défaut
            article.setImage("default.jpg"); // Image par défaut
            
            System.out.println("Ajout de l'article : " + article);
            
            // Ajouter à la base de données
            as.ajouter(article);
            
            // Afficher message de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setContentText("Article ajouté avec succès !");
            alert.showAndWait();
            
            // Naviguer vers la vue des articles
            navigateToVoirArticles();
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de l'article : " + e.getMessage());
            e.printStackTrace();
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de Base de Données");
            alert.setContentText("Erreur : " + e.getMessage());
            alert.showAndWait();
        } catch (Exception e) {
            System.err.println("Erreur inattendue : " + e.getMessage());
            e.printStackTrace();
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur Inattendue");
            alert.setContentText("Une erreur inattendue s'est produite : " + e.getMessage());
            alert.showAndWait();
        }
    }
    
    @FXML
    void cancel(ActionEvent event) {
        try {
            // Assurez-vous que la scène est définie dans NavigationUtil
            ensureStageIsSet();
            NavigationUtil.navigateToViewArticles();
        } catch (IOException e) {
            e.printStackTrace();
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de Navigation");
            alert.setContentText("Impossible de naviguer vers la vue des articles : " + e.getMessage());
            alert.showAndWait();
        }
    }
    
    @FXML
    void navigateToVoirArticles(ActionEvent event) {
        try {
            // Assurez-vous que la scène est définie dans NavigationUtil
            ensureStageIsSet();
            NavigationUtil.navigateToViewArticles();
        } catch (IOException e) {
            e.printStackTrace();
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de Navigation");
            alert.setContentText("Impossible de naviguer vers la vue des articles : " + e.getMessage());
            alert.showAndWait();
        }
    }
    
    private void ensureStageIsSet() {
        if (NavigationUtil.getMainStage() == null) {
            Stage currentStage = (Stage) titleTF.getScene().getWindow();
            NavigationUtil.setMainStage(currentStage);
        }
    }
    
    private void navigateToVoirArticles() {
        try {
            // Assurez-vous que la scène est définie dans NavigationUtil
            ensureStageIsSet();
            NavigationUtil.navigateToViewArticles();
        } catch (IOException e) {
            e.printStackTrace();
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de Navigation");
            alert.setContentText("Impossible de naviguer vers la vue des articles : " + e.getMessage());
            alert.showAndWait();
        }
    }
}
