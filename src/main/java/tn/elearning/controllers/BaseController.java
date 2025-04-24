// ✅ BaseController.java
package tn.elearning.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import java.io.IOException;

public class BaseController {

    @FXML
    private StackPane contentArea;

    private static BaseController instance;

    public BaseController() {
        instance = this;
    }

    public static BaseController getInstance() {
        return instance;
    }

    public void setContent(Parent view) {
        contentArea.getChildren().setAll(view);
    }

    @FXML
    public void goAccueil() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/PageAccueil.fxml"));
            Parent view = loader.load();
            setContent(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void goCours() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/CoursView.fxml"));
            Parent view = loader.load();
            setContent(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void goQuizz() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ListeQuizzFront.fxml"));
            Parent view = loader.load();
            setContent(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void goAdd() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AddQuizzView.fxml"));
            Parent addView = loader.load();
            setContent(addView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}