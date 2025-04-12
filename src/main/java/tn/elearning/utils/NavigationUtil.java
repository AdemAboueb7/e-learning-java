package tn.elearning.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tn.elearning.entities.Article;
import tn.elearning.controller.ArticleDetailController;
import tn.elearning.controller.ArticleManagerController;

import java.io.IOException;

public class NavigationUtil {
    
    private static Stage mainStage;
    
    public static void setMainStage(Stage stage) {
        mainStage = stage;
    }
    
    public static Stage getMainStage() {
        return mainStage;
    }
    
    public static void navigateTo(String fxmlPath) throws IOException {
        if (mainStage == null) {
            throw new IllegalStateException("Main stage has not been set. Call setMainStage before navigation.");
        }
        
        Parent root = FXMLLoader.load(NavigationUtil.class.getResource(fxmlPath));
        Scene scene = new Scene(root);
        mainStage.setScene(scene);
    }
    
    public static void navigateToArticleDetail(Article article) throws IOException {
        if (mainStage == null) {
            throw new IllegalStateException("Main stage has not been set. Call setMainStage before navigation.");
        }
        
        FXMLLoader loader = new FXMLLoader(NavigationUtil.class.getResource("/ArticleDetail.fxml"));
        Parent root = loader.load();
        
        ArticleDetailController controller = loader.getController();
        controller.setArticle(article);
        
        Scene scene = new Scene(root);
        mainStage.setScene(scene);
        mainStage.setTitle(article.getTitle());
    }
    
    public static void navigateToArticleManager() throws IOException {
        if (mainStage == null) {
            throw new IllegalStateException("Main stage has not been set. Call setMainStage before navigation.");
        }
        
        FXMLLoader loader = new FXMLLoader(NavigationUtil.class.getResource("/ArticleManager.fxml"));
        Parent root = loader.load();
        
        // Get the controller to call refreshArticles
        ArticleManagerController controller = loader.getController();
        
        Scene scene = new Scene(root);
        mainStage.setScene(scene);
        mainStage.setTitle("Article Manager");
        
        // Refresh the articles list
        controller.refreshArticles();
    }
    
    public static void navigateToAddArticle() throws IOException {
        navigateTo("/AjouterArticle.fxml");
        mainStage.setTitle("Add New Article");
    }
    
    public static void navigateToViewArticles() throws IOException {
        navigateTo("/VoirArticles.fxml");
        mainStage.setTitle("Articles");
    }
} 