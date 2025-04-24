package tn.elearning.controller.Chapitre;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.elearning.entities.Chapitre;
import tn.elearning.entities.Module;
import tn.elearning.services.ChapitreService;
import tn.elearning.services.ModuleService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherChapitreController implements Initializable {

    @FXML
    private TextField nommodule;  // Champ pour afficher le nom du module

    @FXML
    private TextArea descriptionmodule;  // Champ pour afficher la description du module

    @FXML
    private TableView<Chapitre> chapitrestable;  // Table pour afficher les chapitres

    @FXML
    private TableColumn<Chapitre, String> nomChapitre;  // Colonne pour afficher le nom du chapitre

    @FXML
    private TableColumn<Chapitre, String> descriptionChapitre;  // Colonne pour afficher la description du chapitre

    @FXML
    private Button btnModifier;  // Bouton pour modifier le module

    @FXML
    private Button btnSupprimer;  // Bouton pour supprimer le module

    @FXML
    private Button btnAjouter;

    private ModuleService moduleService = new ModuleService();
    private ChapitreService chapitreService = new ChapitreService();
    private Module selectedModule;  // Le module actuellement sélectionné

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // Exemple d'ID de module pour la démonstration
            selectedModule = moduleService.recupererParId(1);

            // Si le module est trouvé, afficher ses informations
            if (selectedModule != null) {
                nommodule.setText(selectedModule.getNom());
                descriptionmodule.setText(selectedModule.getDescription());

                // Lier les colonnes de la TableView aux getters de Chapitre
                nomChapitre.setCellValueFactory(cellData -> {
                    return new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNom());
                });
                descriptionChapitre.setCellValueFactory(cellData -> {
                    return new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDescription());
                });

                // Récupérer les chapitres associés au module et les afficher dans la TableView
                List<Chapitre> chapitres = chapitreService.recupererParModule(selectedModule.getId());
                ObservableList<Chapitre> chapitresList = FXCollections.observableArrayList(chapitres);
                chapitrestable.setItems(chapitresList);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void modifier() {
        // Action de modification du module
        if (selectedModule != null) {
            selectedModule.setNom(nommodule.getText());
            selectedModule.setDescription(descriptionmodule.getText());
            try {
                moduleService.modifier(selectedModule);
                showAlert("Succès", "Module modifié avec succès.");
            } catch (SQLException e) {
                showAlert("Erreur", "Impossible de modifier le module.");
            }
        }
    }

    @FXML
    private void supprimer() {
        // Action de suppression du module
        if (selectedModule != null) {
            try {
                moduleService.supprimer(selectedModule);
                showAlert("Succès", "Module supprimé.");
                // Optionnel : Fermer la fenêtre ou revenir à la liste
            } catch (SQLException e) {
                showAlert("Erreur", "Impossible de supprimer le module.");
            }
        }
    }
    @FXML
    public void ajouter(ActionEvent event) {

        // Logique pour rediriger vers la page d'ajout du module
        try {
            // Charger la page d'ajout (par exemple, ajouterModule.fxml)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/path/to/ajouterModule.fxml"));
            Parent root = loader.load();

            // Obtenir la scène actuelle et la modifier pour afficher la nouvelle page
            Scene scene = new Scene(root);
            Stage stage = (Stage) btnAjouter.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}
