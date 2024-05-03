package org.chatgui.model.dao.message;

import org.chatgui.model.entities.MessageEntity;
import org.hibernate.Session;
import org.chatgui.model.hibernateconfiguration.HibernateUtil;
import org.hibernate.query.Query;

import java.util.List;

public class MessageDAOImpl implements IMessageDAO {
    @Override
    public List<MessageEntity> getAllMessages() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            Query<MessageEntity> query = session.createQuery("from MessageEntity", MessageEntity.class);
            List<MessageEntity> messages = query.getResultList();
            session.getTransaction().commit();
            return messages;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
