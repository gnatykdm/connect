package org.connect.services.user;

import org.connect.model.entities.UserEntity;

public interface IUserService {
    void connect(UserEntity user);
    void disconnect(UserEntity user);
    boolean checkUserSignData(String userEmail, String userName);
    boolean checkUserLoginData(String name, String password);
}
