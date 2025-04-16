package tn.elearning.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import tn.elearning.entities.Abonnement;
import tn.elearning.entities.Paiement;
import tn.elearning.entities.User;
import tn.elearning.services.ServiceAbonnement;
import tn.elearning.services.ServicePaiement;
import tn.elearning.utils.UserSession;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class cardsAbonnementController {

    ServiceAbonnement serviceAbonnement = new ServiceAbonnement();
    @FXML
    private Button paiements;

    @FXML
    private VBox cardsContainer;

    public void initialize() {
        try {

            List<Abonnement> abonnements = serviceAbonnement.recuperer();
            for (Abonnement ab : abonnements) {
                VBox card = createCard(ab);
                cardsContainer.getChildren().add(card);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private VBox createCard(Abonnement ab) {
        VBox card = new VBox(10);
        card.setStyle("-fx-border-color: #cccccc; -fx-border-width: 1; -fx-padding: 10;");
        Label typeLabel = new Label("Type: " + ab.getType());
        Label prixLabel = new Label("Prix: " + ab.getPrix());
        Label descriptionLabel = new Label("Description: " + ab.getDescription());
        Label dureeLabel = new Label("Durée: " + ab.getDuree());
        Button choisirBtn = new Button("Payer");
        choisirBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        choisirBtn.setOnAction(e -> handlePayer(ab));
        card.getChildren().addAll(typeLabel, prixLabel, descriptionLabel, dureeLabel,choisirBtn);

        return card;
    }
    private void handlePayer(Abonnement ab) {
        double montant = ab.getPrix();
        Abonnement id_abonnement = ab;
        LocalDateTime datePaiement = LocalDateTime.now();
        User user = UserSession.getInstance().getUser();
        Paiement paiement = new Paiement(montant, id_abonnement, datePaiement,user);
        ServicePaiement sp=new ServicePaiement();
        try {
            sp.ajouter(paiement);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Paiement");
            alert.setHeaderText(null);
            alert.setContentText("Paiement effectué avec succès !");
            alert.showAndWait();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void ToPaiements(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPaiements.fxml"));
            Parent root = loader.load();
            paiements.getScene().setRoot(root);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

}