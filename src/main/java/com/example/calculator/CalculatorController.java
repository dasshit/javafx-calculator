package com.example.calculator;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.Objects;
import java.util.logging.*;

public class CalculatorController {
    @FXML
    TextField currentInput;
    
    Logger log = Logger.getLogger("CalculatorController");
    String currentBuffer = "";
    double previousBufferedValue = 0.0;
    String lastOperation = "";

    public double parseBuffer(){
        try {
            return Double.parseDouble(currentBuffer);
        }
        catch (java.lang.NumberFormatException e){
            log.info(e.toString());
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
        log.info(String.valueOf(previousBufferedValue));
    }

    public void setCurrentInput(String value){

        currentBuffer = currentBuffer + value;

        currentInput.setText(
                currentBuffer
        );
    }

    @FXML
    public void onKeyPressed(KeyEvent keyEvent){

        log.info(String.valueOf(keyEvent));

        switch (keyEvent.getCode()){
            case DIGIT0, DIGIT1,
                    DIGIT2, DIGIT3,
                    DIGIT4, DIGIT5,
                    DIGIT6, DIGIT7,
                    DIGIT9 -> setCurrentInput(keyEvent.getText());
            case DIGIT8 -> {
                if (keyEvent.isShiftDown()){
                    executeLastOperation(keyEvent.getText());
                } else {
                    setCurrentInput(keyEvent.getText());
                }
            }
            case PLUS, MINUS,
                    MULTIPLY, DIVIDE, SLASH -> executeLastOperation(keyEvent.getText());
            case EQUALS, ENTER, BACK_SPACE -> {
                executeLastOperation(keyEvent.getText());
                if (Objects.equals(keyEvent.getText(), "=") || Objects.equals(keyEvent.getText(), "\r")){
                    currentInput.setText(
                            Double.toString(previousBufferedValue)
                    );
                }
            }
            default -> {
                log.info("Doing nothing");
            }
        }
    }

    @FXML
    public void onNumberButtonClick(MouseEvent mouseEvent) {
        log.info(String.valueOf(mouseEvent));

        setCurrentInput(
                ((Button) mouseEvent.getSource()).getText()
        );
    }
    @FXML
    public void onOperationButtonClick(MouseEvent mouseEvent) {
        log.info(String.valueOf(mouseEvent));

        String buttonText = ((Button) mouseEvent.getSource()).getText();
        executeLastOperation(buttonText);
        if (Objects.equals(buttonText, "=")){
            currentInput.setText(
                    Double.toString(previousBufferedValue)
            );
        }
    }
}