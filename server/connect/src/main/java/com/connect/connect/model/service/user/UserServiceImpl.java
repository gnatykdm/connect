package com.connect.connect.model.service.user;

import com.connect.connect.model.dao.friends.IFriendShipDAO;
import com.connect.connect.model.dao.user.IUserDAO;
import com.connect.connect.model.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private IUserDAO userDAO;
    @Autowired
    private IFriendShipDAO friendShipDAO;

    @Override
    @Transactional
    public void createUser(User user) {
        logger.info("Creating user: {}", user);
        try {
            userDAO.registerUser(user);
        } catch (Exception e) {
            logger.error("Error creating user: ", e);
            throw e;
        }
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        logger.info("Updating user: {}", user);
        try {
            userDAO.updateUser(user);
        } catch (Exception e) {
            logger.error("Error updating user: ", e);
            throw e;
        }
    }

    @Override
    @Transactional
    public void deleteUser(Integer userId) {
        logger.info("Deleting user with ID: {}", userId);
        try {
            userDAO.removeUserById(userId);
        } catch (Exception e) {
            logger.error("Error deleting user: ", e);
            throw e;
        }
    }

    @Override
    @Transactional
    public User getUserById(Integer userId) {
        logger.info("Getting user by ID: {}", userId);
        try {
            return userDAO.getUserById(userId);
        } catch (Exception e) {
            logger.error("Error getting user by ID: ", e);
            throw e;
        }
    }

    @Override
    @Transactional
    public User getUserByUsername(String username) {
        logger.info("Getting user by username: {}", username);
        try {
            return userDAO.getUserByUserName(username);
        } catch (Exception e) {
            logger.error("Error getting user by username: ", e);
            throw e;
        }
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        logger.info("Getting all users");
        try {
            return userDAO.getAllUsers();
        } catch (Exception e) {
            logger.error("Error getting all users: ", e);
            throw e;
        }
    }

    @Override
    @Transactional
    public List<User> getAllFriends(Integer userId) {
        logger.info("Getting all friends for user ID: {}", userId);
        try {
            return friendShipDAO.getAllFriends(userId);
        } catch (Exception e) {
            logger.error("Error getting all friends: ", e);
            throw e;
        }
    }

    @Override
    @Transactional
    public void addFriendship(Integer user1Id, Integer user2Id) {
        logger.info("Adding friendship between user ID: {} and user ID: {}", user1Id, user2Id);
        try {
            friendShipDAO.addFriendShip(user1Id, user2Id);
        } catch (Exception e) {
            logger.error("Error adding friendship: ", e);
            throw e;
        }
    }

    @Override
    @Transactional
    public void removeFriendship(Integer user1Id, Integer user2Id) {
        logger.info("Removing friendship between user ID: {} and user ID: {}", user1Id, user2Id);
        try {
            friendShipDAO.removeFriendship(user1Id, user2Id);
        } catch (Exception e) {
            logger.error("Error removing friendship: ", e);
            throw e;
        }
    }

    @Override
    @Transactional
    public boolean areFriends(Integer user1Id, Integer user2Id) {
        logger.info("Checking if user ID: {} and user ID: {} are friends", user1Id, user2Id);
        try {
            return friendShipDAO.checkFriends(user1Id, user2Id);
        } catch (Exception e) {
            logger.error("Error checking friendship: ", e);
            throw e;
        }
    }

    @Override
    @Transactional
    public User getUserByUsernameAndPassword(String username, String password) {
        logger.info("Getting user by username: {} and password", username);
        try {
            return userDAO.getUserByUsernameAndPassword(username, password);
        } catch (Exception e) {
            logger.error("Error getting user by username and password: ", e);
            throw e;
        }
    }

    @Override
    @Transactional
    public void createUserByUserNameAndPassword(String username, String password) {
        logger.info("Creating user with username: {} and password", username);
        try {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            userDAO.registerUser(user);
        } catch (Exception e) {
            logger.error("Error creating user by username and password: ", e);
            throw e;
        }
    }
}
