package com.connect.connect.model.dao.message;

import com.connect.connect.model.entity.MessageEntity;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MessageDAOImpl implements IMessageDAO {

    @Autowired
    private EntityManager manager;

    @Override
    @Transactional
    public List<MessageEntity> getAllMessages() {
        Session session = manager.unwrap(Session.class);
        List<MessageEntity> messages = session.createQuery("from MessageEntity", MessageEntity.class)
                .getResultList();
        return messages;
    }

    @Override
    @Transactional
    public void registerMessage(MessageEntity message) {
        Session session = manager.unwrap(Session.class);
        session.persist(message);
    }

    @Override
    @Transactional
    public void deleteMessage(MessageEntity message) {
        Session session = manager.unwrap(Session.class);
        session.remove(message);
    }
}
