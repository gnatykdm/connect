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
import org.connect.controller.ChatController;
import org.connect.model.entities.User;
import org.connect.model.requests.user.UserRequestClient;

public class LoginController {

    @FXML private TextField userName;
    @FXML private PasswordField userPassword;
    @FXML private Button submitButton;
    @FXML private Label dataValidator;

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
        String password = userPassword.getText();

        User user = conn.loginUserRequest(name, password);
        if (user != null) {
            dataValidator.setStyle("-fx-text-fill: #00FF00");
            dataValidator.setText("Success");

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
            dataValidator.setText("Invalid credentials");
        }
    }
}
