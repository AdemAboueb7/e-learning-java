package tn.elearning.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import tn.elearning.entities.Abonnement;
import tn.elearning.services.ServiceAbonnement;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherAbonnementsController implements Initializable {
    ServiceAbonnement sa=new ServiceAbonnement();



    @FXML
    private Button ajouterabo;

    @FXML
    private TextArea descab;

    @FXML
    private TextField dureeab;

    @FXML
    private TextField idab;

    @FXML
    private TextField prixab;

    @FXML
    private TextField typeab;

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
    @FXML
    void abonnementSelect(MouseEvent event) {
        Abonnement selected = abonnementtable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            idab.setText(String.valueOf(selected.getId()));
            typeab.setText(selected.getType());
            prixab.setText(String.valueOf(selected.getPrix()));
            descab.setText(selected.getDescription());
            dureeab.setText(selected.getDuree());
        }

    }
    @FXML
    void modifier(MouseEvent event) {
        Abonnement selected = abonnementtable.getSelectionModel().getSelectedItem();

        if (selected != null) {
            try {
                selected.setType(typeab.getText());
                selected.setPrix(Double.parseDouble(prixab.getText()));
                selected.setDescription(descab.getText());
                selected.setDuree(dureeab.getText());
                sa.modifier(selected);
                abonnementtable.refresh();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void AllerVersAjout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterAbonnement.fxml"));
            Parent root = loader.load();
            descab.getScene().setRoot(root);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }


            @FXML
            void supprimer (MouseEvent event){
                Abonnement selected = abonnementtable.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    try {
                        sa.supprimer(selected);
                        abonnementtable.getItems().remove(selected);
                        idab.clear();
                        typeab.clear();
                        prixab.clear();
                        descab.clear();
                        dureeab.clear();
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                        ;
                    }
                } else {
                    System.out.println("Aucun abonnement sélectionné.");
                }

            }

        }
