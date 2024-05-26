package com.connect.connect.model.dao.user;

import com.connect.connect.model.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOImpl implements IUserDAO {

    @Autowired
    private EntityManager manager;

    @Override
    @Transactional
    public void registerUser(User user) {
        Session session = manager.unwrap(Session.class);
        session.persist(user);
    }

    @Override
    @Transactional
    public void removeUserById(Integer userId) {
        Session session = manager.unwrap(Session.class);
        User user = session.get(User.class, userId);
        session.remove(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        Session session = manager.unwrap(Session.class);
        session.refresh(user);
    }

    @Override
    @Transactional
    public User getUserById(Integer userId) {
        return manager.find(User.class, userId);
    }

    @Override
    @Transactional
    public User getUserByUserName(String userName) {
        Session session = manager.unwrap(Session.class);

        Query<User> query = session.createQuery("from User where username = :userName", User.class);
        query.setParameter("userName", userName);
        return query.getSingleResult();
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        Session session = manager.unwrap(Session.class);

        Query<User> query = session.createQuery("from User", User.class);
        return query.getResultList();
    }

    @Override
    public User getUserByUsernameAndPassword(String username, String password) {
        Session session = manager.unwrap(Session.class);

        Query<User> query = session.createQuery("from User where username = :username and password = :password",
                User.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        return query.getSingleResult();
    }
}
