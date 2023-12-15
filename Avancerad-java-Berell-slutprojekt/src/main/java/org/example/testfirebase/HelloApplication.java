package org.example.testfirebase;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Optional;

public class HelloApplication extends Application {
    public static HashMap<String, Object> dataMap;
    public static String question;
    static String databaseUrl = "https://opentdb.com/api.php?amount=1";
    private static String answer;
    public Optional<String> result;

    public static void main(String[] args) throws IOException {
        //firebaseRequests("person.json"); //Om vi ska använda firebase så ändra dessa
        //putRequest("person.json");   //Om vi ska använda firebase så ändra dessa
        readAPI();
        launch(); //Launches our JavaFX
    }

    //Requests data from the supplied firebase URL and prints it in console
    public static void firebaseRequests(String databasePath) {
        String databaseUrl = "https://testjb-b8fac-default-rtdb.europe-west1.firebasedatabase.app/";

        try {
            // Create the URL for the HTTP GET request
            URL url = new URL(databaseUrl + databasePath);

            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the request method to GET
            connection.setRequestMethod("GET");

            // Get the response code t.ex 400, 404, 200 är ok
            int responseCode = connection.getResponseCode();
            System.out.println("response code:" + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) { // ok är bra
                // Read the response from the InputStream
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Handle the response data
                System.out.println("Response from Firebase Realtime Database:");
                System.out.println(response);
            } else { //404 403 402 etc error koder
                // Handle the error response
                System.out.println("Error response code: " + responseCode);
            }

            // Close the connection
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Puts data in firebase by the help of the dataMap, maybe get this to work with JavaFX?
    public static void putRequest(String databasePath) {

        try {
            URL url = new URL(databaseUrl + databasePath);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("PUT");

            connection.setDoOutput(true);

            connection.setRequestProperty("Content-Type", "application/json"); //typen

            dataMap = new HashMap<>();
            dataMap.put("name", "Joel");
            dataMap.put("age", 28);
            dataMap.put("sex", "male");
            dataMap.put("last", "Bech");
            dataMap.put("korvar", "ja");
            //dataMap.put(String.valueOf(scan));

            String jsonInputString = new Gson().toJson(dataMap);
            //String jsonInputString = "{\"name\": \"Alrik\"}";

            // Write the data to the output stream
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Get the response code
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK)
                System.out.println("PUT request successful");
            else
                System.out.println("Error response code: " + responseCode);


            // Close the connection
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void readAPI() {
        String databaseUrl = "https://opentdb.com/api.php?amount=1&difficulty=easy&type=boolean";
        String databasePath = "";
        try {
            //Create the URL for the HTTP GET request
            URL url = new URL(databaseUrl + databasePath);

            //Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //Set the request method to GET
            connection.setRequestMethod("GET");

            //Get the response code t.ex 400, 404, 200 är ok
            int responseCode = connection.getResponseCode();
            System.out.println("response code:" + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) { // ok är bra
                //Read the response from the InputStream
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                //Parsing our API
                JsonParser parser = new JsonParser();
                JsonObject root = parser.parse(response.toString()).getAsJsonObject();

                //Making the response from API into a jsonarray named resultsArray, so we can later extract what we need from the array
                JsonArray resultsArray = root.getAsJsonArray("results");
                //If results array exists and has a size bigger than 0 - mostly error handling
                if (resultsArray != null && !resultsArray.isEmpty()) {
                    //Retrieve the first element from the results array
                    JsonObject firstResult = resultsArray.get(0).getAsJsonObject();
                    //Get the value associated with the "question" key from the first result
                    question = firstResult.get("question").getAsString();
                    //To be able to display " and ' correctly display them
                    question = question.replaceAll("&quot;", "\"").replaceAll("&#039;", "'")
                            .replaceAll("&eacute;", "é");
                    //Get the value from correct_answer from the first result
                    answer = firstResult.get("correct_answer").getAsString();
                    System.out.println("Question: " + question);
                    System.out.println(answer);
                } else {
                    System.out.println("No results found in the response.");
                }

                //Printing the full response form the API in console to know what it looks like
                System.out.println(response);

            } else { //404 403 402 etc error koder
                // Handle the error response
                System.out.println("Error response code: " + responseCode);
            }

            // Close the connection
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 300);
        stage.setTitle("Trivia Game");
        stage.setScene(scene);

        //Dialog with a confirmation request - use to input player name and start game after countdown 5 sec?
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Text Input Dialog");
        dialog.setHeaderText("Look, a Text Input Dialog");
        dialog.setContentText("Please enter your name:");

        // Traditional way to get the response value.
        result = dialog.showAndWait();
        if (result.isPresent()) {
            Thread.sleep(0);//program pauses before continuing, no sleep in testing
            System.out.println("Your name: " + result.get());
        }
        //putRequest(result + ".json"); //This might work for putting info and username in firebase
        stage.show();
    }
}