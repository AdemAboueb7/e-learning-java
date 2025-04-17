package tn.elearning.controller.Module;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import tn.elearning.entities.Module;
import tn.elearning.services.ModuleService;

import java.sql.SQLException;

public class AjouterModuleController {

    @FXML
    private Button ajouterbouton;

    @FXML
    private TextField descriptionfield;

    @FXML
    private TextField nomField;
    private final ModuleService moduleService = new ModuleService();

    @FXML
    private Label errorLabel;

    @FXML
    private void handleAjouterModule() {
        String nom = nomField.getText().trim();
        String description = descriptionfield.getText().trim();

        if (nom.isEmpty()) {
            showAlert("Erreur", "Le champ nom est obligatoire !");
            return;
        }

        try {
            Module module = new Module();
            module.setNom(nom);
            module.setDescription(description);

            moduleService.ajouter(module);
            showAlert("Succès", "Module ajouté avec succès !");
        } catch (Exception e) {
            showAlert("Erreur", "Erreur lors de l'ajout : " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }

    @FXML
    public void ajoutermodule(ActionEvent event) {
        String nom = nomField.getText();
        String description = descriptionfield.getText();

        // Réinitialiser le message d'erreur avant chaque tentative d'ajout
        errorLabel.setVisible(false);
        errorLabel.setText("");

        // Vérifier si le nom du module est vide
        if (nom.isEmpty()) {
            errorLabel.setText("Le nom du module est requis !");
            errorLabel.setVisible(true);
            return;
        }

        // Vérifier si la description est vide
        if (description.isEmpty()) {
            errorLabel.setText("La description du module est requise !");
            errorLabel.setVisible(true);
            return;
        }

        // Vérifier la longueur du nom (par exemple, au moins 3 caractères)
        if (nom.length() < 3) {
            errorLabel.setText("Le nom du module doit comporter au moins 3 caractères !");
            errorLabel.setVisible(true);
            return;
        }

        // Vérifier la longueur de la description (par exemple, au moins 10 caractères)
        if (description.length() < 10) {
            errorLabel.setText("La description doit comporter au moins 10 caractères !");
            errorLabel.setVisible(true);
            return;
        }

        // Si tout est valide, procéder à l'ajout du module
        ModuleService moduleService = new ModuleService();
        moduleService.ajouterModule(nom, description);

        // Afficher un message de succès ou rediriger l'utilisateur
        System.out.println("Module ajouté avec succès!");

        // Optionnel: Fermer ou rediriger l'utilisateur vers une autre page après l'ajout
    }
}






