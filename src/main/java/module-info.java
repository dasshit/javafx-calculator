module com.example.calculator {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.logging;

    opens com.example.calculator to javafx.fxml;
    exports com.example.calculator;
}