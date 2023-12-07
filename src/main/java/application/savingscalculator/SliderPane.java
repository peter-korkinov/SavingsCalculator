package application.savingscalculator;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;

public class SliderPane extends BorderPane {

    public SliderPane(String labelText, int valueLabelDecimalFormat,
                      double sliderMin, double sliderMax,
                      double sliderValue) {

        Label label = new Label(labelText);

        Slider slider = new Slider(sliderMin, sliderMax, sliderValue);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);


        Label sliderValueLabel = new Label();
        sliderValueLabel.textProperty().bind(
                Bindings.format(
                        "%." + valueLabelDecimalFormat + "f",
                        slider.valueProperty()
                )
        );

        this.setLeft(label);
        this.setCenter(slider);
        this.setRight(sliderValueLabel);

        Insets spacing = new Insets(5, 5, 5, 5);

        BorderPane.setMargin(label, spacing);
        BorderPane.setMargin(slider, spacing);
        BorderPane.setMargin(sliderValueLabel, spacing);
    }
}