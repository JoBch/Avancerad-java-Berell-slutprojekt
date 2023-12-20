package org.example.quizAPI;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;

import static org.example.quizAPI.Player.patchRequest;
import static org.example.quizAPI.ReadAPI.readAPI;

public class Controller extends Main implements Initializable {

    boolean stopCountDown = false;
    @FXML
    private Label countDownLabel;
    @FXML
    private Button falseButton;
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
            correctAnswers++;
            outputTextArea.appendText("\nCorrect");
        } else {
            outputTextArea.appendText("\nYOU'RE WRONG");
        }
        outputTextArea.appendText("\nClick next question");
        stopCountDown = true;
    }

    @FXML
    void onnextButtonClick(ActionEvent event) {
        nextButton.setText("Next Question");
        stopCountDown = false;
        String value = cBoxGameMode.getValue();
        try {
            if (cBoxGameMode == null) {
                System.out.println("Forgot to choose GAME MODE, Easy was chosen for you");
            }
            switch (value) {
                case "Easy":
                    databaseUrl = easy;
                    break;
                case "Medium":
                    databaseUrl = medium;
                    break;
                case "Hard":
                    databaseUrl = hard;
                    break;
                case null:
                    databaseUrl = easy;
                    System.out.println("Forgot to choose GAME MODE, Easy was chosen for you"); //denna skrivs ut vid fel
                default:
                    databaseUrl = easy;
                    System.out.println("Gick in i default");
                    break;
            }
        } catch (NullPointerException exception) {
            System.out.println(" ");
        }
        readAPI();
        countDown();
        trueButton.setDisable(false);
        falseButton.setDisable(false);
        outputTextArea.setText(question);
    }

    @FXML
    void ontrueButtonClick(ActionEvent event) {
        if (answer) {
            correctAnswers++;
            outputTextArea.appendText("\nCorrect");
        } else {
            outputTextArea.appendText("\nYOU'RE WRONG");
        }
        outputTextArea.appendText("\nClick next question");
        stopCountDown = true;
    }

    @FXML
    void onendButtonClick(ActionEvent event) {
        endRound();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cBoxGameMode.getItems().addAll(items);
        cBoxGameMode.setOnAction(event -> {
        });
    }

    public void countDown() {
        Thread countDownThread = new Thread(new Runnable() {
            @Override
            public void run() {
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

    public void getRequests(String databasePath) {
        String databaseUrl = "https://testjb-b8fac-default-rtdb.europe-west1.firebasedatabase.app/";
        try {
            //Create the URL for the HTTP GET request
            URL url = new URL(databaseUrl + databasePath);

            //Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            //Get the http response code
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                //Read the response from the InputStream
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                //Using Gson to parse the JSON string
                String jsonString = String.valueOf(response);
                JsonObject jsonObject = new Gson().fromJson(jsonString, JsonObject.class);

                //Iterating through keys and retrieving values dynamically
                for (String key : jsonObject.keySet()) {
                    JsonElement value = jsonObject.get(key);
                    outputTextArea.appendText(key + ": " + value + "\n");
                }

            } else {
                System.out.println("Error response code: " + responseCode);
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void endRound() {
        outputTextArea.clear();
        outputTextArea.setText("Highscores:\n");
        patchRequest(userName, correctAnswers);
        getRequests("username.json");
        correctAnswers = 0;
        nextButton.setText("New Game");
    }
}