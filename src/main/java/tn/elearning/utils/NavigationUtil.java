package tn.elearning.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tn.elearning.entities.Article;
import tn.elearning.entities.User;
import tn.elearning.controller.ArticleDetailController;
import tn.elearning.controller.ArticleManagerController;

import java.io.IOException;
import java.net.URL;

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

        URL fxmlUrl = NavigationUtil.class.getResource(fxmlPath);
        if (fxmlUrl == null) {
            throw new IOException("Cannot find FXML file: " + fxmlPath);
        }

        try {
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();
            Scene scene = new Scene(root);

            URL cssUrl = NavigationUtil.class.getResource("/alpha-education.css");
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
            }

            mainStage.setScene(scene);
        } catch (IOException e) {
            System.err.println("Error loading " + fxmlPath + ": " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public static void navigateToArticleDetail(Article article) throws IOException {
        if (mainStage == null) {
            throw new IllegalStateException("Main stage has not been set. Call setMainStage before navigation.");
        }

        try {
            URL fxmlUrl = NavigationUtil.class.getResource("/fxml/ArticleDetail.fxml");
            if (fxmlUrl == null) {
                throw new IOException("Cannot find ArticleDetail.fxml");
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();

            ArticleDetailController controller = loader.getController();
            controller.setArticle(article);

            Scene scene = new Scene(root);
            URL cssUrl = NavigationUtil.class.getResource("/alpha-education.css");
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
            }

            mainStage.setScene(scene);
            mainStage.setTitle(article.getTitle());
        } catch (IOException e) {
            System.err.println("Error loading ArticleDetail.fxml: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public static void navigateToArticleManager() throws IOException {
        if (mainStage == null) {
            throw new IllegalStateException("Main stage has not been set. Call setMainStage before navigation.");
        }

        try {
            URL fxmlUrl = NavigationUtil.class.getResource("/fxml/ArticleManager.fxml");
            if (fxmlUrl == null) {
                throw new IOException("Cannot find ArticleManager.fxml");
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();

            ArticleManagerController controller = loader.getController();

            Scene scene = new Scene(root);
            URL cssUrl = NavigationUtil.class.getResource("/alpha-education.css");
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
            }

            mainStage.setScene(scene);
            mainStage.setTitle("Article Manager");
            controller.refreshArticles();
        } catch (IOException e) {
            System.err.println("Error loading ArticleManager.fxml: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public static void navigateToAddArticle() throws IOException {
        navigateTo("/fxml/AjouterArticle.fxml");
        mainStage.setTitle("Add New Article");
    }

    public static void navigateToViewArticles() throws IOException {
        if (mainStage == null) {
            throw new IllegalStateException("Main stage has not been set. Call setMainStage before navigation.");
        }

        User currentUser = UserSession.getInstance().getUser();
        String fxmlPath;

        if (currentUser != null && currentUser.getRoles() != null) {
            if (currentUser.getRoles().contains("ROLE_ADMIN")) {
                fxmlPath = "/fxml/VoirArticles.fxml";
            } else if (currentUser.getRoles().contains("ROLE_ENSEIGNANT")) {
                fxmlPath = "/fxml/VoirArticlesTeacher.fxml";
            } else {
                fxmlPath = "/fxml/VoirArticlesParent.fxml";
            }
        } else {
            fxmlPath = "/fxml/VoirArticlesParent.fxml";
        }

        navigateTo(fxmlPath);
        mainStage.setTitle("Articles");
    }

    public static void navigateToCours() throws IOException {
        navigateTo("/fxml/Cours.fxml");
        mainStage.setTitle("Cours");
    }

    public static void navigateToModule() throws IOException {
        navigateTo("/fxml/Module.fxml");
        mainStage.setTitle("Modules");
    }

    public static void navigateToChapitre() throws IOException {
        navigateTo("/fxml/Chapitre.fxml");
        mainStage.setTitle("Chapitres");
    }

    public static void navigateToListeCours() throws IOException {
        navigateTo("/fxml/ListCours.fxml");
        mainStage.setTitle("Liste des cours");
    }
    public static void navigateToDashboard() throws IOException {
        navigateTo("/dashboard.fxml");
        mainStage.setTitle("Dashboard");
    }

}
