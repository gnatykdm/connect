package com.connect.connect.model.dao.message;

import com.connect.connect.model.entity.Message;
import com.connect.connect.model.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MessageDAOImpl implements IMessageDAO {

    @Autowired
    private EntityManager manager;

    @Override
    @Transactional
    public void saveMessage(Message message) {
        manager.persist(message);
    }

    @Override
    @Transactional
    public void updateMessage(Message message) {
        Session session = manager.unwrap(Session.class);
        session.refresh(message);
    }

    @Override
    @Transactional
    public void remoweMessage(Integer messageId) {
        Session session = manager.unwrap(Session.class);

        Message message = session.get(Message.class, messageId);
        session.remove(message);
    }

    @Override
    @Transactional
    public Message getMessageById(Integer messageId) {
        Session session = manager.unwrap(Session.class);
        return session.get(Message.class, messageId);
    }

    @Override
    @Transactional
    public List<Message> getMessageSendByUser(Integer sendUserId) {
        Session session = manager.unwrap(Session.class);
        User sender = session.find(User.class, sendUserId);

        Query<Message> query = session.createQuery("from Message where sender = :sender", Message.class);
        query.setParameter("sender", sender);
        return query.getResultList();
    }

    @Override
    @Transactional
    public List<Message> getMessageReceivedByUser(Integer receiveUserId) {
        Session session = manager.unwrap(Session.class);
        User receiver = session.find(User.class, receiveUserId);

        Query<Message> query = session.createQuery("from Message where receiver = :receiver", Message.class);
        query.setParameter("receiver", receiver);
        return query.getResultList();
    }
}
