package org.connect.controller.logcontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.connect.controller.maincontroller.ChatController;
import org.connect.model.entities.User;
import org.connect.model.service.user.UserRequestClient;

public class LoginController {

    @FXML private TextField userName;
    @FXML private PasswordField userPassword;
    @FXML private Button submitButton;
    @FXML private Label dataValidator;
    @FXML private Button switchToSign;

    private final UserRequestClient conn = new UserRequestClient();

    private static final String BUTTON_HOVER_STYLE = "-fx-background-color: #969de2; -fx-background-radius: 10px;";
    private static final String BUTTON_DEFAULT_STYLE = "-fx-background-color: #8379e7; -fx-background-radius: 10px;";

    private static final String BUTTON_SWITCH_DEFAULT = "-fx-background-color: #242424; -fx-text-fill: #ffffff; -fx-text-fill: #ffffff; -fx-font-size: 9px;";
    private static final String BUTTON_SWITCH_HOVER = "-fx-background-color: #242424; -fx-text-fill: #c1ff72; -fx-font-size: 9px;";

    @FXML
    public void initialize() {
        submitButton.setOnMouseEntered(e -> submitButton.setStyle(BUTTON_HOVER_STYLE));
        submitButton.setOnMouseExited(e -> submitButton.setStyle(BUTTON_DEFAULT_STYLE));

        switchToSign.setOnMouseEntered(e -> switchToSign.setStyle(BUTTON_SWITCH_HOVER));
        switchToSign.setOnMouseExited(e -> switchToSign.setStyle(BUTTON_SWITCH_DEFAULT));
    }

    @FXML
    public void submit(ActionEvent event) throws Exception {
        String name = userName.getText();
        String password = userPassword.getText();

        User user = conn.loginUserRequest(name, password);
        if (user != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/connect/view/logviews/home.fxml"));
            Parent parent = loader.load();
            ChatController controller = loader.getController();
            controller.setUser(user);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(parent);

            stage.setScene(scene);
            stage.show();

            userPassword.clear();
        } else {
            dataValidator.setStyle("-fx-text-fill: #FF0000");
            dataValidator.setText("Incorrect Name/Password");
        }
    }

    @FXML
    public void switchToSignButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/connect/view/logviews/sign.fxml"));
            Parent parent = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(parent);

            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
