package tn.elearning.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.elearning.entities.Abonnement;
import tn.elearning.entities.Paiement;
import tn.elearning.entities.User;
import tn.elearning.services.ServicePaiement;
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            User user = UserSession.getInstance().getUser();
            List<Paiement>listp=sp.recuperer();
            List<Paiement> filteredPaiements = listp.stream()
                    .filter(p -> p.getUser().getId() == user.getId())
                    .collect(Collectors.toList());
            ObservableList<Paiement> paiements = FXCollections.observableArrayList(filteredPaiements);
            idp.setCellValueFactory(new PropertyValueFactory<>("id"));
            datep.setCellValueFactory(new PropertyValueFactory<>("date"));
            montantp.setCellValueFactory(new PropertyValueFactory<>("montant"));
            abonnementp.setCellValueFactory(new PropertyValueFactory<>("idAbonnement"));
            tablep.setItems(paiements);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    ServicePaiement sp=new ServicePaiement();

    @FXML
    private TableColumn<Paiement, Integer> abonnementp;

    @FXML
    private TableColumn<Paiement, LocalDateTime> datep;

    @FXML
    private TableColumn<Paiement, Integer> idp;

    @FXML
    private TableColumn<Paiement, Double> montantp;

    @FXML
    private TableView<Paiement> tablep;

    @FXML
    void ajouterp(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cardAbonnements.fxml"));
            Parent root = loader.load();
            ajouterp.getScene().setRoot(root);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

}
