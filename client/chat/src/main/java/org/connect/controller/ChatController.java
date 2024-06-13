package org.connect.controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import lombok.Setter;
import org.connect.model.entities.ChatRoom;
import org.connect.model.entities.Message;
import org.connect.model.entities.User;
import org.connect.requests.chatroom.ChatRoomRequest;
import javafx.application.Platform;
import org.connect.requests.message.IMessageRequest;
import org.connect.requests.message.MessageRequest;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatController {

    private static final Logger LOGGER = Logger.getLogger(ChatController.class.getName());

    @FXML
    private TextField messageField;

    @FXML
    private Button sendButton;

    @FXML
    private VBox messagesBox;

    @FXML
    private ScrollPane messagesScrollPane;

    @FXML
    private Label usernameLabel;

    @FXML
    private Button addButton;

    @FXML
    private VBox chatBox;

    @Setter
    private User user = new User();

    private final ChatRoomRequest chatRoomRequest = new ChatRoomRequest();

    private final String BUTTON_HOVER_STYLE = "-fx-background-color: #555555; -fx-text-fill: white; -fx-font-size: 25px; -fx-border-width: 2px; -fx-border-radius: 10;";
    private final String BUTTON_DEFAULT_STYLE = "-fx-background-color: #3c3c3c; -fx-text-fill: white; -fx-font-size: 25px; -fx-border-width: 2px; -fx-border-radius: 10;";
    private final String BUTTON_STYLE = "-fx-background-color: #3c3c3c; -fx-text-fill: white; -fx-font-size: 25px; -fx-border-width: 2px; -fx-border-radius: 10;";


    public void initialize() {
        try {
            initializeUIComponents();
            loadChatRooms();
            updateUserInfo();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to initialize ChatController", e);
        }
    }

    private void initializeUIComponents() {
        sendButton.setOnAction(event -> {
            try {
                sendMessage();
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Failed to send message", e);
            }
        });
        addButton.setOnAction(event -> {
            try {
                createNewChatRoom();
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Failed to create new chat room", e);
            }
        });
    }

    private void loadChatRooms() {
        try {
            final List<ChatRoom> chatRooms = chatRoomRequest.getAllChatRooms(user.getUserId());
            if (chatRooms == null) {
                return;
            }
            Platform.runLater(() -> {
                chatBox.getChildren().clear();
                for (ChatRoom chatRoom : chatRooms) {
                    Button chatButton = new Button(chatRoom.getUser2().getUsername());
                    chatButton.setStyle(BUTTON_STYLE);
                    chatButton.setMaxWidth(Double.MAX_VALUE);
                    chatButton.setAlignment(Pos.CENTER_LEFT);
                    HBox.setHgrow(chatButton, Priority.ALWAYS);

                    chatButton.setOnMouseEntered(e -> chatButton.setStyle(BUTTON_HOVER_STYLE));
                    chatButton.setOnMouseExited(e -> chatButton.setStyle(BUTTON_DEFAULT_STYLE));

                    chatButton.setOnAction(event -> openChatWindow(chatRoom));
                    chatBox.getChildren().add(chatButton);
                }
                addButton.setVisible(chatRooms.isEmpty());
            });
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to load chat rooms", e);
        }
    }

    private void openChatWindow(ChatRoom chatRoom) {
        messagesBox.getChildren().clear();
        displayMessage("Message from " + chatRoom.getUser2().getUsername());
        loadMessages(chatRoom);
    }

    private void loadMessages(ChatRoom chatRoom) {
        IMessageRequest messageRequest = new MessageRequest();
        try {
            List<Message> messageList = messageRequest.getMessagesByChatRoom(chatRoom.getUser2().getUserId());
            if (messageList != null) {
                for (Message message : messageList) {
                    displayMessage(message.getMessageText());
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to load messages", e);
        }
    }

    private void updateUserInfo() {
        if (user != null) {
            Platform.runLater(() -> {
                usernameLabel.setText(user.getUsername());
                usernameLabel.setWrapText(true);
            });
        }
    }

    @FXML
    private void sendMessage() throws Exception {
        IMessageRequest messageRequest = new MessageRequest();
        String content = messageField.getText().trim();
        if (!content.isEmpty()) {
            Message message = new Message();

            displayMessage(content);
            messageField.clear();
        }
    }

    private void createNewChatRoom() {
        try {
            int receiverId = getReceiverId();
            chatRoomRequest.createChatRoom(user.getUserId(), receiverId);
            loadChatRooms();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to create new chat room", e);
        }
    }

    public void userInfo(User user) {
        this.user = user;
        Platform.runLater(() -> usernameLabel.setText(user.getUsername()));
    }

    private void displayMessage(String message) {
        Label messageLabel = new Label(message);
        messageLabel.setWrapText(true);
        messageLabel.setMaxWidth(400);
        messageLabel.getStyleClass().add("chat-message");

        Region spacing = new Region();
        spacing.setPrefHeight(10);

        Platform.runLater(() -> {
            messagesBox.getChildren().addAll(messageLabel, spacing);
            messagesScrollPane.setVvalue(1.0);
        });
    }

    private int getReceiverId() {
        return 2;
    }

    private int getCurrentChatRoomId() {
        return 1;
    }
}
