package controllers;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import services.MockUserService;
import utils.SessionManager;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    private final MockUserService userService = new MockUserService(); // temporaire

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        User user = userService.authenticate(username, password);

        if (user != null) {
            SessionManager.setCurrentUser(user);

            try {
                String fxmlToLoad = user.getRole().equals("admin")
                        ? "/html/EventList.fxml"
                        : "/html/EventParticipation.fxml";

                Parent root = FXMLLoader.load(getClass().getResource(fxmlToLoad));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Bienvenue " + user.getRole());
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de connexion");
            alert.setContentText("Nom d'utilisateur ou mot de passe incorrect !");
            alert.showAndWait();
        }
    }
}
