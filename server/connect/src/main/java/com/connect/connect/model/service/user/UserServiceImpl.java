package com.connect.connect.model.service.user;

import com.connect.connect.model.dao.friends.IFriendShipDAO;
import com.connect.connect.model.dao.user.IUserDAO;
import com.connect.connect.model.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserDAO userDAO;
    @Autowired
    private IFriendShipDAO friendShipDAO;

    @Override
    @Transactional
    public void createUser(User user) {
        userDAO.registerUser(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    @Override
    @Transactional
    public void deleteUser(Integer userId) {
        userDAO.removeUserById(userId);
    }

    @Override
    @Transactional
    public User getUserById(Integer userId) {
        return userDAO.getUserById(userId);
    }

    @Override
    @Transactional
    public User getUserByUsername(String username) {
       return userDAO.getUserByUserName(username);
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    @Transactional
    public List<User> getAllFriends(Integer userId) {
        return friendShipDAO.getAllFriends(userId);
    }

    @Override
    @Transactional
    public void addFriendship(Integer user1Id, Integer user2Id) {
        friendShipDAO.addFriendShip(user1Id, user2Id);
    }

    @Override
    @Transactional
    public void removeFriendship(Integer user1Id, Integer user2Id) {
        friendShipDAO.removeFriendship(user1Id, user2Id);
    }

    @Override
    @Transactional
    public boolean areFriends(Integer user1Id, Integer user2Id) {
        return friendShipDAO.checkFriends(user1Id, user2Id);
    }

    @Override
    @Transactional
    public User getUserByUsernameAndPassword(String username, String password) {
        return userDAO.getUserByUsernameAndPassword(username, password);
    }
}
