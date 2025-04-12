package tn.elearning.tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tn.elearning.utils.NavigationUtil;

import java.io.IOException;

public class MainFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Configurer la fenêtre principale
        primaryStage.setWidth(1000);
        primaryStage.setHeight(700);
        primaryStage.setTitle("E-Learning Platform");
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        
        // Définir le stage dans NavigationUtil pour que toutes les vues utilisent la même fenêtre
        NavigationUtil.setMainStage(primaryStage);
        
        // Charger la vue initiale (vous pouvez choisir quelle vue afficher au démarrage)
        // Pour charger la vue "Voir Articles" au démarrage:
        NavigationUtil.navigateToViewArticles();
        // Ou pour charger la vue "Ajouter Article" au démarrage:
        // NavigationUtil.navigateToAddArticle();
        
        // Afficher la fenêtre
        primaryStage.show();
        
        // Pour déboguer - afficher un message de confirmation
        System.out.println("Application démarrée avec succès. Navigation configurée dans une seule fenêtre.");
    }
}
