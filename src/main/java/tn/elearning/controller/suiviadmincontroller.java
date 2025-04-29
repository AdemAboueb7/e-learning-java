package tn.elearning.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import tn.elearning.entities.User;
import tn.elearning.services.ServiceUser;
import tn.elearning.services.ServiceMatiereProf;
import tn.elearning.entities.matiereprof;

import java.sql.SQLException;
import java.util.List;

public class suiviadmincontroller {

    @FXML private Label adminCountLabel;
    @FXML private Label teacherCountLabel;
    @FXML private Label parentCountLabel;
    @FXML private Label totalCountLabel;
    @FXML private BarChart<String, Number> experienceBarChart;

    @FXML private PieChart matierePieChart;

    private final ServiceUser serviceUser = new ServiceUser();
    private final ServiceMatiereProf serviceMatiereProf = new ServiceMatiereProf();

    public void initialize() {
        try {
            // Récupérer tous les utilisateurs pour afficher les informations
            List<User> allUsers = serviceUser.getAllUsers();

            int adminCount = 0;
            int teacherCount = 0;
            int parentCount = 0;

            for (User user : allUsers) {
                List<String> roles = user.getRoles();
                if (roles != null) {
                    if (roles.contains("ROLE_ADMIN")) {
                        adminCount++;
                    }
                    if (roles.contains("ROLE_TEACHER")) {
                        teacherCount++;
                    }
                    if (roles.contains("ROLE_PARENT")) {
                        parentCount++;
                    }
                }
            }

            adminCountLabel.setText("Admins : " + adminCount);
            teacherCountLabel.setText("Professeurs : " + teacherCount);
            parentCountLabel.setText("Parents : " + parentCount);
            totalCountLabel.setText("Utilisateurs totaux : " + allUsers.size());

            // Charger les statistiques des matières
            chargerStatistiquesParMatiere();
            chargerExperienceParPlage(allUsers);

        } catch (SQLException e) {
            adminCountLabel.setText("Erreur");
            teacherCountLabel.setText("Erreur");
            parentCountLabel.setText("Erreur");
            totalCountLabel.setText("Erreur");
            e.printStackTrace();
        }
    }
    private void chargerExperienceParPlage(List<User> users) {
        int range0_2 = 0;
        int range3_5 = 0;
        int range6_10 = 0;
        int range10plus = 0;
        int rangeNull=0;

        for (User user : users) {
            if (user.getRoles().contains("ROLE_TEACHER")) {
                System.out.println("Expérience pour " + user.getNom() + ": " + user.getExperience());
                Integer exp = user.getExperience();

                if (exp != null) {
                    if (exp <= 2) range0_2++;
                    else if (exp <= 5) range3_5++;
                    else if (exp <= 10) range6_10++;
                    else range10plus++;
                } else {
                    rangeNull++;  // Si expérience est null
                }
            }
        }

        // Ajoute maintenant les données au BarChart ici (si besoin)
        // Exemple :
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Expérience");

        series.getData().add(new XYChart.Data<>("0-2 ans", range0_2));
        series.getData().add(new XYChart.Data<>("3-5 ans", range3_5));
        series.getData().add(new XYChart.Data<>("6-10 ans", range6_10));
        series.getData().add(new XYChart.Data<>("10+ ans", range10plus));

        experienceBarChart.getData().add(series); // Assure-toi que experienceBarChart est bien défini en FXML
    }


    // Méthode pour charger les statistiques des matières dans le PieChart
    private void chargerStatistiquesParMatiere() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        try {
            // Récupérer toutes les matières en utilisant la méthode getAll() de ServiceMatiereProf
            List<matiereprof> matieres = serviceMatiereProf.getAll();

            // Pour chaque matière, récupérer le nombre d'enseignants
            for (matiereprof matiere : matieres) {
                int count = serviceUser.getTeachersCountByMatiere(matiere.getNom()); // Récupérer le nombre d'enseignants pour chaque matière
                pieChartData.add(new PieChart.Data(matiere.getNom(), count));
            }

            matierePieChart.setData(pieChartData);
            matierePieChart.setTitle("Répartition des enseignants par matière");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}




