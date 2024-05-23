package org.connect.services.user;

import org.connect.model.dao.user.UserDAOImpl;
import org.connect.model.entities.UserEntity;

public class UserServiceImpl implements IUserService {

    private UserDAOImpl userDAO;

    @Override
    public void connect(UserEntity user) {
        userDAO.register(user);
    }

    @Override
    public void disconnect(UserEntity user) {
        userDAO.delete(user);
    }

    @Override
    public boolean checkUserData(UserEntity user) {
        return false;
    }
}
