
package org.connect.controller.logcontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.connect.model.entities.User;
import org.connect.requests.UserRequestClient;

public class SignController {

    @FXML
    private TextField userName;
    @FXML
    private PasswordField userPassword;
    @FXML
    private TextField userEmail;
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

    @FXML
    public void submit(ActionEvent event) throws Exception {
        String name = userName.getText();
        String email = userEmail.getText();
        String password = userPassword.getText();

        User user = new User(name, email, password);
        boolean validation = conn.sendPostRequest(user);

        if (validation) {
            dataValidator.setStyle("-fx-text-fill: #00FF00");
            dataValidator.setText("success");
        }
    }
}
