package com.connect.connect.model.dao.user;

import com.connect.connect.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IUserDAO {
    void registerUser(User user);

    void removeUserById(Integer userId);

    void updateUser(User user);

    User getUserById(Integer userId);

    User getUserByUserName(String userName);

    List<User> getAllUsers();

    User getUserByUsernameAndPassword(String username, String password);
}
