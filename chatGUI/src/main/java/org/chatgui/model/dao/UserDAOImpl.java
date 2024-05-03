package org.chatgui.model.dao;

import org.chatgui.model.dao.daointerfaces.*;
import org.chatgui.model.entities.UserEntity;
import org.chatgui.model.hibernateconfiguration.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class UserDAOImpl implements IDeleteDAO<UserEntity>, IGetDAO<UserEntity>, IRegisterDAO<UserEntity> {

    @Override
    public void delete(UserEntity entity) {
        session(entity);
    }

    private void session(UserEntity entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();

            session.delete(entity);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void deleteById(UserEntity entity) {
        session(entity);
    }

    @Override
    public List<UserEntity> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();

            List<UserEntity> entities = session.createQuery("from UserEntity", UserEntity.class).list();
            session.getTransaction().commit();
            return entities;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public UserEntity getById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.get(UserEntity.class, id);
        } catch (Exception e) {
            return null;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void register(UserEntity entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();

            session.save(entity);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void update (UserEntity entity){
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();

            session.update(entity);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}