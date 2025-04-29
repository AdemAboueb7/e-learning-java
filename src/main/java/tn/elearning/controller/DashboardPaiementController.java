package tn.elearning.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import tn.elearning.entities.Abonnement;
import tn.elearning.services.ServiceAbonnement;
import tn.elearning.services.ServicePaiement;

import java.sql.SQLException;
import java.util.List;

public class DashboardPaiementController {

    @FXML
    private BarChart<String, Number> barChartPaiements;

    @FXML
    private Label labelTotalAbonnements;

    @FXML
    private Label labelTotalPaiements;

    @FXML
    private PieChart pieChartGlobal;
    ServicePaiement sp=new ServicePaiement();

    public void initialize() {

        afficherStatistiques();
        remplirChartPaiements();
        remplirPieChart();
    }
    private void afficherStatistiques() {
        Double totalPaiements;
        int totalAbonnements = 0;
        try {
              totalPaiements = sp.getTotalPaiements();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            totalAbonnements=sp.getTotalAbonnements();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        labelTotalPaiements.setText(String.valueOf(totalPaiements));
        labelTotalAbonnements.setText(String.valueOf(totalAbonnements));
    }


    private void remplirChartPaiements() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Abonnements");
        ServiceAbonnement sa= new ServiceAbonnement();
        try {
            List<Abonnement> abonnements = sa.recuperer();
            for (Abonnement a : abonnements) {
                int nbPaiements = sp.getNombrePaiementsParAbonnement(a.getId());
                System.out.println("Abonnement: " + a.getType() + ", Paiements: " + nbPaiements);
                series.getData().add(new XYChart.Data<>(a.getType(), nbPaiements));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        barChartPaiements.getData().clear();
        barChartPaiements.getData().add(series);
    }


    private void remplirPieChart() {
        try {
            pieChartGlobal.getData().addAll(
                    new PieChart.Data("Abonnements", sp.getTotalAbonnements()),
                    new PieChart.Data("Paiements", sp.getTotalPaiements() )
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}

