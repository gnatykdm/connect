package org.connect.model.dao.message;

import org.connect.model.entities.User;

import java.util.List;

public interface IUserService {
    List<User> getAllUsers();
}
