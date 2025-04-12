package tn.elearning.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.elearning.entities.Article;
import tn.elearning.services.ArticleService;
import tn.elearning.utils.NavigationUtil;

import java.io.IOException;
import java.sql.SQLException;

public class AjouterArticleController {

    private final ArticleService as = new ArticleService();

    @FXML
    private TextField categoryTF;

    @FXML
    private TextArea contentTF;

    @FXML
    private TextField titleTF;

    @FXML
    void ajouter(ActionEvent event) {
        try {
            // Basic validation
            if (titleTF.getText().isEmpty() || contentTF.getText().isEmpty() || categoryTF.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Validation Error");
                alert.setContentText("All fields must be filled");
                alert.showAndWait();
                return;
            }

            // Create and populate article
            Article article = new Article(categoryTF.getText(), contentTF.getText(), titleTF.getText());
            article.setUserId(1); // Default user ID
            article.setImage("default.jpg"); // Default image
            
            System.out.println("Adding article: " + article);
            
            // Add to database
            as.ajouter(article);
            
            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Article successfully added with ID: " + article.getId());
            alert.showAndWait();
            
            // Navigate back to view articles
            navigateToVoirArticles();
            
        } catch (SQLException e) {
            System.err.println("Error adding article: " + e.getMessage());
            e.printStackTrace();
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setContentText("Error: " + e.getMessage());
            alert.showAndWait();
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Unexpected Error");
            alert.setContentText("An unexpected error occurred: " + e.getMessage());
            alert.showAndWait();
        }
    }
    
    @FXML
    void cancel(ActionEvent event) {
        try {
            // Make sure the stage is set in NavigationUtil
            ensureStageIsSet();
            NavigationUtil.navigateToViewArticles();
        } catch (IOException e) {
            e.printStackTrace();
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Navigation Error");
            alert.setContentText("Could not navigate to the articles view: " + e.getMessage());
            alert.showAndWait();
        }
    }
    
    @FXML
    void navigateToVoirArticles(ActionEvent event) {
        try {
            // Make sure the stage is set in NavigationUtil
            ensureStageIsSet();
            NavigationUtil.navigateToViewArticles();
        } catch (IOException e) {
            e.printStackTrace();
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Navigation Error");
            alert.setContentText("Could not navigate to the articles view: " + e.getMessage());
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
            // Make sure the stage is set in NavigationUtil
            ensureStageIsSet();
            NavigationUtil.navigateToViewArticles();
        } catch (IOException e) {
            e.printStackTrace();
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Navigation Error");
            alert.setContentText("Could not navigate to the articles view: " + e.getMessage());
            alert.showAndWait();
        }
    }

    private void navigateToArticleManager() {
        try {
            NavigationUtil.navigateToArticleManager();
        } catch (IOException e) {
            e.printStackTrace();
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Navigation Error");
            alert.setContentText("Could not navigate to article manager: " + e.getMessage());
            alert.showAndWait();
        }
    }
}
