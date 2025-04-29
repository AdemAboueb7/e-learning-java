package tn.elearning.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import tn.elearning.entities.Cours;
import tn.elearning.services.CoursDAO;
import tn.elearning.services.ChapitreDAO;
import tn.elearning.services.ModuleDAO;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DashboardController {
    @FXML private Text totalCoursesText;
    @FXML private Text totalChaptersText;
    @FXML private Text totalModulesText;

    @FXML private PieChart coursesPerModuleChart;
    @FXML private BarChart<String, Number> chaptersPerModuleChart;
    @FXML private CategoryAxis moduleAxis;
    @FXML private NumberAxis chapterCountAxis;

    @FXML private TableView<Cours> activityTable;
    @FXML private TableColumn<Cours, String> courseColumn;
    @FXML private TableColumn<Cours, String> chapterColumn;
    @FXML private TableColumn<Cours, String> dateColumn;

    private final CoursDAO coursDAO = new CoursDAO();
    private final ChapitreDAO chapitreDAO = new ChapitreDAO();
    private final ModuleDAO moduleDAO = new ModuleDAO();

    @FXML
    public void initialize() {
        try {
            loadBasicStats();
            loadCoursesPerModuleChart();
            setupChaptersPerModuleChart();
            loadRecentActivity();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadBasicStats() throws SQLException {
        totalCoursesText.setText(String.valueOf(coursDAO.getTotalCourses()));
        totalChaptersText.setText(String.valueOf(chapitreDAO.getTotalChapitres()));
        totalModulesText.setText(String.valueOf(moduleDAO.getTotalModules()));
    }

    private void loadCoursesPerModuleChart() throws SQLException {
        Map<String, Long> coursesPerModule = coursDAO.getCoursesPerModule();

        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList(
                coursesPerModule.entrySet().stream()
                        .map(entry -> new PieChart.Data(entry.getKey(), entry.getValue()))
                        .collect(Collectors.toList())
        );

        coursesPerModuleChart.setData(pieData);
        coursesPerModuleChart.setLegendVisible(false);
    }

    private void setupChaptersPerModuleChart() throws SQLException {
        // Load CSS for BarChart coloring
        URL cssUrl = getClass().getResource("/chart-styles.css");
        if (cssUrl != null) {
            chaptersPerModuleChart.getStylesheets().add(cssUrl.toExternalForm());
        } else {
            System.err.println("CSS file not found at /css/chart-styles.css");
        }

        chaptersPerModuleChart.getData().clear();
        chaptersPerModuleChart.setTitle("Chapters per Module");
        chaptersPerModuleChart.setLegendVisible(false);
        chaptersPerModuleChart.setAnimated(false);
        moduleAxis.setLabel("Modules");
        chapterCountAxis.setLabel("Number of Chapters");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Chapters");

        Map<String, Long> chaptersPerModule = chapitreDAO.getChaptersPerModule();
        chaptersPerModule.forEach((moduleName, count) ->
                series.getData().add(new XYChart.Data<>(moduleName, count))
        );

        chaptersPerModuleChart.getData().add(series);

        // Tooltips need to be installed after nodes are ready
        chaptersPerModuleChart.layout(); // Force layout to generate nodes
        for (XYChart.Data<String, Number> data : series.getData()) {
            Node node = data.getNode();
            if (node != null) {
                Tooltip tooltip = new Tooltip(
                        data.getXValue() + "\nChapters: " + data.getYValue()
                );
                Tooltip.install(node, tooltip);
            }
        }
    }

    private void loadRecentActivity() throws SQLException {
        List<Cours> recentCourses = coursDAO.getRecentCourses(5);

        courseColumn.setCellValueFactory(new PropertyValueFactory<>("titre"));
        chapterColumn.setCellValueFactory(cellData -> {
            try {
                int chapitreId = cellData.getValue().getChapitreId();
                String chapterName = chapitreDAO.getChapterNameById(chapitreId);
                return new SimpleStringProperty(chapterName);
            } catch (SQLException e) {
                return new SimpleStringProperty("N/A");
            }
        });
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));

        activityTable.setItems(FXCollections.observableArrayList(recentCourses));
    }
}
