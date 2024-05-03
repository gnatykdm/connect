package org.chatgui.model.dao.user;

import org.chatgui.model.entities.UserEntity;
import java.util.List;

public interface IUserDAO {
    List<UserEntity> getAllUsers();
}
