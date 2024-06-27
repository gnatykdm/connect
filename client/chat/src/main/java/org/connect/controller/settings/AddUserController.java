package org.connect.controller.settings;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.connect.model.entities.User;
import org.connect.model.service.chatroom.ChatRoomRequest;
import org.connect.model.service.chatroom.IChatRoomRequest;
import org.connect.model.service.user.IUserRequest;
import org.connect.model.service.user.UserRequestClient;

public class AddUserController {
    @FXML private TextField searchField;
    @FXML private Button searchButton;
    @FXML private ScrollPane usersScrollPane;
    @FXML private VBox usersBox;

    IUserRequest userRequest = new UserRequestClient();
    IChatRoomRequest chatRoomRequest = new ChatRoomRequest();

    @FXML
    private void searchUsers() throws Exception {

    }

    private void clearUserBox() {
        usersBox.getChildren().clear();
    }
}
