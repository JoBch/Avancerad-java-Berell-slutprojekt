package org.example.quizAPI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    //public static HashMap<String, Integer> dataMap;
    public static String question;
    public static String databaseUrl;// = "https://opentdb.com/api.php?amount=1&type=boolean";
    public static String easy = "https://opentdb.com/api.php?amount=1&difficulty=easy&type=boolean";
    public static String medium = "https://opentdb.com/api.php?amount=1&difficulty=medium&type=boolean";
    public static String hard = "https://opentdb.com/api.php?amount=1&difficulty=hard&type=boolean";
    public static boolean answer;
    public static String userName;
    public static int correctAnswers = 0;

    public static void main(String[] args) {
        launch(); //Launches our JavaFX
    }

    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 300);
        stage.setTitle("Trivia Game");
        stage.setScene(scene);

        welcomeDialog();

        //putRequest(result + ".json"); //This might work for putting info and username in firebase
        stage.show();
    }

    public void welcomeDialog() throws InterruptedException {
        //Dialog with a confirmation request
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Text Input Dialog");
        dialog.setHeaderText("Look, a Text Input Dialog");
        dialog.setContentText("Please enter your name:");

        //Getting the response value
        userName = String.valueOf(dialog.showAndWait()).split("\\[")[1].split("\\]")[0];
        //userName.ifPresent(s -> System.out.println("Your name: " + s));
    }
}