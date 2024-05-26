package org.connect.controller.logcontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lombok.Getter;
import org.connect.model.entities.UserEntity;


public class SignController {
    @FXML
    private TextField userName;
    @FXML
    private TextField userEmail;
    @FXML
    private PasswordField userPassword;
    @FXML
    private Button submitButton;
    @FXML
    private Label dataValidator;

    @Getter
    private String name;
    @Getter
    private String email;
    @Getter
    private String password;

    private static final String BUTTON_HOVER_STYLE = "-fx-background-color: #969de2; -fx-background-radius: 10px;";
    private static final String BUTTON_DEFAULT_STYLE = "-fx-background-color: #8379e7; -fx-background-radius: 10px;";

    @FXML
    public void initialize() {
        submitButton.setOnMouseEntered(e -> submitButton.setStyle(BUTTON_HOVER_STYLE));
        submitButton.setOnMouseExited(e -> submitButton.setStyle(BUTTON_DEFAULT_STYLE));
    }

    @FXML
    public void submit(ActionEvent event) {

    }
}
