package org.example.testfirebase;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Scanner;

import static org.example.testfirebase.HelloApplication.*;

public class HelloController extends HelloApplication {

    @FXML
    public static TextField inputTextField;

    @FXML
    private Button skicka;

    @FXML
    private Label welcomeText;
    public Scanner scan;

    @FXML
    void onSkickaButtonClick(ActionEvent event) {
        putRequest("person.json");
        scan = new Scanner(System.in);
        dataMap.put(String.valueOf(scan), inputTextField);
    }
}
