package application.savingscalculator;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;

public class SavingsCalculatorApplication extends Application {

    @Override
    public void start(Stage stage) {
        Insets slidersSpacing = new Insets(5, 5, 5, 5);

        Label savingsLabel = new Label("Monthly savings");
        Slider savingsSlider = new Slider(25, 250, 25);
        savingsSlider.setShowTickMarks(true);
        savingsSlider.setShowTickLabels(true);
        Label savingsValue = new Label();
        savingsValue.textProperty().bind(
                Bindings.format("%.0f", savingsSlider.valueProperty()));
        BorderPane savingsBox = new BorderPane();
        savingsBox.setLeft(savingsLabel);
        savingsBox.setCenter(savingsSlider);
        savingsBox.setRight(savingsValue);
        BorderPane.setMargin(savingsLabel, slidersSpacing);
        BorderPane.setMargin(savingsSlider, slidersSpacing);
        BorderPane.setMargin(savingsValue, slidersSpacing);

        Label interestLabel = new Label("Yearly Interest Rate");
        Slider interestSlider = new Slider(0, 10, 0);
        interestSlider.setShowTickMarks(true);
        interestSlider.setShowTickLabels(true);
        Label interestValue = new Label();
        interestValue.textProperty().bind(
                Bindings.format("%.1f", interestSlider.valueProperty())
        );
        BorderPane interestBox = new BorderPane();
        interestBox.setLeft(interestLabel);
        interestBox.setCenter(interestSlider);
        interestBox.setRight(interestValue);
        BorderPane.setMargin(interestLabel, slidersSpacing);
        BorderPane.setMargin(interestSlider, slidersSpacing);
        BorderPane.setMargin(interestValue, slidersSpacing);

        VBox sliders = new VBox(savingsBox, interestBox);

        NumberAxis xAxis = new NumberAxis(0, 30, 1);
        NumberAxis yAxis = new NumberAxis();

        XYChart.Series<Number, Number> savingsPlot = new XYChart.Series<>();
        XYChart.Series<Number, Number> savingsWithInterestPlot = new XYChart.Series<>();

        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);

        chart.setTitle("Savings Calculator");
        chart.setLegendVisible(false);
        chart.getData().add(savingsPlot);
        chart.getData().add(savingsWithInterestPlot);

        savingsSlider.valueProperty().addListener(
                (observableValue, number, t1) -> plotSavings(
                        savingsSlider, interestSlider,
                        savingsPlot, savingsWithInterestPlot));

        interestSlider.valueProperty().addListener(
                (observableValue, number, t1) -> plotSavingsWithInterest(
                        savingsSlider, interestSlider,
                        savingsWithInterestPlot));

        BorderPane layout = new BorderPane();
        layout.setTop(sliders);
        layout.setCenter(chart);

        plotSavings(savingsSlider, interestSlider, savingsPlot, savingsWithInterestPlot);

        Scene scene = new Scene(layout, 740, 580);
        stage.setScene(scene);
        stage.show();
    }

    private static void plotSavings (
            Slider savingSlider,
            Slider interestSlider,
            XYChart.Series < Number, Number > savingsPlot,
            XYChart.Series < Number, Number > savingsWithInterestPlot){

        savingsPlot.getData().clear();

        savingsPlot.getData().add(new XYChart.Data<>(0, 0));

        for (int i = 1; i <= 30; i++) {
            double sum = i * 12 * savingSlider.valueProperty().intValue();

            savingsPlot.getData().add(new XYChart.Data<>(i, sum));
        }

        plotSavingsWithInterest(savingSlider, interestSlider, savingsWithInterestPlot);
    }

    private static void plotSavingsWithInterest (
            Slider savingSlider,
            Slider interestSlider,
            XYChart.Series < Number, Number > savingsWithInterestPlot){

        savingsWithInterestPlot.getData().clear();

        savingsWithInterestPlot.getData().add(
                new XYChart.Data<>(0, 0));

        double sum = 0;

        for (int i = 1; i <= 30; i++) {
            sum += 12 * savingSlider.valueProperty().intValue();
            sum += sum * (interestSlider.valueProperty().doubleValue() * 0.01);

            savingsWithInterestPlot.getData().add(new XYChart.Data<>(i, sum));
        }
    }

    public static void main (String[]args){
        launch(SavingsCalculatorApplication.class);
    }
}