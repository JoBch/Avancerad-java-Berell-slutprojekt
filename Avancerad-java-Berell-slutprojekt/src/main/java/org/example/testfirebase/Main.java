package org.example.testfirebase;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

public class Main extends Application {
    public static HashMap<String, Object> dataMap;
    public static String question;
    public static String databaseUrl = "https://opentdb.com/api.php?amount=1";
    public static String answer;
    public Optional<String> result;

    public static void main(String[] args) {
        launch(); //Launches our JavaFX
    }

    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
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