package tn.elearning.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.elearning.entities.Abonnement;
import tn.elearning.entities.Paiement;
import tn.elearning.entities.User;
import tn.elearning.services.ServicePaiement;
import tn.elearning.utils.FactureGenerator;
import tn.elearning.utils.UserSession;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AfficherPaiementsController implements Initializable {

    @FXML
    private Button ajouterp;

    @FXML
    private TableColumn<Paiement, String> abonnementp;

    @FXML
    private TableColumn<Paiement, LocalDateTime> expirationp;

    @FXML
    private TableColumn<Paiement, LocalDateTime> datep;

    @FXML
    private TableColumn<Paiement, Integer> idp;

    @FXML
    private TableColumn<Paiement, Double> montantp;

    @FXML
    private TableColumn<Paiement, Void> factureCol;


    @FXML
    private TableView<Paiement> tablep;

    private final ServicePaiement sp = new ServicePaiement();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            User user = UserSession.getInstance().getUser();
            List<Paiement> listp = sp.recuperer();
            List<Paiement> filteredPaiements = listp.stream()
                    .filter(p -> p.getUser() != null && p.getUser().getId() == user.getId())
                    .collect(Collectors.toList());

            ObservableList<Paiement> paiements = FXCollections.observableArrayList(filteredPaiements);

            datep.setCellValueFactory(new PropertyValueFactory<>("date"));
            montantp.setCellValueFactory(new PropertyValueFactory<>("montant"));
            abonnementp.setCellValueFactory(cellData -> {
                Paiement paiement = cellData.getValue();
                Abonnement abonnement = paiement.getAbonnement();
                if (abonnement != null && abonnement.getType() != null) {
                    return new SimpleObjectProperty<>(abonnement.getType());  // Si 'TypeAbonnement' a une méthode getNom()
                } else {
                    return new SimpleObjectProperty<>(null);
                }
            });


            expirationp.setCellValueFactory(cellData -> {
                Paiement paiement = cellData.getValue();
                Abonnement abonnement = paiement.getAbonnement();
                if (abonnement != null && abonnement.getDuree() != null && paiement.getDate() != null) {
                    LocalDateTime dateDebut = paiement.getDate();
                    String duree = abonnement.getDuree();
                    LocalDateTime dateExpiration = calculerDateFin(dateDebut, duree);
                    System.out.println("DEBUG: dateExpiration = " + dateExpiration);
                    return new SimpleObjectProperty<>(dateExpiration);
                } else {
                    System.out.println("DEBUG: abonnement ou date est null pour paiement id=" + paiement.getId());
                    return new SimpleObjectProperty<>(null);
                }
            });
            tablep.setItems(paiements);
            factureCol.setCellFactory(col -> new TableCell<>() {
                private final Button button = new Button("Télécharger");

                {
                    button.setOnAction(event -> {
                        Paiement paiement = getTableView().getItems().get(getIndex());
                        if (paiement != null) {
                            // Générer ou ouvrir la facture
                            FactureGenerator generator = new FactureGenerator();
                            generator.generateFacture(paiement);
                            try {
                                java.awt.Desktop.getDesktop().open(new java.io.File("Facture_Paiement_" + paiement.getId() + ".pdf"));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(button);
                    }
                }
            });


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public LocalDateTime calculerDateFin(LocalDateTime dateDebut, String duree) {
        if (duree == null || dateDebut == null) {
            return null;
        }
        try {
            if (duree.contains("mois")) {
                int months = Integer.parseInt(duree.split(" ")[0]);
                return dateDebut.plusMonths(months);
            } else if (duree.contains("an")) {
                int years = Integer.parseInt(duree.split(" ")[0]);
                return dateDebut.plusYears(years);
            }
        } catch (NumberFormatException e) {
            System.out.println("Erreur de format de durée: " + duree);
        }
        return dateDebut;
    }

    @FXML
    void ajouterp(ActionEvent event) {
        try {
            User user = UserSession.getInstance().getUser();
            List<Paiement> listp = sp.recuperer();
            List<Paiement> filteredPaiements = listp.stream()
                    .filter(p -> p.getUser().getId() == user.getId())
                    .collect(Collectors.toList());
            if (filteredPaiements.isEmpty()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/cardAbonnements.fxml"));
                Parent root = loader.load();
                ajouterp.getScene().setRoot(root);
                return;
            }
            boolean abonnementExpire = false;
            for (Paiement paiement : filteredPaiements) {
                Abonnement abonnement = paiement.getAbonnement();
                String duree = abonnement.getDuree();
                LocalDateTime dateDebut = paiement.getDate();
                LocalDateTime dateExpiration = calculerDateFin(dateDebut, duree);
                if (dateExpiration.isBefore(LocalDateTime.now())) {
                    abonnementExpire = true;
                    break;
                }
            }
            if (abonnementExpire) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/cardAbonnements.fxml"));
                Parent root = loader.load();
                ajouterp.getScene().setRoot(root);
            } else {
                afficherAlerte("Votre abonnement n'est pas expiré", "Vous ne pouvez pas ajouter de paiement tant que votre abonnement n'est pas expiré.");
            }

        } catch (IOException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    private void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
