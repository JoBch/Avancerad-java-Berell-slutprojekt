package org.example.testfirebase;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Optional;

public class HelloApplication extends Application {
    public static TextArea outputTextArea;

    public static HashMap<String, Object> dataMap;
    static String databaseUrl = "https://opentdb.com/api.php?amount=1";

    public static void main(String[] args) throws IOException {
        /*URL url = new URL("https://opentdb.com/api.php?amount=1");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("accept", "application/json");

        InputStream responseStream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(responseStream));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        //JsonParser parse = new JsonParser();
        JsonParser parser = null;
        JsonObject root = parser.parse(response.toString()).getAsJsonObject();
        System.out.println(root.get("fact").getAsString());*/

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

    public static void readAPI (){
        String databaseUrl = "https://opentdb.com/api.php?amount=1&difficulty=easy&type=boolean";
        String databasePath = "";
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

               /* Scanner sc = new Scanner(String.valueOf(response));
                String page = ""; //som CSV, läser första raden för att få fram värde till headers i kolumnerna
                while (sc.hasNext()) {
                    String line2 = sc.nextLine();
                    String[] array = line2.split(",");
                    System.out.println(line2);
                    page += line2;
                    System.out.println(line2);
                }
                *//*if (sc.hasNext()) { //Läser första raden i filen för att få rubriker till headers
                    String headerLine = sc.nextLine();
                    String[] columnHeaders = headerLine.split(",");
                    System.out.println(Arrays.toString(columnHeaders));
                }*/



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
    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 300);
        stage.setTitle("Welcome to our Trivia game!");
        stage.setScene(scene);



        //Dialog with a confirmation request - use to input playername and start game after countdown 5 sec?
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Text Input Dialog");
        dialog.setHeaderText("Look, a Text Input Dialog");
        dialog.setContentText("Please enter your name:");

        // Traditional way to get the response value.
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                Thread.sleep(2000);//program pauses before continuing
                System.out.println("Your name: " + result.get());
            }
        stage.show();
    }
}