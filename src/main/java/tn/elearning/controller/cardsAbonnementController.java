package tn.elearning.controller;

import com.mysql.cj.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.elearning.entities.Abonnement;
import tn.elearning.entities.Paiement;
import tn.elearning.entities.User;
import tn.elearning.services.ServiceAbonnement;
import tn.elearning.services.ServicePaiement;
import tn.elearning.services.StripeService;
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
        ServicePaiement sp = new ServicePaiement();

        // Crée une instance du service Stripe
        StripeService stripeService = new StripeService();

        try {
            // Crée une session de paiement Stripe pour l'abonnement
            com.stripe.model.checkout.Session stripeSession = stripeService.createCheckoutSession(ab);

            // Ouvre l'URL de la session Stripe dans le navigateur par défaut
            String checkoutUrl = stripeSession.getUrl();
            if (checkoutUrl != null) {
                java.awt.Desktop.getDesktop().browse(java.net.URI.create(checkoutUrl));
            }


            // Attendre que l'utilisateur ait le temps de payer (facultatif mais conseillé)
            Thread.sleep(50000); // Pause 15 secondes pour lui laisser le temps

            // Vérifier l'état du paiement
            com.stripe.model.checkout.Session retrievedSession = stripeService.retrieveSession(stripeSession.getId());

            if ("paid".equals(retrievedSession.getPaymentStatus())) {
                // Paiement réussi → Ajouter dans la base
                Paiement paiement = new Paiement(montant, id_abonnement, datePaiement, user);
                sp.ajouter(paiement);

                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Succès");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Paiement effectué avec succès !");
                successAlert.showAndWait();
            } else {
                // Paiement échoué ou annulé
                Alert failAlert = new Alert(Alert.AlertType.ERROR);
                failAlert.setTitle("Échec du Paiement");
                failAlert.setHeaderText(null);
                failAlert.setContentText("Le paiement n'a pas été finalisé. Veuillez réessayer.");
                failAlert.showAndWait();
            }

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de Paiement");
            alert.setHeaderText("Une erreur s'est produite");
            alert.setContentText("Erreur : " + e.getMessage());
            alert.showAndWait();
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