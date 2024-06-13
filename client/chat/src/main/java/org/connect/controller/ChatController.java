package org.connect.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import org.connect.model.entities.ChatRoom;
import org.connect.model.entities.Message;
import org.connect.model.entities.User;
import org.connect.model.requests.chatroom.ChatRoomRequest;
import org.connect.model.requests.chatroom.IChatRoomRequest;
import org.connect.model.requests.message.IMessageRequest;
import org.connect.model.requests.message.MessageRequest;
import org.connect.model.requests.user.IUserRequest;
import org.connect.model.requests.user.UserRequestClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatController {
    @FXML private TextField messageField;
    @FXML private Button sendButton;
    @FXML private VBox messagesBox;
    @FXML private ScrollPane messagesScrollPane;
    @FXML private Label usernameLabel;
    @FXML private Button addButton;
    @FXML private VBox chatBox;
    @FXML private HBox statusBar;
    @FXML private Label chatWithLabel;

    private User user = new User();
    private Integer receiverId;

    private final IChatRoomRequest chatRoomRequest = new ChatRoomRequest();
    private final IMessageRequest messageRequest = new MessageRequest();
    private final IUserRequest userRequest = new UserRequestClient();

    private final String BUTTON_HOVER_STYLE = "-fx-background-color: #555555; -fx-text-fill: white; -fx-font-size: 25px; -fx-border-width: 2px; -fx-border-radius: 10;";
    private final String BUTTON_DEFAULT_STYLE = "-fx-background-color: #3c3c3c; -fx-text-fill: white; -fx-font-size: 25px; -fx-border-width: 2px; -fx-border-radius: 10;";

    private static final Logger LOGGER = Logger.getLogger(ChatController.class.getName());

    public void initialize() {
        try {
            initializeUIComponents();
            loadChatRooms();
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
        LOGGER.info("Loading chat rooms for userId: " + user.getUserId());
        try {
            final List<ChatRoom> chatRooms = chatRoomRequest.getAllChatRooms(user.getUserId());
            if (chatRooms == null) {
                return;
            }
            Platform.runLater(() -> {
                chatBox.getChildren().clear();
                for (ChatRoom chatRoom : chatRooms) {
                    Button chatButton = new Button(chatRoom.getUser2().getUsername());
                    chatButton.setStyle(BUTTON_DEFAULT_STYLE);
                    chatButton.setMaxWidth(Double.MAX_VALUE);
                    chatButton.setAlignment(Pos.CENTER_LEFT);

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
        receiverId = chatRoom.getUser2().getUserId();
        chatWithLabel.setText("Chat with: " + chatRoom.getUser2().getUsername());
        loadMessages(chatRoom);
    }

    private void loadMessages(ChatRoom chatRoom) {
        IMessageRequest messageRequest = new MessageRequest();
        try {
            List<Message> messageList = messageRequest.getMessages(user.getUserId(), chatRoom.getUser2().getUserId());
            List<Message> messages = messageRequest.getMessages(chatRoom.getUser2().getUserId(), user.getUserId());
            messageList.addAll(messages);

            messageList = sortMessagesByTime(messageList);
            if (messageList != null) {
                for (Message message : messageList) {
                    displayMessage(message);
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to load messages", e);
        }
    }

    @FXML
    private void sendMessage() throws Exception {
        String content = messageField.getText().trim();
        if (!content.isEmpty()) {
            try {
                messageRequest.sendMessage(user.getUserId(), receiverId, content);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Failed to send message", e);
            }

            User receiver = userRequest.getUserById(receiverId);
            Message message = new Message(null, user, receiver, content, LocalDateTime.now());
            displayMessage(message);
            messageField.clear();
        }
    }

    private List<Message> sortMessagesByTime(List<Message> messages) {
        messages.sort(Comparator.comparing(Message::getTimestamp));
        return messages;
    }

    public void setUser(User user) {
        this.user = user;
        if (user != null) {
            loadChatRooms();
            usernameLabel.setText("@" + user.getUsername());
        }
    }

    private void createNewChatRoom() {
        try {
            chatRoomRequest.createChatRoom(user.getUserId(), receiverId);
            loadChatRooms();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to create new chat room", e);
        }
    }

    private void displayMessage(Message message) {
        Label messageLabel = new Label(message.getMessageText());
        messageLabel.setWrapText(true);
        messageLabel.setStyle("-fx-background-insets: 0; -fx-background-radius: 10;");

        String backgroundColor;
        if (message.getSender().getUserId().equals(user.getUserId())) {
            backgroundColor = "#8379e7"; // Background color for sender
        } else {
            backgroundColor = "#555555FF"; // Background color for receiver
        }
        messageLabel.setStyle("-fx-background-color: " + backgroundColor + "; -fx-background-insets: 0; -fx-background-radius: 10; " +
                "-fx-border-color: " + backgroundColor + "; -fx-border-radius: 10; -fx-border-width: 1; -fx-padding: 5px; -fx-text-fill: white; -fx-font-size: 14px;");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedTimestamp = message.getTimestamp().format(formatter);
        Label senderLabel = new Label("Sent by: " + message.getSender().getUsername() + " at " + formattedTimestamp);
        senderLabel.setStyle("-fx-text-fill: #555555; -fx-font-size: 10px;");
        senderLabel.setTextAlignment(TextAlignment.RIGHT);

        VBox messageContainer = new VBox(messageLabel, senderLabel);
        messageContainer.setSpacing(4);
        messageContainer.setPadding(new Insets(4, 5, 0, 5));

        messageContainer.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(messageContainer, Priority.ALWAYS);

        Platform.runLater(() -> {
            messagesBox.getChildren().add(messageContainer);
            messagesScrollPane.setVvalue(1.0);
        });
    }

}
