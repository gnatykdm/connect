package org.connect.controller.maincontroller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.connect.model.entities.ChatRoom;
import org.connect.model.entities.Message;
import org.connect.model.entities.User;
import org.connect.model.service.chatroom.ChatRoomRequest;
import org.connect.model.service.chatroom.IChatRoomRequest;
import org.connect.model.service.message.IMessageRequest;
import org.connect.model.service.message.MessageRequest;
import org.connect.model.service.user.IUserRequest;
import org.connect.model.service.user.UserRequestClient;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatController {
    @FXML private TextArea messageField;
    @FXML private Button sendButton;
    @FXML private VBox messagesBox;
    @FXML private ScrollPane messagesScrollPane;
    @FXML private Label usernameLabel;
    @FXML private VBox chatBox;
    @FXML private Label chatWithLabel;
    @FXML private Button settingsButton;
    @FXML private Button plusButton;

    private User user = new User();
    private Integer receiverId;

    private final IChatRoomRequest chatRoomRequest = new ChatRoomRequest();
    private final IMessageRequest messageRequest = new MessageRequest();
    private final IUserRequest userRequest = new UserRequestClient();

    private final ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(1);

    private static final Logger LOGGER = Logger.getLogger(ChatController.class.getName());

    private static final String PLUS_BUTTON_DEFAULT = "-fx-background-color: #242424; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-width: 2px; -fx-border-color: #c1ff72; -fx-border-radius: 5px;";
    private static final String PLUS_BUTTON_HOVER = "-fx-background-color: #c1ff72; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-width: 2px; -fx-border-color: #c1ff72; -fx-border-radius: 5px;";

    private static final String SETTINGS_BUTTON_DEFAULT = "-fx-background-color: #242424; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-width: 2px; -fx-border-color: #c1ff72; -fx-border-radius: 5px;";
    private static final String SETTINGS_BUTTON_HOVER = "-fx-background-color: #c1ff72; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-width: 2px; -fx-border-color: #c1ff72; -fx-border-radius: 5px;";

    private static final String SEND_BUTTON_DEFAULT = "-fx-background-color: #c1ff72; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-width: 2px; -fx-border-radius: 10px; -fx-background-radius: 10px;";
    private static final String SEND_BUTTON_HOVER = "-fx-background-color: #242424; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-width: 2px; -fx-border-radius: 10px; -fx-border-color: #c1ff72; -fx-background-radius: 10px;";

    public void initialize() {

        plusButton.setOnMouseEntered(e -> plusButton.setStyle(PLUS_BUTTON_HOVER));
        plusButton.setOnMouseExited(e -> plusButton.setStyle(PLUS_BUTTON_DEFAULT));

        settingsButton.setOnMouseEntered(e -> settingsButton.setStyle(SETTINGS_BUTTON_HOVER));
        settingsButton.setOnMouseExited(e -> settingsButton.setStyle(SETTINGS_BUTTON_DEFAULT));

        sendButton.setOnMouseEntered(e -> sendButton.setStyle(SEND_BUTTON_HOVER));
        sendButton.setOnMouseExited(e -> sendButton.setStyle(SEND_BUTTON_DEFAULT));

        try {
            initializeUIComponents();
            setupTextAreaAutoResize();
            if (user != null) {
                loadChatRooms();
            }
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
    }

    private void setupTextAreaAutoResize() {
        messageField.setWrapText(true);
        messageField.textProperty().addListener((observable, oldValue, newValue) -> {
            double newHeight = computeTextHeight(newValue);
            messageField.setPrefHeight(newHeight);
            sendButton.setPrefHeight(newHeight);
        });
    }

    private double computeTextHeight(String text) {
        int lines = text.split("\n").length;
        double lineHeight = 20;
        return lines * lineHeight + 20;
    }

    private void loadChatRooms() {
        Runnable task = () -> {
            LOGGER.info("Loading chat rooms for userId: " + user.getUserId());
            try {
                final List<ChatRoom> chatRooms = chatRoomRequest.getAllChatRooms(user.getUserId());
                if (chatRooms == null) {
                    return;
                }

                Platform.runLater(() -> {
                    chatBox.getChildren().clear();
                    for (ChatRoom chatRoom : chatRooms) {
                        if (!chatRoom.getUser2().equals(user)) {
                            Button chatButton = new Button(chatRoom.getUser2().getUsername());
                            chatButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-width: 2px; -fx-border-color: #c1ff72; -fx-border-radius: 5px;");
                            chatButton.setMaxWidth(Double.MAX_VALUE);
                            chatButton.setAlignment(Pos.CENTER_LEFT);
                            chatButton.setPadding(new Insets(10, 20, 10, 20));

                            chatButton.setOnMouseEntered(e -> chatButton.setStyle("-fx-background-color: #3c3c3c; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-width: 2px; -fx-border-color: #c1ff72; -fx-border-radius: 5px;"));
                            chatButton.setOnMouseExited(e -> chatButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-width: 2px; -fx-border-color: #c1ff72; -fx-border-radius: 5px;"));

                            chatButton.setOnAction(event -> openChatWindow(chatRoom));
                            chatBox.getChildren().add(chatButton);
                        }
                    }
                });

            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Failed to load chat rooms", e);
            }
        };

        scheduledExecutor.scheduleAtFixedRate(task, 0, 5, TimeUnit.SECONDS);
    }


    public void loadMessages(ChatRoom chatRoom) {
        Runnable task = () -> {
            try {
                List<Message> messageList = messageRequest.getMessages(user.getUserId(), receiverId);

                for (Message message : sortMessagesByTime(messageList)) {
                    displayMessage(message);
                }

            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Failed to load messages", e);
            }
        };
        scheduledExecutor.scheduleAtFixedRate(task, 0, 5, TimeUnit.SECONDS);
    }

    private void openChatWindow(ChatRoom chatRoom) {
        receiverId = chatRoom.getUser2().getUserId();
        chatWithLabel.setText("Chat with: " + chatRoom.getUser2().getUsername());
        loadMessages(chatRoom);
    }



    @FXML
    private void sendMessage() {
        String content = messageField.getText().trim();
        if (!content.isEmpty()) {
            try {
                messageRequest.sendMessage(user.getUserId(), receiverId, content);
                User receiver = userRequest.getUserById(receiverId);
                Message message = new Message(null, user, receiver, content, LocalDateTime.now());

                displayMessage(message);

            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Failed to send message", e);
            }
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
        Platform.runLater(() -> {
            Label messageLabel = new Label(message.getMessageText());
            messageLabel.setWrapText(true);
            messageLabel.setStyle("-fx-background-insets: 0; -fx-background-radius: 10;");

            String backgroundColor;
            if (message.getSender().getUserId().equals(user.getUserId())) {
                backgroundColor = "#8379e7";
            } else {
                backgroundColor = "#555555FF";
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
            messageContainer.setPadding(new Insets(4, 10, 0, 5));

            messageContainer.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(messageContainer, Priority.ALWAYS);

            messagesScrollPane.vvalueProperty().unbind();
            messagesBox.getChildren().add(messageContainer);
            messagesScrollPane.layout();
            messagesScrollPane.setVvalue(1.0);
        });
    }


    @FXML
    private void switchPlusButton(ActionEvent event) {
        switchScene("/org/connect/view/settingsview/adduser.fxml", event);
    }

    @FXML
    private void switchToSettings(ActionEvent event) {
        switchScene("/org/connect/view/settingsview/sett.fxml", event);
    }

    private void switchScene(String path, ActionEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(path));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(parent);

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
