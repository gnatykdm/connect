package com.connect.connect.model.service.user;

import com.connect.connect.model.entity.User;
import com.connect.connect.model.repository.user.UserRepository;
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
    private UserRepository userRepository;

    @Override
    public void createUser(User user) {
        logger.info("Creating user: {}", user);
        try {
            userRepository.save(user);
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
            userRepository.save(user);
        } catch (Exception e) {
            logger.error("Error updating user: ", e);
            throw e;
        }
    }

    @Override
    @Transactional
    public void deleteUserById(Integer userId) {
        logger.info("Deleting user with ID: {}", userId);
        try {
            userRepository.deleteByUserId(userId);
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
            return userRepository.findByUserId(userId);
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
            return userRepository.findByUsername(username);
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
            return userRepository.findAll();
        } catch (Exception e) {
            logger.error("Error getting all users: ", e);
            throw e;
        }
    }

    @Override
    @Transactional
    public boolean loginUserValidation(String userName, String userPassword) {

        logger.info("Validating user login: {}", userName);
        List<User> userList = userRepository.findAll();

        logger.info("User list: {}", userList);
        for (User u : userList) {
            if (u.getUsername().equals(userName) && u.getPassword().equals(userPassword)) {
                logger.info("User login successful");
                return true;
            }
        }
        logger.info("User login failed");
        return false;
    }
}
