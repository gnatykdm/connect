package org.connect.model.dao.user;

import org.connect.model.entities.User;
import org.connect.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class UserDAOImpl implements IUserDAO {

    @Override
    public List<User> getAllUsers() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Query<User> query = session.createQuery("from User", User.class);
        return  query.getResultList();
    }
}
