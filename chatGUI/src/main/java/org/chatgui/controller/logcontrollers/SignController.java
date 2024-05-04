package org.chatgui.controller.logcontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import lombok.Getter;
import org.chatgui.model.dao.UserDAOImpl;
import org.chatgui.model.entities.UserEntity;

import java.time.LocalDate;

public class SignController {
    @FXML
    private TextField userName;
    @FXML
    private TextField userEmail;
    @FXML
    private TextField userPassword;


    @Getter
    private String name;
    @Getter
    private String email;
    @Getter
    private String password;

    public void submit(ActionEvent event) {
        name = userName.getText();
        email = userEmail.getText();
        password = userPassword.getText();

        System.out.println("-------------------------------");
        System.out.println("Registering user: " + name + " " + email + " " + password);

        UserEntity user = new UserEntity(name, password, email, LocalDate.now());
        UserDAOImpl userDAO = new UserDAOImpl();
        userDAO.register(user);
    }
}
