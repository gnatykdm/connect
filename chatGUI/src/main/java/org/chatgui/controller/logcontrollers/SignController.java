package org.chatgui.controller.logcontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import lombok.Getter;
import org.chatgui.model.dao.UserDAOImpl;
import org.chatgui.model.entities.UserEntity;

import java.time.LocalDate;

public class SignController {
    @FXML
    private TextField userName;
    @FXML
    private TextField userEmail;
    @FXML
    private TextField userPassword;
    @FXML
    private Button submitButton;

    private final UserDAOImpl userDAO = new UserDAOImpl();

    @Getter
    private String name;
    @Getter
    private String email;
    @Getter
    private String password;

    @FXML
    public void initialize() {
        // Set button styles
        submitButton.setOnMouseEntered(e -> submitButton.setStyle(
                "-fx-background-color: #969de2;" +
                        "-fx-background-radius: 10px;"
        ));
        submitButton.setOnMouseExited(e -> submitButton.setStyle(
                "-fx-background-color: #8379e7;" +
                        "-fx-background-radius: 10px;"
        ));
    }

    @FXML
    public void submit(ActionEvent event) {
        name = userName.getText();
        email = userEmail.getText();
        password = userPassword.getText();

        if (name != null && !name.isEmpty() && email != null && !email.isEmpty() && password != null && !password.isEmpty()) {
            System.out.println("-------------------------------");
            System.out.println("Registering user: " + name + " " + email + " " + password);

            UserEntity user = new UserEntity(name, password, email, LocalDate.now());
            userDAO.register(user);
        } else {
            System.out.println("-------------------------------");
            System.out.println("Register Data is null");
        }
    }

    private void setTextFieldStyle(TextField textField) {
        textField.setStyle("-fx-text-fill: black;");
    }
}
