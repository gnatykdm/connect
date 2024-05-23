package org.connect.controller.logcontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lombok.Getter;
import org.connect.services.validation.ICheckUserDAO;
import org.connect.services.validation.UserCheckDAOImpl;
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
        name = userName.getText();
        pass = userPassword.getText();

        ICheckUserDAO userCheckDAO = new UserCheckDAOImpl();
        if (! (userCheckDAO.checkUserName(name) && userCheckDAO.checkUserPassword(pass))) {
            System.out.println("Incorrect username or password");
            dataValidator.setText("Incorrect username or password");
        } else {
            System.out.println("You are logining in as: " + name + " " + pass);
        }
    }
}
