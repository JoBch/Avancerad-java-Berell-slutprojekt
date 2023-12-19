package org.example.quizAPI;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Player extends Main {

    //Puts a username into our firebase and patches existing usernames with new scores
    public static void patchRequest(String userName, int correctAnswers) {
        String databaseUrl = "https://testjb-b8fac-default-rtdb.europe-west1.firebasedatabase.app/";
        String databasePath = "username";

        try {
            //Retrieve existing data from Firebase
            HttpClient httpClient = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_2)
                    .build();

            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(URI.create(databaseUrl + databasePath + ".json"))
                    .GET()
                    .build();

            HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());

            if (getResponse.statusCode() == 200) {
                //If data exists, update the value
                String existingData = getResponse.body();
                JsonObject json = new Gson().fromJson(existingData, JsonObject.class);

                //Get the current value for the user
                int currentValue = json.has(userName) ? json.get(userName).getAsInt() : 0;

                //Calculate the updated value
                int updatedValue = currentValue + correctAnswers;

                //Update the JSON object with the new value
                json.addProperty(userName, updatedValue);

                //Convert the updated JSON object to a string
                String jsonInputString = new Gson().toJson(json);

                //Perform PATCH request to update the value in Firebase
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(databaseUrl + databasePath + ".json"))
                        .method("PATCH", HttpRequest.BodyPublishers.ofString(jsonInputString))
                        .header("Content-Type", "application/json")
                        .build();

                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println(response);
            } else {
                System.out.println(getResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
