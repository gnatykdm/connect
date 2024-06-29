package org.connect.model.service.user;

import org.connect.model.dto.UserDTO;
import org.connect.model.entities.User;

public interface IUserRequest {
    User registerUser(UserDTO user) throws Exception;
    User loginUserRequest(String username, String password) throws Exception;
    User getUserById(Integer userId) throws Exception;
    User getUserByUsername(String username) throws Exception;

    void updateUserName(Integer id, String name);
    void updateUserEmail(Integer id, String email);
    void updateUserPassword(Integer id, String password);
}
