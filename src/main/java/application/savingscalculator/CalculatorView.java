package application.savingscalculator;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public final class CalculatorView {
    private static final int YEARS_IN_FUTURE = 30;

    private CalculatorView() {}

    public static BorderPane getView() {
        BorderPane savingsBox = new SliderPane(
                "Monthly savings", 0,
                25.0, 250.0, 25.0);

        BorderPane interestBox = new SliderPane(
                "Yearly interest rate", 1,
                0, 10, 0);

        VBox sliders = new VBox(savingsBox, interestBox);

        NumberAxis xAxis = new NumberAxis(0, YEARS_IN_FUTURE, 1);
        NumberAxis yAxis = new NumberAxis();

        XYChart.Series<Number, Number> savingsPlot = new XYChart.Series<>();
        XYChart.Series<Number, Number> savingsWithInterestPlot = new XYChart.Series<>();

        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);

        chart.setTitle("Savings Calculator");
        chart.setLegendVisible(false);
        chart.getData().add(savingsPlot);
        chart.getData().add(savingsWithInterestPlot);

        Slider savingsSlider = (Slider) savingsBox.getCenter();
        Slider interestSlider = (Slider) interestBox.getCenter();

        savingsSlider.valueProperty().addListener(
                (observableValue, number, t1) -> plotSavings(
                        savingsSlider, interestSlider,
                        savingsPlot, savingsWithInterestPlot));

        interestSlider.valueProperty().addListener(
                (observableValue, number, t1) -> plotSavingsWithInterest(
                        savingsSlider, interestSlider,
                        savingsWithInterestPlot));

        BorderPane view = new BorderPane();
        view.setTop(sliders);
        view.setCenter(chart);

        plotSavings(
                savingsSlider, interestSlider,
                savingsPlot, savingsWithInterestPlot);

        return view;
    }

    private static void plotSavings(
            Slider savingSlider,
            Slider interestSlider,
            XYChart.Series<Number, Number> savingsPlot,
            XYChart.Series<Number, Number> savingsWithInterestPlot) {

        savingsPlot.getData().clear();

        savingsPlot.getData().add(new XYChart.Data<>(0, 0));

        for (int i = 1; i <= YEARS_IN_FUTURE; i++) {
            double sum = i * 12 * savingSlider.valueProperty().intValue();

            savingsPlot.getData().add(new XYChart.Data<>(i, sum));
        }

        plotSavingsWithInterest(
                savingSlider, interestSlider, savingsWithInterestPlot);
    }

    private static void plotSavingsWithInterest(
            Slider savingSlider,
            Slider interestSlider,
            XYChart.Series<Number, Number> savingsWithInterestPlot) {

        savingsWithInterestPlot.getData().clear();

        savingsWithInterestPlot.getData().add(
                new XYChart.Data<>(0, 0));

        double totalSavings = 0;

        for (int i = 1; i <= YEARS_IN_FUTURE; i++) {
            totalSavings += 12 * savingSlider.valueProperty().intValue();

            totalSavings +=
                    totalSavings
                            * (interestSlider.valueProperty().doubleValue() * 0.01);

            savingsWithInterestPlot.getData().add(
                    new XYChart.Data<>(i, totalSavings));
        }
    }
}