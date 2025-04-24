package tn.elearning.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.elearning.entities.quizz;
import tn.elearning.services.ServiceQuizz;

public class ModifierQuizzController {

    @FXML private TextField matiereField;
    @FXML private TextField chapitreField;
    @FXML private TextField difficulteField;
    @FXML private Label infoLabel;

    private quizz quizzToEdit;
    private final ServiceQuizz service = new ServiceQuizz();

    public void setQuizz(quizz q) {
        this.quizzToEdit = q;
        matiereField.setText(q.getMatiere());
        chapitreField.setText(String.valueOf(q.getChapitre()));
        difficulteField.setText(String.valueOf(q.getDifficulte()));
    }

    @FXML
    public void handleModifier() {
        try {
            int chapitre = Integer.parseInt(chapitreField.getText().trim());
            int difficulte = Integer.parseInt(difficulteField.getText().trim());
            String matiere = matiereField.getText().trim();

            if (matiere.isEmpty() || chapitre <= 0 || difficulte < 1 || difficulte > 5) {
                infoLabel.setText("❌ Données invalides.");
                return;
            }

            quizzToEdit.setMatiere(matiere);
            quizzToEdit.setChapitre(chapitre);
            quizzToEdit.setDifficulte(difficulte);
            service.modifier(quizzToEdit.getId(), matiere);

            infoLabel.setText("✅ Quizz modifié !");
            ((Stage) matiereField.getScene().getWindow()).close();

        } catch (NumberFormatException e) {
            infoLabel.setText("❌ Veuillez entrer des nombres valides.");
        }
    }
}