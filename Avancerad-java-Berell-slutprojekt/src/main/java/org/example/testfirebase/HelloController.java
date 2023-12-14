package org.example.testfirebase;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import static org.example.testfirebase.HelloApplication.question;
import static org.example.testfirebase.HelloApplication.readAPI;

public class HelloController {

    @FXML
    private Button falseButton;

    @FXML
    private Label headLineLabel;

    @FXML
    private Button nextButton;

    @FXML
    private TextArea outputTextArea;

    @FXML
    private Button trueButton;

    @FXML
    void onfalseButtonClick(ActionEvent event) {

    }

    @FXML
    void onnextButtonClick(ActionEvent event) {
        //putRequest("person.json");
        readAPI();
        outputTextArea.setText(question);
    }

    @FXML
    void ontrueButtonClick(ActionEvent event) {

    }

}

