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
import org.connect.controller.settings.AddUserController;
import org.connect.controller.settings.SettingController;
import org.connect.model.entities.ChatRoom;
import org.connect.model.entities.Message;
import org.connect.model.entities.User;
import org.connect.model.service.chatroom.ChatRoomRequest;
import org.connect.model.service.chatroom.IChatRoomRequest;
import org.connect.model.service.message.IMessageRequest;
import org.connect.model.service.message.MessageRequest;
import org.connect.model.service.user.IUserRequest;
import org.connect.model.service.user.UserRequest;

import java.io.IOException;
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
    private final IUserRequest userRequest = new UserRequest();

    private final ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(1);

    private static final Logger LOGGER = Logger.getLogger(ChatController.class.getName());

    private static final String PLUS_BUTTON_DEFAULT =
            "-fx-background-color: #242424; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-width: 2px; -fx-border-color: #c1ff72; -fx-border-radius: 5px;";
    private static final String PLUS_BUTTON_HOVER =
            "-fx-background-color: #c1ff72; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-width: 2px; -fx-border-color: #c1ff72; -fx-border-radius: 5px;";

    private static final String SETTINGS_BUTTON_DEFAULT =
            "-fx-background-color: #242424; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-width: 2px; -fx-border-color: #c1ff72; -fx-border-radius: 5px;";
    private static final String SETTINGS_BUTTON_HOVER =
            "-fx-background-color: #c1ff72; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-width: 2px; -fx-border-color: #c1ff72; -fx-border-radius: 5px;";

    private static final String SEND_BUTTON_DEFAULT =
            "-fx-background-color: #c1ff72; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-width: 2px; -fx-border-radius: 10px; -fx-background-radius: 10px;";
    private static final String SEND_BUTTON_HOVER =
            "-fx-background-color: #242424; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-width: 2px; -fx-border-radius: 10px; -fx-border-color: #c1ff72; -fx-background-radius: 10px;";

    private final List<ChatRoom> friends = new ArrayList<>();
    private Set<Integer> displayedMessageIds = new HashSet<>();

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
                List<ChatRoom> getChatRooms = chatRoomRequest.getAllChatRooms(user.getUserId());
                friends.addAll(getChatRooms);
                Platform.runLater(() -> {
                    chatBox.getChildren().clear();
                    Set<String> usernames = new HashSet<>();
                    for (ChatRoom chatRoom : getChatRooms) {
                        User user2 = chatRoom.getUser2();
                        this.receiverId = user2.getUserId();
                        if (!usernames.contains(user2.getUsername()) && !user2.getUserId().equals(user.getUserId())) {
                            usernames.add(user2.getUsername());

                            Button chatButton = new Button(user2.getUsername());
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
        Platform.runLater(() -> scheduledExecutor.scheduleAtFixedRate(task, 0, 5, TimeUnit.SECONDS));
    }

    private void loadMessages() {
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
        loadMessages();
    }

    @FXML
    private void sendMessage() {
        String content = messageField.getText().trim();
        if (!content.isEmpty()) {
            try {
                messageRequest.sendMessage(user.getUserId(), receiverId, content);
                messageField.clear();
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
            usernameLabel.setText("#" + user.getUsername());
        }
    }

    private void displayMessage(Message message) {
        Platform.runLater(() -> {
            if (shouldDisplayMessage(message) && !displayedMessageIds.contains(message.getMessageId())) {
                displayedMessageIds.add(message.getMessageId());
                VBox messageContainer = createMessageContainer(message);
                addMessageToBox(messageContainer);
                scrollToBottom();
            }
        });
    }

    private boolean shouldDisplayMessage(Message message) {
        return message.getSender().getUserId().equals(user.getUserId()) ||
                message.getReceiver().getUserId().equals(user.getUserId());
    }

    private VBox createMessageContainer(Message message) {
        Label messageLabel = new Label(message.getMessageText());
        messageLabel.setWrapText(true);
        messageLabel.setStyle("-fx-background-insets: 0; -fx-background-radius: 10;");

        if (message.getSender().getUserId().equals(user.getUserId())) {
            messageLabel.setStyle("-fx-background-color: #c1ff72; -fx-text-fill: #242424; -fx-font-size: 14px; -fx-padding: 10px; -fx-background-radius: 10;");
            messageLabel.setAlignment(Pos.CENTER_LEFT);
        } else {
            messageLabel.setStyle("-fx-background-color: #3c3c3c; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px; -fx-background-radius: 10;");
            messageLabel.setAlignment(Pos.CENTER_RIGHT);
        }

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

        return messageContainer;
    }

    private void addMessageToBox(VBox messageContainer) {
        messagesScrollPane.vvalueProperty().unbind();
        messagesBox.getChildren().add(messageContainer);
        messagesScrollPane.layout();
    }

    private void scrollToBottom() {
        messagesScrollPane.setVvalue(1.0);
    }


    @FXML
    private void switchPlusButton(ActionEvent event) {
        stopExecutorService();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/connect/view/settingsview/adduser.fxml"));
        try {
            Parent parent = loader.load();
            AddUserController controller = loader.getController();
            controller.setUser(user);
            controller.setChatRooms(friends);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToSettings(ActionEvent event) {
        scheduledExecutor.shutdown();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/connect/view/settingsview/settings.fxml"));
        try {
            Parent parent = loader.load();
            SettingController controller = loader.getController();
            controller.setUser(user);
            controller.updateUserData();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopExecutorService() {
        scheduledExecutor.shutdown();
    }
}