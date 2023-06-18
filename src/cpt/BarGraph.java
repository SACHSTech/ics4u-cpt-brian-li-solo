package cpt;

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.*;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.math.BigInteger;

public class BarGraph {

    // Initialize private instance variables

    // Create VBoxes for bargraph
    private VBox VBoxBarGraph = new VBox(0);

    // Define axes of graph
    private CategoryAxis xAxis = new CategoryAxis();
    private NumberAxis yAxis = new NumberAxis();

    // Import data from CSV File using listData class
    private ArrayList<data> dataPoints = ListData.getDataPoints();

    // Create graph
    private BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

    // Create a list of checkboxes
    ArrayList<CheckBox> checkboxList = new ArrayList<>();

    public BarGraph() throws IOException {

        barChart.getStylesheets().add(getClass().getResource("css/bar-chart.css").toExternalForm());

        // Set labels for x and y axis
        xAxis.setLabel("Artificial Intelligence Systems");
        yAxis.setLabel("Parameters (Log Increments | 10^n)");

        barChart.setPrefWidth(1000);
        barChart.setPrefHeight(650);
        VBoxBarGraph.setPadding(new Insets(10));

        barChart.setTitle("Number of parameters in notable artificial intelligence systems");

        FlowPane container = new FlowPane(30, 10); // Set horizontal and vertical spacing between checkboxes
        container.setPrefWrapLength(415); // Set preferred width of the FlowPane (adjust as needed)
        container.setPadding(new Insets(10, 30, 30, 30));

        // For each datapoint, a new checkbox is created for it
        for (data dataPoint : dataPoints) {
            if (!dataPoint.getParameter().equals("NA")) {
                CheckBox checkbox = new CheckBox(dataPoint.getEntity());
                checkbox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue) {
                        String entity = checkbox.getText();
                        updateGraphVisibility(entity);
                    } else {
                        String entity = checkbox.getText();
                        removeDataFromBarChart(entity);
                    }
                });

                // Set each checkbox as unclicked
                checkbox.setSelected(false);
                checkboxList.add(checkbox);

            }
        }

        TextField searchField = new TextField();
        searchField.setPromptText("Seach Artificial Intelligence Systems:"); // Set a prompt text for the search field
        searchField.textProperty()
                .addListener((observable, oldValue, newValue) -> filterCheckboxes(newValue, checkboxList, container));

        // Add checkboxes to the FlowPane
        container.getChildren().addAll(checkboxList);

        ScrollPane scrollPane = new ScrollPane(container);
        scrollPane.setPrefViewportHeight(250);
        scrollPane.setFitToWidth(true); // Allow the ScrollPane to fit the width of the VBox

        // Add the bar chart and the checkbox container to the VBox
        VBoxBarGraph.getChildren().addAll(barChart, searchField, scrollPane);

    }

    /**
     * The method used to return the graph created in a VBox for the main file to
     * display
     * 
     * @author Brian Li
     */
    public VBox createGraphView() throws IOException {
        return VBoxBarGraph;
    }

    /**
     * Void method used to filter the checkboxes to see if it contains the string
     * inputted
     * 
     * @author Brian Li
     */
    private void filterCheckboxes(String searchText, List<CheckBox> checkboxes, FlowPane container) {
        container.getChildren().clear();

        // Using linear search to check every individual checkbox
        for (CheckBox checkbox : checkboxes) {
            String checkboxText = checkbox.getText();
            if (checkboxText.toLowerCase().contains(searchText.toLowerCase())) {
                container.getChildren().add(checkbox);
            }
        }
    }

    /**
     * Void method that adds data to graph based on whats selected by the checkbox
     * 
     * @param Entity The entity of the checkbox item
     * 
     * @author Brian Li
     */
    private void updateGraphVisibility(String entity) {

        // Initialize series and setting its name
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(entity);

        // Add data points for the selected entity
        for (data dataPoint : dataPoints) {
            if (dataPoint.getEntity().equals(entity)) {
                String identifier = dataPoint.getYear() + " | " + dataPoint.getEntity() + " | " + dataPoint.getDay();

                // Since the number is too big for int, set as BigInteger
                BigInteger numBig = new BigInteger(dataPoint.getParameter());

                // Find parameter on the logarithmic scale. (10^n)
                double dblParameters = Math.log10(numBig.doubleValue());

                // Add data to series
                series.getData().add(new XYChart.Data<>(identifier, dblParameters));
            }
        }

        // Add the series to the bar chart
        barChart.getData().add(series);
    }

    /**
     * Void method that REMOVES data from graph based on whats unselected by the
     * checkbox
     * 
     * @param Entity The entity of the checkbox item
     * 
     * @author Brian Li
     */
    private void removeDataFromBarChart(String entity) {

        // Initialize an instance of list of series in barchart
        ObservableList<XYChart.Series<String, Number>> seriesList = barChart.getData();

        // Iterate over series in barchart
        for (XYChart.Series<String, Number> series : seriesList) {

            // If series equal entity, remove from barchart
            if (series.getName().equals(entity)) {
                seriesList.remove(series);
                break;
            }
        }
    }

}
