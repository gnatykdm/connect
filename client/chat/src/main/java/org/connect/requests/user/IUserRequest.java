package org.connect.requests.user;

import org.connect.model.dto.UserDTO;
import org.connect.model.entities.User;

public interface IUserRequest {
    User registerUser(UserDTO user) throws Exception;
    User loginUserRequest(String username, String password) throws Exception;
}
