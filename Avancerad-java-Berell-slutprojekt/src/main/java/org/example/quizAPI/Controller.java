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

//import static org.example.quizAPI.Player.getRequests;
import static org.example.quizAPI.Player.patchRequest;
import static org.example.quizAPI.ReadAPI.readAPI;

public class Controller extends Main implements Initializable {

    public String[] items;
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
            correctAnswers++;
            outputTextArea.appendText("\nCorrect");
        } else {
            outputTextArea.appendText("\nLOL FEL");
        }
        outputTextArea.appendText("\nClick next question");
        stopCountDown = true;
    }

    @FXML
    void onnextButtonClick(ActionEvent event) {
        nextButton.setText("Next Question");
        stopCountDown = false;
        //patchRequest(userName, correctAnswers);
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
            outputTextArea.appendText("\nLOL FEL");
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
        items = new String[]{"Easy", "Medium", "Hard"};
        cBoxGameMode.getItems().addAll(items);

        cBoxGameMode.setOnAction(event -> {

            if (cBoxGameMode.toString().equals(items[0])) {
                databaseUrl = easy;
                System.out.println(databaseUrl + items[0]);
            } else if (cBoxGameMode.toString().equals(items[1])) {
                databaseUrl = medium;
                System.out.println(databaseUrl + items[1]);
            } else if (cBoxGameMode.toString().equals(items[2])) {
                databaseUrl = hard;
                System.out.println(databaseUrl + items[2]);
            } else {
                databaseUrl = "https://opentdb.com/api.php?amount=1&difficulty=easy&type=boolean";
                System.out.println("Något gick fel");
            }
        });
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



    public void getRequests(String databasePath) {
        String databaseUrl = "https://testjb-b8fac-default-rtdb.europe-west1.firebasedatabase.app/";

        try {

            //Create the URL for the HTTP GET request
            URL url = new URL(databaseUrl + databasePath);

            //Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //Set the request method to GET
            connection.setRequestMethod("GET");

            //Get the response code t.ex 400, 404, 200 är ok
            int responseCode = connection.getResponseCode();
            // System.out.println("response code:" +responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) { // ok är bra
                //Read the response from the InputStream
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                //Handle the response data
                System.out.println("Response from Firebase Realtime Database:" + response);

                String jsonString = String.valueOf(response);

                //Using Gson to parse the JSON string
                JsonObject jsonObject = new Gson().fromJson(jsonString, JsonObject.class);

                //Iterating through keys and retrieving values dynamically
                for (String key : jsonObject.keySet()) {
                    JsonElement value = jsonObject.get(key);
                    System.out.println(key + ": " + value);
                    outputTextArea.appendText(key + ": " + value + "\n");
                }

            } else {
                System.out.println("Error response code: " + responseCode);
            }

            // Close the connection
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

