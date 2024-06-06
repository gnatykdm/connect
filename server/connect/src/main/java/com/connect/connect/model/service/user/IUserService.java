package com.connect.connect.model.service.user;

import com.connect.connect.model.entity.User;

import java.util.List;

public interface IUserService {
    void createUser(User user);

    void updateUser(User user);

    void deleteUserById(Integer userId);

    User getUserById(Integer userId);

    User getUserByUsername(String username);

    List<User> getAllUsers();

    boolean loginUserValidation(String userName, String userPassword);
}
