package org.chatgui.controller.logcontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.io.IOException;

public class LogInfoController {
    @FXML
    private TextField userName;
    @FXML
    private TextField userPassword;

    @Getter
    private String name;
    @Getter
    private String pass;

    @NotNull
    private FXMLLoader loader;

    @NotNull
    private Scene scene;

    @NotNull
    private Stage stage;

    public void logIn(ActionEvent event) throws IOException {
    }
}
