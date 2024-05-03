package org.chatgui.model.dao;

import org.chatgui.model.dao.daointerfaces.*;
import org.chatgui.model.entities.MessageEntity;
import org.chatgui.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class MessageDAOImpl implements IDeleteDAO<MessageEntity>, IGetDAO<MessageEntity>, IRegisterDAO<MessageEntity> {

    @Override
    public void delete(MessageEntity entity) {
        session(entity);
    }

    private void session(MessageEntity entity) {
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
    public void deleteById(MessageEntity entity) {
        session(entity);
    }

    @Override
    public List<MessageEntity> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();

            List<MessageEntity> entities = session.createQuery("from MessageEntity", MessageEntity.class).list();
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
    public MessageEntity getById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.get(MessageEntity.class, id);
        } catch (Exception e) {
            return null;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void register(MessageEntity entity) {
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
    public void update (MessageEntity entity){
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