package tn.elearning.controller.Module;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.elearning.entities.Module;
import tn.elearning.services.ModuleService;

public class ModifierModuleController {

    @FXML
    private TextField nomField;

    @FXML
    private TextArea descriptionField;

    private Module module; // Le module à modifier

    private final ModuleService moduleService = new ModuleService();

    // Initialiser le formulaire avec les données du module sélectionné
    public void setModule(Module module) {
        this.module = module;
        nomField.setText(module.getNom());
        descriptionField.setText(module.getDescription());
    }

    // Bouton enregistrer (lié dans le FXML avec onAction="#enregistrerModifications")
    @FXML
    private void enregistrerModifications(ActionEvent event) {
        try {
            // Mise à jour de l'objet module
            module.setNom(nomField.getText());
            module.setDescription(descriptionField.getText());

            // Appel du service
            moduleService.modifier(module);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Modification");
            alert.setHeaderText(null);
            alert.setContentText("Le module a été modifié avec succès !");
            alert.showAndWait();

            // Fermer la fenêtre
            ((Stage) nomField.getScene().getWindow()).close();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Échec de la modification");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
