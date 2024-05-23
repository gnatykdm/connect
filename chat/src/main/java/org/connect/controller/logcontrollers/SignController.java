package org.connect.controller.logcontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lombok.Getter;
import org.connect.model.entities.UserEntity;
import org.connect.services.user.IUserService;
import org.connect.services.user.UserServiceImpl;


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
        String name = userName.getText();
        String email = userEmail.getText();
        String password = userPassword.getText();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            dataValidator.setStyle("-fx-text-fill: #ff0000");
            dataValidator.setText("All fields are required.");
            return;
        }

        IUserService userService = new UserServiceImpl();

        if (!userService.checkUserSignData(name, email)) {
            UserEntity newUser = new UserEntity(name, email, password);
            userService.connect(newUser);

            System.out.println("**User was registered to the database");
            dataValidator.setStyle("-fx-text-fill: #c1ff72");
            dataValidator.setText("Successfully registered " + name + "!");
        } else {
            dataValidator.setStyle("-fx-text-fill: #ff0000");
            dataValidator.setText("User already exists.");
        }
    }
}
