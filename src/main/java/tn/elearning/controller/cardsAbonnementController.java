package tn.elearning.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import tn.elearning.entities.Abonnement;
import tn.elearning.services.ServiceAbonnement;

import java.sql.SQLException;

public class cardsAbonnementController  {
ServiceAbonnement serviceAbonnement = new ServiceAbonnement();
    @FXML
    private Label description;

    @FXML
    private Label duree;

    @FXML
    private Label prix;

    @FXML
    private Label type;
    public void initialize() {
        try {
            Abonnement ab = serviceAbonnement.recupererParId(1); // ID d'exemple
            // Remplissage des labels directement dans initialize
            if (ab != null) {
                type.setText(ab.getType());
                prix.setText(String.valueOf(ab.getPrix()));
                description.setText(ab.getDescription());
                duree.setText(ab.getDuree());
            } else {
                // Si l'abonnement est null, afficher un message ou gérer autrement
                type.setText("Type indisponible");
                prix.setText("Prix indisponible");
                description.setText("Description indisponible");
                duree.setText("Durée indisponible");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Afficher un message d'erreur à l'utilisateur
        }
}}
