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
import org.connect.model.entities.ChatRoom;
import org.connect.model.entities.User;
import org.connect.model.service.user.IUserRequest;
import org.connect.model.service.user.UserRequestClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SettingController {
    @FXML private Label userName;
    @FXML private Label userEmail;
    @FXML private Label userPassword;
    @FXML private Label activeUsers;

    @FXML private Button editName;
    @FXML private Button editEmail;
    @FXML private Button editPassword;
    @FXML private Button logout;
    @FXML private Button homeButton;

    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private TextField passwordField;

    private static final String EDIT_BUTTON_DEFAULT = "-fx-background-color: #8379e7; -fx-background-radius: 10px; -fx-text-fill: #ffffff; -fx-padding: 5px 10px;";
    private static final String EDIT_BUTTON_HOVER = "-fx-background-color: #969de2; -fx-background-radius: 10px; -fx-text-fill: #ffffff; -fx-padding: 5px 10px;";

    private static final String LOGOUT_BUTTON_DEFAULT = "-fx-background-color: #8379e7; -fx-border-radius: 5px; -fx-text-fill: #ffffff; -fx-border-width: 2px; -fx-border-color:  #c1ff72; -fx-font-size: 14px;";
    private static final String LOGOUT_BUTTON_HOVER = "-fx-background-color: #c1ff72; -fx-border-radius: 5px; -fx-text-fill: #ffffff; -fx-border-width: 2px; -fx-border-color:  #c1ff72; -fx-font-size: 14px;";

    private static final String HOME_BUTTON_DEFAULT = "-fx-background-color: transparent; -fx-border-radius: 5px; -fx-text-fill: #ffffff; -fx-border-width: 2px; -fx-border-color:  #c1ff72; -fx-font-size: 14px;";
    private static final String HOME_BUTTON_HOVER = "-fx-background-color: #c1ff72; -fx-border-radius: 5px; -fx-text-fill: #ffffff; -fx-border-width: 2px; -fx-border-color:  #c1ff72; -fx-font-size: 14px;";

    private static final Logger logger  = Logger.getLogger(SettingController.class.getName());

    @Setter
    private User user = new User();
    @Setter
    List<ChatRoom> chatRooms = new ArrayList<>();

    private final IUserRequest userRequest = new UserRequestClient();

    public void initialize() {
        userName.setText(user.getUsername());
        userEmail.setText(user.getEmail());
        userPassword.setText(user.getPassword());
        activeUsers.setText(String.valueOf(chatRooms.size()));

        editName.setStyle(EDIT_BUTTON_DEFAULT);
        editEmail.setStyle(EDIT_BUTTON_DEFAULT);
        editPassword.setStyle(EDIT_BUTTON_DEFAULT);
        logout.setStyle(LOGOUT_BUTTON_DEFAULT);

        editName.setOnMouseEntered(e -> editName.setStyle(EDIT_BUTTON_HOVER));
        editName.setOnMouseExited(e -> editName.setStyle(EDIT_BUTTON_DEFAULT));

        editEmail.setOnMouseEntered(e -> editEmail.setStyle(EDIT_BUTTON_HOVER));
        editEmail.setOnMouseExited(e -> editEmail.setStyle(EDIT_BUTTON_DEFAULT));

        editPassword.setOnMouseEntered(e -> editPassword.setStyle(EDIT_BUTTON_HOVER));
        editPassword.setOnMouseExited(e -> editPassword.setStyle(EDIT_BUTTON_DEFAULT));

        homeButton.setOnMouseEntered(e -> homeButton.setStyle(HOME_BUTTON_HOVER));
        homeButton.setOnMouseExited(e -> homeButton.setStyle(HOME_BUTTON_DEFAULT));

        logout.setOnMouseEntered(e -> logout.setStyle(LOGOUT_BUTTON_HOVER));
        logout.setOnMouseExited(e -> logout.setStyle(LOGOUT_BUTTON_DEFAULT));
    }

    @FXML
    public void editNameButton(ActionEvent event) {
        String newName = nameField.getText();
        userRequest.updateUserName(newName);
        setUser(updateUserData());
    }

    @FXML
    public void editEmailButton(ActionEvent event) {
        String newEmail = emailField.getText();
        userRequest.updateUserEmail(newEmail);
        setUser(updateUserData());
    }

    @FXML
    public void editPasswordButton(ActionEvent event) {
        String newPassword = passwordField.getText();
        userRequest.updateUserPassword(newPassword);
        setUser(updateUserData());
    }

    @FXML
    public void logoutButton(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        try {
            loader.load();
            ((Node) event.getSource()).getScene().getWindow().hide();

            Stage stage = new Stage();
            Scene scene = new Scene(loader.getRoot());
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Failed to load login.fxml");
        }
    }

    @FXML
    public void switchToHome(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/connect/view/logviews/home.fxml"));
            Parent parent = loader.load();

            ChatController controller = loader.getController();
            controller.setUser(user);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private User updateUserData() {
        try {
            return userRequest.getUserById(user.getUserId());
        } catch (Exception e) {
            return null;
        }
    }
}
