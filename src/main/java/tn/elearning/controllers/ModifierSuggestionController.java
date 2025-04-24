package tn.elearning.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import tn.elearning.entities.Suggestion;
import tn.elearning.services.ServiceSuggestion;

import java.io.IOException;

public class ModifierSuggestionController {

    @FXML private TextField contenuField;
    @FXML private CheckBox correcteCheck;
    @FXML private Label errorLabel;

    private Suggestion selectedSuggestion;
    private final ServiceSuggestion service = new ServiceSuggestion();

    public void setSuggestion(Suggestion s) {
        this.selectedSuggestion = s;
        contenuField.setText(s.getContenu());
        correcteCheck.setSelected(Boolean.TRUE.equals(s.getEstCorrecte()));
    }

    @FXML
    public void handleModifier() {
        String texte = contenuField.getText().trim();
        if (texte.isEmpty()) {
            errorLabel.setText("❌ Le contenu ne peut pas être vide.");
            return;
        }

        selectedSuggestion.setContenu(texte);
        selectedSuggestion.setEstCorrecte(correcteCheck.isSelected());

        service.modifier(selectedSuggestion);
        errorLabel.setText("✅ Suggestion modifiée !");
    }

    @FXML
    public void handleRetour() {
        Stage stage = (Stage) contenuField.getScene().getWindow();
        stage.close();
    }
}
