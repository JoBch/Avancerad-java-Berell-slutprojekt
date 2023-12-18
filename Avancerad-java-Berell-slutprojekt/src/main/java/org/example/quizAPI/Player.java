package org.example.quizAPI;

import com.google.gson.Gson;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class Player extends Main {

    public static HashMap<String, Integer> dataMap = new HashMap<>();

    //Puts data in firebase by the help of the dataMap
    public static void putRequest(String databasePath) {

        try {
            String databaseUrl = "https://testjb-b8fac-default-rtdb.europe-west1.firebasedatabase.app/.json";
            URL url = new URL(databaseUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("PUT");

            connection.setDoOutput(true);

            connection.setRequestProperty("Content-Type", "application/json"); //typen

            dataMap.put(userName, correctAnswers);

            String jsonInputString = new Gson().toJson(dataMap);
            System.out.println(jsonInputString);

            System.out.println(dataMap); //Just for trying
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
}
