package org.connect.services.validation;

import org.connect.model.dao.user.UserDAOImpl;
import org.connect.model.entities.UserEntity;
import java.util.List;

public class UserCheckDAOImpl implements ICheckUserDAO {

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

    @Override
    public boolean checkUserEmail(String userEmail) {
        List<UserEntity> userEmailList = userDAO.getAll();
        return userEmailList.stream().map(UserEntity::getUserEmail)
                .anyMatch(email -> email.equals(userEmail));
    }
}
