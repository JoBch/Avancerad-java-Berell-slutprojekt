package org.example.testfirebase;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Optional;

import static javafx.scene.image.Image.*;
import static org.example.testfirebase.HelloApplication.databaseUrl;
import static org.example.testfirebase.HelloApplication.putRequest;

public class HelloController {

    public TextArea outputTextArea;

    @FXML
    private Button start;
    @FXML
    private Button stop;
    @FXML
    private ImageView imageView;



    @FXML
    void onstartButtonClick(ActionEvent event) throws InterruptedException {
        //putRequest("person.json");
        //Countdown for start of quiz show it in
    }
    @FXML
    void onstopButtonClick(ActionEvent event) {
        outputTextArea.appendText("Because he is poopoo");
    }

}

