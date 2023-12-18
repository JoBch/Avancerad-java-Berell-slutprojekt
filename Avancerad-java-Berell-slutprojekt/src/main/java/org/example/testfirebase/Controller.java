package org.example.testfirebase;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

import static org.example.testfirebase.Main.answer;
import static org.example.testfirebase.Main.question;
import static org.example.testfirebase.ReadAPI.readAPI;
//import static org.example.testfirebase.HelloApplication.readAPI;

public class Controller implements Initializable {

    boolean stopCountDown = false;
    @FXML
    private Label countDownLabel;
    @FXML
    private Button falseButton;
    @FXML
    private Label headLineLabel;
    @FXML
    private ComboBox<String> cBoxGameMode;
    @FXML
    private Button nextButton;
    @FXML
    private TextArea outputTextArea;
    @FXML
    private Button trueButton;

    @FXML
    void onfalseButtonClick(ActionEvent event) {
        if (!answer) {
            outputTextArea.appendText("\ncorrect");
            //NÅGOT SOM STOPPAR NEDRÄKNINGEN
        } else {
            outputTextArea.appendText("\nLOL FEL");
            //NÅGOT SOM STOPPAR NEDRÄKNINGEN
            countDown();
        }
        outputTextArea.appendText("\nclick next question");
        stopCountDown = true;
    }

    @FXML
    void onnextButtonClick(ActionEvent event) {
        nextButton.setText("Next Question");
        //putRequest("person.json"); //Think we need it here to upload score to firebase?
        stopCountDown = false;
        readAPI();
        countDown();
        outputTextArea.setText(question);
        trueButton.setDisable(false);
        falseButton.setDisable(false);
    }

    @FXML
    void ontrueButtonClick(ActionEvent event) {
        if (answer) {
            outputTextArea.appendText("\ncorrect");
            //NÅGOT SOM STOPPAR NEDRÄKNINGEN
        } else {
            outputTextArea.appendText("\nLOL FEL");
            //NÅGOT SOM STOPPAR NEDRÄKNINGEN
        }
        outputTextArea.appendText("\nclick next question");
        stopCountDown = true;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cBoxGameMode.setItems(FXCollections.observableArrayList("Easy", "Medium", "Hard"));
    }

    public void countDown() {
        Thread countDownThread = new Thread(new Runnable() {
            @Override
            public void run() {

                /*If you're running a task on a background thread and need to update a JavaFX UI component
                 (like a label, button text, etc.), you should use Platform.runLater() to ensure that the UI updates occur
                 on the JavaFX Application Thread. This is because JavaFX UI components should only be modified from this
                 dedicated UI thread to avoid concurrency issues and ensure thread safety in the UI.*/

                for (int i = 10; i >= 0; i--) {
                    if (stopCountDown) {
                        break;
                    } else {
                        final int count = i; //Final variable to be used in the lambda expression
                        Platform.runLater(() -> countDownLabel.setText(count + " ")); //Update UI on JavaFX Application Thread
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                trueButton.setDisable(true);
                falseButton.setDisable(true);
            }
        });
        countDownThread.start();
    }
}

