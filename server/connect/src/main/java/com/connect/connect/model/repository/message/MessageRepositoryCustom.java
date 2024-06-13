package com.connect.connect.model.repository.message;

import com.connect.connect.model.entity.Message;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.List;

public class MessageRepositoryCustom implements IMessageRepositoryCustom {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Message> getMessagesSentByUser(Integer userId) {

        if (userId == null) {
            throw new NullPointerException("User ID cannot be null");
        }

        Query query = manager.createQuery("from Message m where m.sender.userId = :userId");
        return query.getResultList();
    }

    @Override
    public List<Message> getMessageByChatRoomId(Integer chatRoomId) {
        if (chatRoomId == null) {
            throw new NullPointerException("Chat room ID cannot be null");
        }

        Query query = manager.createQuery("from Message m where m.receiver.userId = :chatRoomId", Message.class);
        query.setParameter("chatRoomId", chatRoomId);
        return query.getResultList();
    }
}
