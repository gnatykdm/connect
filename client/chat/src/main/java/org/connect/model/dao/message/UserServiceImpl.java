package org.connect.model.dao.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.connect.model.dao.user.IUserDAO;
import org.connect.model.entities.User;

import java.util.List;

public class UserServiceImpl implements IUserService {

    private IUserDAO userDAO;

    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }
}
