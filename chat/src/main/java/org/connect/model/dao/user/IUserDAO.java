package org.connect.model.dao.user;

import org.connect.model.entities.User;

import java.util.List;

public interface IUserDAO {
    List<User> getAllUsers();
}
