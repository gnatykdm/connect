package org.chatgui.model.dao;

import org.chatgui.model.dao.daointerfaces.*;
import org.chatgui.model.entities.ParticipantEntity;
import org.chatgui.model.hibernateconfiguration.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class ParticipantDAOImpl implements IGetDAO<ParticipantEntity>, IRegisterDAO<ParticipantEntity>, IDeleteDAO<ParticipantEntity> {

    @Override
    public void delete(ParticipantEntity entity) {
        session(entity);
    }

    private void session(ParticipantEntity entity) {
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
    public void deleteById(ParticipantEntity entity) {
        session(entity);
    }

    @Override
    public List<ParticipantEntity> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();

            List<ParticipantEntity> entities = session.createQuery("from ParticipantEntity ", ParticipantEntity.class).list();
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
    public ParticipantEntity getById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.get(ParticipantEntity.class, id);
        } catch (Exception e) {
            return null;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void register(ParticipantEntity entity) {
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
    public void update (ParticipantEntity entity){
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
