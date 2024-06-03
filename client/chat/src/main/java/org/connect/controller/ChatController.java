package org.connect.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
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

    private final HttpRequestClient httpClient = new HttpRequestClient();

    public void initialize() {
        sendButton.setOnAction(event -> sendMessage());
    }

    private void sendMessage() {
        String content = messageField.getText();
        httpClient.sendMessage(1, 2, content);
        displayMessage(content);

        messageField.clear();
    }

    private void displayMessage(String message) {
        Label messageLabel = new Label(message);
        messageLabel.setWrapText(true);
        messageLabel.setMaxWidth(400);
        messageLabel.setStyle("-fx-background-color: #c1ff72; -fx-text-fill: black; -fx-font-size: 14px; -fx-padding: 10px; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx-border-color: #8abf51; -fx-border-width: 1px;");

        Region spacing = new Region();
        spacing.setPrefHeight(10);

        messagesBox.getChildren().addAll(messageLabel, spacing);
        messagesScrollPane.setVvalue(1.0);
    }
}
