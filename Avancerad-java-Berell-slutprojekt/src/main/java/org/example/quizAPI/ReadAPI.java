package org.example.quizAPI;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ReadAPI extends Main {

    public static void readAPI() {
        //SwitchCase setText to choose difficulty
        //databaseUrl = "https://opentdb.com/api.php?amount=1&difficulty=easy&type=boolean";

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
            System.out.println("response code API:" + responseCode);
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
                    //To be able to display special chars correctly
                    question = question.replaceAll("&quot;", "\"").replaceAll("&#039;", "'")
                            .replaceAll("&eacute;", "é").replaceAll("&rsquo;", "´");
                    //Get the value from correct_answer from the first result
                    answer = firstResult.get("correct_answer").getAsBoolean();
                    System.out.println("Question: " + question);
                    System.out.println(answer);
                } else {
                    System.out.println("No results found in the response.");
                }

            } else { //404 403 402 etc error koder
                // Handle the error response
                System.out.println("Error response code API: " + responseCode);
            }

            // Close the connection
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
