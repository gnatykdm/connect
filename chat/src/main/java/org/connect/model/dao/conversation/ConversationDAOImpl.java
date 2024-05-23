package org.connect.model.dao.conversation;

import org.connect.model.dao.daointerfaces.*;
import org.hibernate.Session;
import java.util.List;
import org.connect.util.HibernateUtil;
import org.connect.model.entities.ConversationEntity;

public class ConversationDAOImpl implements IDeleteDAO<ConversationEntity>, IGetDAO<ConversationEntity>, IRegisterDAO<ConversationEntity> {


    @Override
    public void delete(ConversationEntity entity) {
        session(entity);
    }

    private void session(ConversationEntity entity) {
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
    public void deleteById(ConversationEntity entity) {
        session(entity);
    }

    @Override
    public List<ConversationEntity> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();

            List<ConversationEntity> entities = session.createQuery("from ConversationEntity", ConversationEntity.class).list();
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
    public ConversationEntity getById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.get(ConversationEntity.class, id);
        } catch (Exception e) {
            return null;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
        }
        }
    }

    @Override
    public void register(ConversationEntity entity) {
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
        public void update(ConversationEntity entity){
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