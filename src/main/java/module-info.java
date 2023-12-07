module application.savingscalculator {
    requires javafx.controls;
    requires javafx.fxml;


    opens application.savingscalculator to javafx.fxml;
    exports application.savingscalculator;
}