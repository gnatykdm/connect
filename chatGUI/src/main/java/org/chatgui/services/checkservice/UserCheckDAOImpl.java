package org.chatgui.services;

import org.chatgui.model.dao.user.UserDAOImpl;
import org.chatgui.model.entities.UserEntity;
import java.util.List;

public class UserCheckDAOImpl implements ICheckUserDAO {

    private String userName;
    private String userPassword;
    private UserDAOImpl userDAO;

    public UserCheckDAOImpl() {
        this.userDAO = new UserDAOImpl();
    }

    @Override
    public boolean checkUserName(String userName) {
        List<UserEntity> userNamesList = userDAO.getAll();
        return userNamesList.stream()
                .map(UserEntity::getUserName)
                .anyMatch(name -> name.equals(userName));
    }


    @Override
    public boolean checkUserPassword(String userPassword) {
        List<UserEntity> userPasswordList = userDAO.getAll();
        return userPasswordList.stream()
                .map(UserEntity::getUserPassword)
                .anyMatch(password -> password.equals(userPassword));
    }
}
