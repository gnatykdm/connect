package org.connect.model.dao.user;

import org.connect.model.entities.UserEntity;
import java.util.List;

public interface IUserDAO {
    void delete(UserEntity entity);
    void deleteById(UserEntity entity);
    UserEntity getById(int id);
    List<UserEntity> getAll();
}
