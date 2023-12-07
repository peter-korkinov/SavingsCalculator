package application.savingscalculator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class SavingsCalculatorApplication extends Application {
    private static final int VIEW_WIDTH = 740;
    private static final int VIEW_HEIGHT = 580;

    @Override
    public void start(Stage stage) {
        // Create the main view of the application
        BorderPane layout = CalculatorView.getView();

        // Create the scene and set it on the stage
        Scene scene = new Scene(layout, VIEW_WIDTH, VIEW_HEIGHT);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(SavingsCalculatorApplication.class);
    }

}