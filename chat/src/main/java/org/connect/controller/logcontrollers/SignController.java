package org.connect.controller.logcontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.connect.model.dao.user.UserDAOImpl;
import org.connect.model.entities.UserEntity;
import org.connect.services.validation.ICheckUserDAO;
import org.connect.services.validation.UserCheckDAOImpl;

import java.io.IOException;
import java.time.LocalDate;

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

    private String name;
    private String email;
    private String password;

    private final UserDAOImpl userDAO = new UserDAOImpl();

    private Stage stage;
    private Scene scene;

    private static final String BUTTON_HOVER_STYLE = "-fx-background-color: #969de2; -fx-background-radius: 10px;";
    private static final String BUTTON_DEFAULT_STYLE = "-fx-background-color: #8379e7; -fx-background-radius: 10px;";

    @FXML
    public void initialize() {
        submitButton.setOnMouseEntered(e -> submitButton.setStyle(BUTTON_HOVER_STYLE));
        submitButton.setOnMouseExited(e -> submitButton.setStyle(BUTTON_DEFAULT_STYLE));
    }

    @FXML
    public void submit(ActionEvent event) {
        name = userName.getText();
        email = userEmail.getText();
        password = userPassword.getText();

        ICheckUserDAO checkUserDAO = new UserCheckDAOImpl();

        if (isInputValid()) {
            if (checkUserDAO.checkUserName(name) && checkUserDAO.checkUserEmail(email)) {
                System.out.println("This user already exists");
                dataValidator.setText("This user already exists");
            } else {
                try {
                    UserEntity user = new UserEntity();
                    user.setUserName(name);
                    user.setUserEmail(email);
                    user.setUserPassword(password);
                    user.setCreationDate(LocalDate.now());

                    userDAO.register(user);

                    Node source = (Node) event.getSource();
                    stage = (Stage) source.getScene().getWindow();
                    scene = new Scene(FXMLLoader.load(getClass().getResource("/org/connect/view/logview/home.fxml")));
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Register Data is null");
        }
    }

    private boolean isInputValid() {
        return name != null && !name.isEmpty() &&
                email != null && !email.isEmpty() &&
                password != null && !password.isEmpty();
    }
}
