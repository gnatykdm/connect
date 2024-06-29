package org.connect.controller.settings;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.Setter;
import org.connect.controller.maincontroller.ChatController;
import org.connect.model.entities.ChatRoom;
import org.connect.model.entities.User;
import org.connect.model.service.chatroom.ChatRoomRequest;
import org.connect.model.service.chatroom.IChatRoomRequest;
import org.connect.model.service.message.IMessageRequest;
import org.connect.model.service.message.MessageRequest;
import org.connect.model.service.user.IUserRequest;
import org.connect.model.service.user.UserRequestClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddUserController {
    @FXML private TextField searchField;
    @FXML private Button searchButton;
    @FXML private VBox usersBox;
    @FXML private Button homeButton;

    private final IUserRequest userRequest = new UserRequestClient();
    private final IMessageRequest messageRequest = new MessageRequest();
    private final IChatRoomRequest chatRoomRequest = new ChatRoomRequest();

    private final String SEARCH_BUTTON_DEFAULT = "-fx-background-color: #8379e7; -fx-text-fill: #ffffff; -fx-background-radius: 10px;";
    private final String SEARCH_BUTTON_HOVER = "-fx-background-color: #969de2; -fx-text-fill: #ffffff; -fx-background-radius: 10px;";

    private final String ADD_BUTTON_DEFAULT = "-fx-background-color: #8379e7; -fx-background-radius: 10px; -fx-text-fill: #ffffff; -fx-padding: 5px 10px;";
    private final String ADD_BUTTON_HOVER = "-fx-background-color: #969de2; -fx-background-radius: 10px; -fx-text-fill: #ffffff; -fx-padding: 5px 10px;";

    private final String HOME_BUTTON_DEFAULT = "-fx-background-color: transparent; -fx-border-radius: 5px; -fx-text-fill: #ffffff; -fx-border-width: 2px; -fx-border-color:  #c1ff72; -fx-font-size: 14px;";
    private final String HOME_BUTTON_HOVER = "-fx-background-color: #c1ff72; -fx-border-radius: 5px; -fx-text-fill: #ffffff; -fx-border-width: 2px; -fx-border-color:  #c1ff72; -fx-font-size: 14px;";

    @Setter
    private List<ChatRoom> chatRooms = new ArrayList<>();

    @Setter
    private User user = new User();

    public void initialize() {
        searchButton.setStyle(SEARCH_BUTTON_DEFAULT);
        searchButton.setOnMouseEntered(e -> searchButton.setStyle(SEARCH_BUTTON_HOVER));
        searchButton.setOnMouseExited(e -> searchButton.setStyle(SEARCH_BUTTON_DEFAULT));

        homeButton.setText("Home");
        homeButton.setStyle(HOME_BUTTON_DEFAULT);
        homeButton.setOnMouseEntered(e -> homeButton.setStyle(HOME_BUTTON_HOVER));
        homeButton.setOnMouseExited(e -> homeButton.setStyle(HOME_BUTTON_DEFAULT));
    }

    @FXML
    private void searchUsers(ActionEvent event) {
        clearUserBox();
        String search = searchField.getText().trim();

        if (search.isEmpty()) {
            displayMessage("Please enter a username", javafx.scene.paint.Color.WHITE);
            return;
        }

        if (search.equals(user.getUsername())) {
            displayMessage("You cannot add yourself", javafx.scene.paint.Color.WHITE);
            return;
        }

        try {
            User getUser = userRequest.getUserByUsername(search);
            if (getUser != null) {
                HBox userBox = createUserBox(getUser);
                usersBox.getChildren().add(userBox);
            } else {
                displayMessage("No user found", javafx.scene.paint.Color.WHITE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            displayMessage("An error occurred while searching for users", javafx.scene.paint.Color.RED);
        }
    }

    private void displayMessage(String message, javafx.scene.paint.Color color) {
        Label label = new Label(message);
        label.setTextFill(color);
        label.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");
        usersBox.getChildren().add(label);
    }

    private HBox createUserBox(User getUser) {
        Button addButton = new Button("Add");
        addButton.setStyle("-fx-background-color: #8379e7; -fx-background-radius: 10px; -fx-text-fill: #ffffff; -fx-padding: 5px 10px;");

        addButton.setOnMouseEntered(e -> addButton.setStyle(ADD_BUTTON_HOVER));
        addButton.setOnMouseExited(e -> addButton.setStyle(ADD_BUTTON_DEFAULT));

        addButton.setOnAction(e -> {
            try {
                chatRoomRequest.createChatRoom(user.getUserId(), getUser.getUserId());
                displayMessage("User already added", javafx.scene.paint.Color.WHITE);
            } catch (Exception ex) {
                ex.printStackTrace();
                displayMessage("Failed to add user", javafx.scene.paint.Color.RED);
            }
        });

        Label userLabel = new Label(getUser.getUsername());
        userLabel.setTextFill(javafx.scene.paint.Color.WHITE);
        userLabel.setStyle("-fx-font-size: 14px;");

        HBox userBox = new HBox(10, userLabel, addButton);
        userBox.setAlignment(Pos.CENTER_LEFT);
        userBox.setPadding(new Insets(5, 10, 5, 10));
        userBox.setStyle("-fx-background-color: #3c3c3c; -fx-background-radius: 5px; -fx-padding: 10px;");

        return userBox;
    }

    @FXML
    public void switchToHome(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/connect/view/logviews/home.fxml"));
        Parent parent = loader.load();

        ChatController controller = loader.getController();
        controller.setUser(user);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    private void clearUserBox() {
        usersBox.getChildren().clear();
    }
}
