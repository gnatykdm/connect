package org.connect.controller.logcontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lombok.Getter;
import org.connect.services.user.IUserService;
import org.connect.services.user.UserServiceImpl;

import java.io.IOException;

public class LoginController {
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

        name = userName.getText();
        pass = userPassword.getText();

        if (name.isEmpty() || pass.isEmpty()) {
            dataValidator.setStyle("-fx-text-fill: #ff0000");
            dataValidator.setText("All fields are required.");
        }

        IUserService userService = new UserServiceImpl();

        if (userService.checkUserSignData(name, pass)) {

            System.out.println("**Welcome: " + name);
            dataValidator.setStyle("-fx-text-fill: #c1ff72");
            dataValidator.setText("Welcome back " + name + "!");
        } else {
            dataValidator.setStyle("-fx-text-fill: #ff0000");
            dataValidator.setText("Invalid data.");
        }


    }
}
