package org.example.testfirebase;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.ChoiceBoxListCell;

import java.lang.runtime.SwitchBootstraps;
import java.net.URL;
import java.util.ResourceBundle;

import static org.example.testfirebase.Main.*;
import static org.example.testfirebase.ReadAPI.readAPI;
//import static org.example.testfirebase.HelloApplication.readAPI;

public class Controller implements Initializable {

    @FXML
    private Button falseButton;

    @FXML
    private Label headLineLabel;
    @FXML
    ComboBox<String> cBoxGameMode;

    @FXML
    private Button nextButton;

    @FXML
    private TextArea outputTextArea;

    @FXML
    private Button trueButton;

    @FXML
    void onfalseButtonClick(ActionEvent event) {
        if (!answer){
            outputTextArea.appendText("\ncorrect");
        }else {
            outputTextArea.appendText("\nLOL FEL");
        }
        outputTextArea.appendText("\nclick next question");
    }

    @FXML
    void onnextButtonClick(ActionEvent event) {
        nextButton.setText("Next Question");
        //putRequest("person.json"); //Think we need it here to upload score to firebase?

        readAPI();
        outputTextArea.setText(question);
    }

    @FXML
    void ontrueButtonClick(ActionEvent event) {
        if (answer){
            outputTextArea.appendText("\ncorrect");
        }else {
            outputTextArea.appendText("\nLOL FEL");
        }
        outputTextArea.appendText("\nclick next question");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cBoxGameMode.setItems(FXCollections.observableArrayList("Easy", "Medium", "Hard"));

    }
}

