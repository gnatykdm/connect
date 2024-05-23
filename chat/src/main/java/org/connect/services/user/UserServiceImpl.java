package org.connect.services.user;

import org.connect.model.dao.user.UserDAOImpl;
import org.connect.model.entities.UserEntity;

import java.util.List;

public class UserServiceImpl implements IUserService {

    private UserDAOImpl userDAO = new UserDAOImpl();

    @Override
    public void connect(UserEntity user) {
        userDAO.register(user);
    }

    @Override
    public void disconnect(UserEntity user) {
        userDAO.delete(user);
    }

    @Override
    public boolean checkUserSignData(String name, String email) {
        List<UserEntity> userList = userDAO.getAll();

        return userList.stream()
                .anyMatch(user -> user.getUsername().equalsIgnoreCase(name)
                        || user.getEmail().equalsIgnoreCase(email));
    }

    @Override
    public boolean checkUserLoginData(String name, String password) {
        List<UserEntity> userList = userDAO.getAll();

        return userList.stream().anyMatch(user -> user.getUsername().equalsIgnoreCase(name)
                && user.getPassword().equalsIgnoreCase(password));
    }
}
