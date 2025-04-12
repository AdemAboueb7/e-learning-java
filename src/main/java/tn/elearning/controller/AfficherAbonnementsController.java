package tn.elearning.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.elearning.entities.Abonnement;
import tn.elearning.services.ServiceAbonnement;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherAbonnementsController implements Initializable {
    ServiceAbonnement sa=new ServiceAbonnement();

    @FXML
    private TableView<Abonnement> abonnementtable;

    @FXML
    private TableColumn<Abonnement, String> desctable;

    @FXML
    private TableColumn<Abonnement, String> dureetable;

    @FXML
    private TableColumn<Abonnement, Integer> idtable;

    @FXML
    private TableColumn<Abonnement, Double> prixtable;

    @FXML
    private TableColumn<Abonnement, String> typetable;

    public void initialize(URL location, ResourceBundle resources) {
        try {
            List<Abonnement> list = sa.recuperer();

            ObservableList<Abonnement> abonnements = FXCollections.observableArrayList(list);

            // Lier les colonnes aux propriétés de l'objet Abonnement
            idtable.setCellValueFactory(new PropertyValueFactory<>("id"));
            typetable.setCellValueFactory(new PropertyValueFactory<>("type"));
            prixtable.setCellValueFactory(new PropertyValueFactory<>("prix"));
            desctable.setCellValueFactory(new PropertyValueFactory<>("description"));
            dureetable.setCellValueFactory(new PropertyValueFactory<>("duree"));

            abonnementtable.setItems(abonnements);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        }

}
