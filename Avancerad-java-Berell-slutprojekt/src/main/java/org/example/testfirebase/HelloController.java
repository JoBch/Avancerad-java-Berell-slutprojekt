package org.example.testfirebase;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    void onstartButtonClick(ActionEvent event) {
        putRequest("person.json");
    }
    @FXML
    void onstopButtonClick(ActionEvent event) {
    }

}

