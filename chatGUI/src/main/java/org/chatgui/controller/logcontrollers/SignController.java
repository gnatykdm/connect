package org.chatgui.controller.logcontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import lombok.Getter;

public class SignController {
    @FXML
    private TextField userName;
    @FXML
    private TextField userEmail;
    @FXML
    private TextField userBirthDate;
    @FXML
    private TextField userPassword;


    @Getter
    private String name;
    @Getter
    private String email;
    @Getter
    private String birthDate;
    @Getter
    private String password;

    public void signUp(ActionEvent event) {
        name = userName.getText();
        email = userEmail.getText();
        birthDate = userBirthDate.getText();
        password = userPassword.getText();


        UserEntity user = new UserEntity(name, birthDate, email, password);
    }
}
