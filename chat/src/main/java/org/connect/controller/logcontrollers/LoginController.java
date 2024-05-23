package org.connect.controller.logcontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lombok.Getter;

import java.io.IOException;

public class LogInfoController {
    @FXML
    private TextField userName;
    @FXML
    private TextField userPassword;
    @FXML
    private Button submitButton;
    @FXML
    private Label dataValidator;

    @Getter
    private String name;
    @Getter
    private String pass;


    @FXML
    public void initialize() {
        submitButton.setOnMouseEntered(e -> submitButton.setStyle(
                "-fx-background-color: #969de2;" +
                        "-fx-background-radius: 10px;"
        ));
        submitButton.setOnMouseExited(e -> submitButton.setStyle(
                "-fx-background-color: #8379e7;" +
                        "-fx-background-radius: 10px;"
        ));
    }

    public void submit(ActionEvent event) throws IOException {

    }
}
