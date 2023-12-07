package application.savingscalculator;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


/**
 * The CalculatorView class is responsible for creating the main view of the
 * application. It is an essentially static class which exposes only one
 * method - the static getView. It creates the sliders and the chart,
 * and sets up the bindings and listeners. Adds everything to a BorderPane and
 * returns it.
 */
public final class CalculatorView {
    // Constant that determines for how many years in the future the app
    // will calculate
    private static final int YEARS_IN_FUTURE = 30;

    // The constructor is private because the class is essentially staic
    private CalculatorView() {}

    /**
     * <p>A method that assembles the main view to be displayed in the app.</p>
     *
     * <p>
     *     Creates the sliders and the chart, and sets up the bindings and
     *     listeners. Adds everything to a BorderPane and returns it.
     * </p>
     *
     * @return BorderPane - the main view to be added to the scene
     */
    public static BorderPane getView() {
        // Create the sliders
        BorderPane savingsPane = new SliderPane(
                "Monthly savings", 0,
                25.0, 250.0, 25.0);

        BorderPane interestPane = new SliderPane(
                "Yearly interest rate", 1,
                0, 10, 0);

        // Create a VBox to hold the sliders
        VBox sliders = new VBox(savingsPane, interestPane);

        // Create the axes for the chart
        NumberAxis xAxis = new NumberAxis(0, YEARS_IN_FUTURE, 1);
        NumberAxis yAxis = new NumberAxis();

        // Create the series for the chart
        XYChart.Series<Number, Number> savingsPlot = new XYChart.Series<>();
        XYChart.Series<Number, Number> savingsWithInterestPlot = new XYChart.Series<>();

        // Create the chart
        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);

        // Set up the chart
        chart.setTitle("Savings Calculator");
        chart.setLegendVisible(false);
        chart.getData().add(savingsPlot);
        chart.getData().add(savingsWithInterestPlot);

        // Get the sliders
        Slider savingsSlider = (Slider) savingsPane.getCenter();
        Slider interestSlider = (Slider) interestPane.getCenter();

        // Set up the listeners for the sliders
        savingsSlider.valueProperty().addListener(
                (observableValue, number, t1) -> plotSavings(
                        savingsSlider, interestSlider,
                        savingsPlot, savingsWithInterestPlot));

        interestSlider.valueProperty().addListener(
                (observableValue, number, t1) -> plotSavingsWithInterest(
                        savingsSlider, interestSlider,
                        savingsWithInterestPlot));

        // Create the main layout and add the sliders and chart
        BorderPane view = new BorderPane();
        view.setTop(sliders);
        view.setCenter(chart);

        // Plot the initial savings
        plotSavings(
                savingsSlider, interestSlider,
                savingsPlot, savingsWithInterestPlot);

        return view;
    }

    /**
     * Plots the accumulating savings for each year.
     * To be triggered upon moving the slider for savings.
     *
     * @param savingSlider the slider for the monthly savings
     * @param interestSlider the slider for the yearly interest rate
     * @param savingsPlot the series for the savings plot
     * @param savingsWithInterestPlot the series for the savings with
     *                                interest plot
     */
    private static void plotSavings(
            Slider savingSlider,
            Slider interestSlider,
            XYChart.Series<Number, Number> savingsPlot,
            XYChart.Series<Number, Number> savingsWithInterestPlot) {

        // Clears the data that is so far in the series
        savingsPlot.getData().clear();

        // Adding the initial zero point
        savingsPlot.getData().add(new XYChart.Data<>(0, 0));

        // Calculate and add the savings for each year
        for (int i = 1; i <= YEARS_IN_FUTURE; i++) {
            // i - the number of the current year times 12 (months in a year)
            // times value of the monthly savings contribution
            double sum = i * 12 * savingSlider.valueProperty().intValue();

            savingsPlot.getData().add(new XYChart.Data<>(i, sum));
        }

        // Updating the Savings with interest plot to account for the new value
        // of the monthly savings contribution
        plotSavingsWithInterest(
                savingSlider, interestSlider, savingsWithInterestPlot);
    }

    /**
     * Plots the accumulating savings plus interest for each year.
     * To be triggered upon moving the interest percentage slider.
     *
     * @param savingSlider the slider for the monthly savings
     * @param interestSlider the slider for the yearly interest rate
     * @param savingsWithInterestPlot the series for the savings with interest plot
     */
    private static void plotSavingsWithInterest(
            Slider savingSlider,
            Slider interestSlider,
            XYChart.Series<Number, Number> savingsWithInterestPlot) {

        // Clears the data that is so far in the series
        savingsWithInterestPlot.getData().clear();

        // Adding the initial zero point
        savingsWithInterestPlot.getData().add(
                new XYChart.Data<>(0, 0));

        // A variable to store the accumulating savings with interest
        double totalSavings = 0;

        // Calculate and add the savings with interest for each year
        for (int i = 1; i <= YEARS_IN_FUTURE; i++) {
            // Add to the total savings - 12 (months in a year)
            // times the value of the monthly savings contribution
            totalSavings += 12 * savingSlider.valueProperty().intValue();

            // Calculate the interest on the total savings and add it to them
            totalSavings +=
                    totalSavings
                            * (interestSlider.valueProperty().doubleValue() * 0.01);

            savingsWithInterestPlot.getData().add(
                    new XYChart.Data<>(i, totalSavings));
        }
    }
}
