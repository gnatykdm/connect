/*
 * @author Gnatyk Dmytro
 * This file is part of CONNECT.
 *
 * CONNECT is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * CONNECT is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with CONNECT. If not, see <https://www.gnu.org/licenses/>.
 */

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
import org.connect.model.dto.UserDTO;
import org.connect.model.entities.User;
import org.connect.model.service.user.UserRequest;

import java.io.IOException;

public class SignController {

    @FXML private TextField userName;
    @FXML private PasswordField userPassword;
    @FXML private TextField userEmail;
    @FXML private Button submitButton;
    @FXML private Label dataValidator;
    @FXML private Button switchToLogin;

    private final UserRequest conn = new UserRequest();

    private static final String BUTTON_HOVER_STYLE = "-fx-background-color: #969de2; -fx-background-radius: 10px;";
    private static final String BUTTON_DEFAULT_STYLE = "-fx-background-color: #8379e7; -fx-background-radius: 10px;";

    private static final String HOME_BUTTON_DEFAULT_STYLE = "-fx-background-color: #242424; -fx-text-fill: #ffffff;";
    private static final String HOME_BUTTON_HOVER_STYLE = "-fx-background-color: #242424; -fx-text-fill: #c1ff72;";

    @FXML
    public void initialize() {
        submitButton.setOnMouseEntered(e -> submitButton.setStyle(BUTTON_HOVER_STYLE));
        submitButton.setOnMouseExited(e -> submitButton.setStyle(BUTTON_DEFAULT_STYLE));

        switchToLogin.setOnMouseEntered(e -> switchToLogin.setStyle(HOME_BUTTON_HOVER_STYLE));
        switchToLogin.setOnMouseExited(e -> switchToLogin.setStyle(HOME_BUTTON_DEFAULT_STYLE));
    }

    @FXML
    public void submit(ActionEvent event) throws Exception {
        String name = userName.getText();
        String email = userEmail.getText();
        String password = userPassword.getText();


        UserDTO userDTO = new UserDTO(name, email, password);
        User globalUser = conn.registerUser(userDTO);

        if (globalUser != null) {
            dataValidator.setStyle("-fx-text-fill: #00FF00");
            dataValidator.setText("Success");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/connect/view/logviews/home.fxml"));
            Parent parent = loader.load();
            ChatController controller = loader.getController();
            controller.setUser(globalUser);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(parent);

            stage.setScene(scene);
            stage.show();
        } else {
            dataValidator.setStyle("-fx-text-fill: #FF0000");
            dataValidator.setText("This user already exists");
        }
    }

    @FXML
    private void switchButton(ActionEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/org/connect/view/logviews/login.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(parent);

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
