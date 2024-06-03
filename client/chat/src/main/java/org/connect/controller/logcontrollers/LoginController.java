package org.connect.controller.logcontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.connect.requests.UserRequestClient;

public class LoginController {
    @FXML
    private TextField userName;
    @FXML
    private TextField userPassword;
    @FXML
    private Button submitButton;
    @FXML
    private Label dataValidator;

    private final UserRequestClient conn = new UserRequestClient();

    private static final String BUTTON_HOVER_STYLE = "-fx-background-color: #969de2; -fx-background-radius: 10px;";
    private static final String BUTTON_DEFAULT_STYLE = "-fx-background-color: #8379e7; -fx-background-radius: 10px;";


    @FXML
    public void initialize() {
        submitButton.setOnMouseEntered(e -> submitButton.setStyle(BUTTON_HOVER_STYLE));
        submitButton.setOnMouseExited(e -> submitButton.setStyle(BUTTON_DEFAULT_STYLE));
    }

    public void submit(ActionEvent event) throws Exception {

        String name = userName.getText();
        String password = userPassword.getText();

        boolean validationResult = conn.loginUserRequest(name, password);
        if (!validationResult) {
            dataValidator.setStyle("-fx-text-fill: #00FF00");
            dataValidator.setText("success");
        }
        dataValidator.setStyle("-fx-text-fill: #FF0000");
        dataValidator.setText("Invalid username/password");
    }
}
