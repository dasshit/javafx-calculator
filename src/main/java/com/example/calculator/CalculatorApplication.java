package com.example.calculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import com.jthemedetecor.*;

public class CalculatorApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CalculatorApplication.class.getResource("main-scene.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 330, 390);
        final OsThemeDetector detector = OsThemeDetector.getDetector();

        if (detector.isDark()){
            scene.getStylesheets().add(
                    Objects.requireNonNull(
                            CalculatorApplication.class.getResource("main-scene.css")).toExternalForm());
        }

        stage.setTitle("Calculator");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);


        detector.registerListener(isDark -> {
            if (isDark){
                scene.getStylesheets().add(
                        Objects.requireNonNull(
                                CalculatorApplication.class.getResource("main-scene.css")).toExternalForm());
            }else{
                scene.getStylesheets().remove(0);
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}