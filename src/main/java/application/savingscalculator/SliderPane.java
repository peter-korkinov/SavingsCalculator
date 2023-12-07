package application.savingscalculator;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;


/**
 * The SliderPane class is a BorderPane that contains a label, a slider,
 * and a value label. The label text, slider range and initial value,
 * and the format of the value label are specified in the constructor.
 */
public class SliderPane extends BorderPane {

    /**
     * Constructor for SliderPane.
     *
     * @param labelText the text for the label
     * @param valueLabelDecimalFormat the number of decimal places
     *                                for the value label
     * @param sliderMin the minimum value for the slider
     * @param sliderMax the maximum value for the slider
     * @param sliderValue the initial value for the slider
     */
    public SliderPane(String labelText, int valueLabelDecimalFormat,
                      double sliderMin, double sliderMax,
                      double sliderValue) {

        // Create the label with info about the slider
        Label label = new Label(labelText);

        // Create the slider
        Slider slider = new Slider(sliderMin, sliderMax, sliderValue);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);

        // Create the label that displays the value of the slider
        // and bind it to it
        Label sliderValueLabel = new Label();
        sliderValueLabel.textProperty().bind(
                Bindings.format(
                        "%." + valueLabelDecimalFormat + "f",
                        slider.valueProperty()
                )
        );

        // Add the label, slider, and value label to the pane
        this.setLeft(label);
        this.setCenter(slider);
        this.setRight(sliderValueLabel);

        // Set the margins
        Insets spacing = new Insets(5, 5, 5, 5);

        BorderPane.setMargin(label, spacing);
        BorderPane.setMargin(slider, spacing);
        BorderPane.setMargin(sliderValueLabel, spacing);
    }
}
