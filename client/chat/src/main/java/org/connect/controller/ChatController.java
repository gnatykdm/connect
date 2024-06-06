package org.connect.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import lombok.Setter;
import org.connect.model.entities.User;
import org.connect.util.HttpRequestClient;

public class ChatController {

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

    @Setter
    private User user;

    private final HttpRequestClient httpClient = new HttpRequestClient();

    public void initialize() {
        sendButton.setOnAction(event -> sendMessage());
        if(user != null) {
            usernameLabel.setText(user.getUsername());
            usernameLabel.setWrapText(true);
        }
    }

    @FXML
    private void sendMessage() {
        String content = messageField.getText().trim();
        if (content.isEmpty()) {
            return;
        }

        int senderId = user.getUserId();
        int receiverId = getReceiverId();

        try {
            httpClient.sendMessage(senderId, receiverId, content);
            displayMessage(content);
            messageField.clear();
        } catch (Exception e) {
            e.printStackTrace();
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
}
