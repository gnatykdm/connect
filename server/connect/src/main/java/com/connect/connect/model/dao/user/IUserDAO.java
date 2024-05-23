package com.connect.connect.model.dao.user;

import com.connect.connect.model.entity.UserEntity;

import java.util.List;

public interface IUserDAO {
    List<UserEntity> getAllUsers();
    UserEntity getUserById(Integer userId);
    void registerUser(UserEntity user);
    void deleteUser(UserEntity user);
}
