package org.chatgui.model.dao.user;

import org.chatgui.model.entities.UserEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.List;
import org.chatgui.model.hibernateconfiguration.HibernateUtil;

public class UserDAOImpl implements IUserDAO {

    @Override
    public List<UserEntity> getAllUsers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            Query<UserEntity> query = session.createQuery("from UserEntity", UserEntity.class);
            List<UserEntity> users = query.getResultList();
            session.getTransaction().commit();
            return users;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
