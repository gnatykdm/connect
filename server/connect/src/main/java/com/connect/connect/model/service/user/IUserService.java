package com.connect.connect.model.service.user;

import com.connect.connect.model.entity.User;

import java.util.List;

public interface IUserService {
    void createUser(User user);

    void updateUser(User user);

    void deleteUser(Integer userId);

    User getUserById(Integer userId);

    User getUserByUsername(String username);

    List<User> getAllUsers();

    List<User> getAllFriends(Integer userId);

    void addFriendship(Integer user1Id, Integer user2Id);

    void removeFriendship(Integer user1Id, Integer user2Id);

    boolean areFriends(Integer user1Id, Integer user2Id);

    User getUserByUsernameAndPassword(String username, String password);
}
