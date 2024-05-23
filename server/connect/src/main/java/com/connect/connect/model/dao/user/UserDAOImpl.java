package com.connect.connect.model.dao.user;

import com.connect.connect.model.entity.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOImpl implements IUserDAO {

    @Autowired
    private EntityManager manager;

    @Override
    @Transactional
    public List<UserEntity> getAllUsers() {
        Session session = manager.unwrap(Session.class);
        List<UserEntity> userList = manager.createQuery("from UserEntity", UserEntity.class).getResultList();
        return userList;
    }

    @Override
    @Transactional
    public UserEntity getUserById(Integer userId) {
        Session session = manager.unwrap(Session.class);
        return session.get(UserEntity.class, userId);
    }

    @Override
    @Transactional
    public void registerUser(UserEntity user) {
        Session session = manager.unwrap(Session.class);
        session.persist(user);
    }

    @Override
    @Transactional
    public void deleteUser(UserEntity user) {
        Session session = manager.unwrap(Session.class);
        session.remove(user);
    }
}
