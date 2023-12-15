package org.example.testfirebase;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class Player extends Main {

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

}
