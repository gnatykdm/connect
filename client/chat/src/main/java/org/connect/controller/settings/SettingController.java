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

package org.connect.controller.settings;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Setter;
import org.connect.controller.maincontroller.ChatController;
import org.connect.model.entities.User;
import org.connect.model.service.user.IUserRequest;
import org.connect.model.service.user.UserRequest;

import java.io.IOException;
import java.util.logging.Logger;

public class SettingController {
    @FXML private Label userName;
    @FXML private Label userEmail;
    @FXML private Label userPassword;

    @FXML private Button editName;
    @FXML private Button editEmail;
    @FXML private Button editPassword;
    @FXML private Button logout;
    @FXML private Button homeButton;

    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private TextField passwordField;

    private static final String EDIT_BUTTON_DEFAULT = "-fx-background-color: #8379e7; -fx-background-radius: 5px; -fx-text-fill: #ffffff;";
    private static final String EDIT_BUTTON_HOVER = "-fx-background-color: #969de2; -fx-background-radius: 5px; -fx-text-fill: #ffffff;";

    private static final String LOGOUT_BUTTON_DEFAULT = "-fx-background-color: #ff5555; -fx-background-radius: 10px; -fx-text-fill: #ffffff;";
    private static final String LOGOUT_BUTTON_HOVER = "-fx-background-color: #ff7777; -fx-background-radius: 10px; -fx-text-fill: #ffffff;";

    private static final String HOME_BUTTON_DEFAULT = "-fx-background-color: transparent; -fx-border-radius: 5px; -fx-text-fill: #ffffff; -fx-border-width: 2px; -fx-border-color:  #c1ff72;";
    private static final String HOME_BUTTON_HOVER = "-fx-background-color: #c1ff72; -fx-border-radius: 5px; -fx-text-fill: #ffffff; -fx-border-width: 2px; -fx-border-color:  #c1ff72;";

    private static final Logger logger  = Logger.getLogger(SettingController.class.getName());

    @Setter
    private User user = new User();

    private final IUserRequest userRequest = new UserRequest();

    public void initialize() {
        editName.setOnMouseEntered(event -> editName.setStyle(EDIT_BUTTON_HOVER));
        editName.setOnMouseExited(event -> editName.setStyle(EDIT_BUTTON_DEFAULT));

        editEmail.setOnMouseEntered(event -> editEmail.setStyle(EDIT_BUTTON_HOVER));
        editEmail.setOnMouseExited(event -> editEmail.setStyle(EDIT_BUTTON_DEFAULT));

        editPassword.setOnMouseEntered(event -> editPassword.setStyle(EDIT_BUTTON_HOVER));
        editPassword.setOnMouseExited(event -> editPassword.setStyle(EDIT_BUTTON_DEFAULT));

        logout.setOnMouseEntered(event -> logout.setStyle(LOGOUT_BUTTON_HOVER));
        logout.setOnMouseExited(event -> logout.setStyle(LOGOUT_BUTTON_DEFAULT));

        homeButton.setOnMouseEntered(event -> homeButton.setStyle(HOME_BUTTON_HOVER));
        homeButton.setOnMouseExited(event -> homeButton.setStyle(HOME_BUTTON_DEFAULT));
    }

    public void updateUserData() {
        userName.setText(user.getUsername() != null ? user.getUsername() : "N/A");
        userEmail.setText(user.getEmail() != null ? user.getEmail() : "N/A");
        userPassword.setText(user.getPassword() != null ? user.getPassword() : "N/A");
    }

    @FXML
    private void editNameButton(ActionEvent event) {
        String name = nameField.getText();
        if (!nameField.getText().isEmpty()) {
            user.setUsername(nameField.getText());
            userRequest.updateUserName(user.getUserId(), name);
            updateUserData();
            nameField.clear();
        }
    }

    @FXML
    private void editEmailButton(ActionEvent event) {
        String email = emailField.getText();
        if (!emailField.getText().isEmpty()) {
            user.setEmail(emailField.getText());
            userRequest.updateUserEmail(user.getUserId(), email);
            updateUserData();
            emailField.clear();
        }
    }

    @FXML
    private void editPasswordButton(ActionEvent event) {
        String password = passwordField.getText();
        if (!passwordField.getText().isEmpty()) {
            user.setPassword(passwordField.getText());
            userRequest.updateUserPassword(user.getUserId(), password);
            updateUserData();
            passwordField.clear();
        }
    }

    @FXML
    private void logoutButton(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void switchToHome(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/connect/view/logviews/home.fxml"));
            Parent root = loader.load();

            ChatController chatController = loader.getController();
            chatController.setUser(user);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            logger.severe("Failed to load ChatView.fxml: " + e.getMessage());
        }
    }
}
