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

    public double parseDouble(String value) {
        try{
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return Double.parseDouble(value + "0");
        }
    }

    public double parseBuffer(){
        // Приводим строку в буффере к double
        try {
            return parseDouble(currentBuffer);
        }
        catch (java.lang.NumberFormatException e){
            // Если возникает ошибка парсинга строки - отдаем ноль
            log.info(e.toString());
            return 0.0;
        }
    }

    public void executeLastOperation(String newOperation) {
        // Выполняем математические операции
        switch (lastOperation) {
            case "+" -> previousBufferedValue = previousBufferedValue + parseBuffer();
            case "-" -> previousBufferedValue = previousBufferedValue - parseBuffer();
            case "*" -> previousBufferedValue = previousBufferedValue * parseBuffer();
            case "/" -> previousBufferedValue = previousBufferedValue / parseBuffer();
            default -> {
                // Для обработки нажатия на "=" сразу после нажатия на любое математическое действие
                boolean b = !(Objects.equals(currentBuffer, ""));
                if (b) {
                    previousBufferedValue = parseBuffer();
                }
            }
        }
        // Обнуляем буффер для нового значения
        currentBuffer = "";
        currentInput.setText(
                currentBuffer
        );
        lastOperation = newOperation;
        log.info(String.valueOf(previousBufferedValue));
    }

    public void setCurrentInput(String value){
        // Дописываем цифры к числу в буфере
        currentBuffer = currentBuffer + value;
        // Обновляем значение в инпуте
        currentInput.setText(
                String.valueOf(parseBuffer())
        );
    }

    @FXML
    public void onKeyPressed(KeyEvent keyEvent){
        // Обрабатываем нажатия клавиш
        log.info(String.valueOf(keyEvent));

        switch (keyEvent.getCode()){
            //Для нажатия на цифры
            case DIGIT0, DIGIT1,
                    DIGIT2, DIGIT3,
                    DIGIT4, DIGIT5,
                    DIGIT6, DIGIT7,
                    DIGIT9 -> setCurrentInput(keyEvent.getText());
            // Для проверки нажатия на 8 с/без зажатия SHIFT
            case DIGIT8 -> {
                if (keyEvent.isShiftDown()){
                    executeLastOperation(keyEvent.getText());
                } else {
                    setCurrentInput(keyEvent.getText());
                }
            }
            case COMMA, PERIOD -> {
                boolean isCommaEnd = currentBuffer.endsWith(".");
                if (!isCommaEnd && !Objects.equals(currentBuffer, "")){
                    setCurrentInput(".");
                } else if (Objects.equals(currentBuffer, "")) {
                    setCurrentInput("0.");
                }
            }
            // Для математических знаков
            case PLUS, MINUS,
                    MULTIPLY, DIVIDE, SLASH -> executeLastOperation(keyEvent.getText());
            // Вызов итогов подсчета
            case EQUALS, ENTER, BACK_SPACE -> {
                executeLastOperation(keyEvent.getText());
                if (Objects.equals(keyEvent.getText(), "=") || Objects.equals(keyEvent.getText(), "\r")){
                    currentInput.setText(
                            Double.toString(previousBufferedValue)
                    );
                }
            }
            // Для нажатий на любые другие клавиши
            default -> {
                log.info("Doing nothing");
            }
        }
    }

    @FXML
    public void onNumberButtonClick(MouseEvent mouseEvent) {
        // Обрабатываем клик на цифры
        log.info(String.valueOf(mouseEvent));
        // Обновляем значение в инпуте
        setCurrentInput(
                ((Button) mouseEvent.getSource()).getText()
        );
    }
    @FXML
    public void onOperationButtonClick(MouseEvent mouseEvent) {
        // Обрабатываем нажатия на кнопки действий
        log.info(String.valueOf(mouseEvent));

        // Выполняем вызванную операцию
        String buttonText = ((Button) mouseEvent.getSource()).getText();
        executeLastOperation(buttonText);
        // Если было нажато "=" - выводим подсчитанное значение
        if (Objects.equals(buttonText, "=")){
            currentInput.setText(
                    Double.toString(previousBufferedValue)
            );
        }
    }
}