package tn.elearning.controller.Module;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import tn.elearning.entities.Module;
import tn.elearning.services.ModuleService;

public class SupprimerModuleController {

    @FXML
    private Label nommoduleLabel;

    @FXML
    private Label descriptionmoduleLabel;

    @FXML
    private Button btnSupprimer;

    @FXML
    private Button btnAnnuler;

    private ModuleService moduleService = new ModuleService();
    private Module selectedModule;

    // Méthode pour initialiser le module
    public void setModule(Module module) {
        this.selectedModule = module;
        if (module != null) {
            nommoduleLabel.setText(module.getNom());
            descriptionmoduleLabel.setText(module.getDescription());
        }
    }

    @FXML
    public void supprimer(ActionEvent event) {
        if (selectedModule != null) {
            try {
                moduleService.supprimer(selectedModule);
                showAlert("Succès", "Module supprimé avec succès.");
                // Optionnel : Fermer la fenêtre ou revenir à la liste des modules
            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Erreur", "Impossible de supprimer le module.");
            }
        }
    }

    @FXML
    public void annuler(ActionEvent event) {
        // Action pour annuler, par exemple fermer la fenêtre actuelle
        System.out.println("Suppression annulée");
        // Fermer la fenêtre ou revenir à la liste des modules
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}
