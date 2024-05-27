
package org.connect.controller.logcontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.connect.controller.logcontrollers.requests.UserRequestClient;
import org.connect.model.entities.User;

public class SignController {

    @FXML
    private TextField userName;
    @FXML
    private PasswordField userPassword;
    @FXML
    private Button submitButton;
    @FXML
    private Label dataValidator;

    private static final String BUTTON_HOVER_STYLE = "-fx-background-color: #969de2; -fx-background-radius: 10px;";
    private static final String BUTTON_DEFAULT_STYLE = "-fx-background-color: #8379e7; -fx-background-radius: 10px;";

    @FXML
    public void initialize() {
        submitButton.setOnMouseEntered(e -> submitButton.setStyle(BUTTON_HOVER_STYLE));
        submitButton.setOnMouseExited(e -> submitButton.setStyle(BUTTON_DEFAULT_STYLE));
    }

    @FXML
    public void submit(ActionEvent event) {
        String name = userName.getText();
        String password = userPassword.getText();

        UserRequestClient userRequestClient = new UserRequestClient();
        try {
            User user = new User();
            user.setUsername(name);
            user.setPassword(password);

            userRequestClient.sendPostRequest(user);
            dataValidator.setText("User registered successfully!");
            dataValidator.setStyle("-fx-text-fill: green;");
        } catch (Exception e) {
            dataValidator.setText("Registration failed: " + e.getMessage());
            dataValidator.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }
}
