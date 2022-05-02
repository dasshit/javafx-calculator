package com.example.calculator;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.util.Objects;

public class HelloController {
    @FXML
    TextField currentInput;

    String currentBuffer = "";
    double previousBufferedValue = 0.0;
    String lastOperation = "";

    public double parseBuffer(){
        try {
            return Double.parseDouble(currentBuffer);
        }
        catch (java.lang.NumberFormatException e){
            System.out.println(e.toString());
            return 0.0;
        }
    }

    public void executeLastOperation(String newOperation) {
        switch (lastOperation) {
            case "+" -> previousBufferedValue = previousBufferedValue + parseBuffer();
            case "-" -> previousBufferedValue = previousBufferedValue - parseBuffer();
            case "*" -> previousBufferedValue = previousBufferedValue * parseBuffer();
            case "/" -> previousBufferedValue = previousBufferedValue / parseBuffer();
            default -> {
                boolean b = !(Objects.equals(currentBuffer, ""));
                if (b) {
                    previousBufferedValue = parseBuffer();
                }
            }
        }
        currentBuffer = "";
        currentInput.setText(
                currentBuffer
        );
        lastOperation = newOperation;
        System.out.println(previousBufferedValue);
    }

    public void setCurrentInput(int value){

        currentBuffer = currentBuffer + value;

        currentInput.setText(
                currentBuffer
        );
    }

    @FXML
    public void onNumberButtonClick(MouseEvent mouseEvent) {
        System.out.println(mouseEvent);

        String buttonText = ((Button) mouseEvent.getSource()).getText();
        setCurrentInput(
                Integer.parseInt(
                        buttonText
                )
        );
    }
    @FXML
    public void onOperationButtonClick(MouseEvent mouseEvent) {
        System.out.println(mouseEvent);

        String buttonText = ((Button) mouseEvent.getSource()).getText();
        executeLastOperation(buttonText);
        if (Objects.equals(buttonText, "=")){
            currentInput.setText(
                    Double.toString(previousBufferedValue)
            );
        }
    }
}