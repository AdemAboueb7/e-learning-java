package tn.elearning.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class NavigationUtil {
    private static Stage mainStage;

    public static void setMainStage(Stage stage) {
        mainStage = stage;
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    public static void navigateToView(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(NavigationUtil.class.getResource(fxmlPath));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        mainStage.setScene(scene);
        mainStage.show();
    }
} 