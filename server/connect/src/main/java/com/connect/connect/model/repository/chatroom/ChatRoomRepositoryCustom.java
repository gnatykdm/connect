package com.connect.connect.model.repository.chatroom;

import com.connect.connect.model.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

public class ChatRoomRepositoryCustom implements IChatRoomRepositoryCustom {

    @PersistenceContext
    private EntityManager manager;


    @Override
    public Integer getChatRoomIdByUser(User user) {
        if (user == null) {
            throw new NullPointerException("User cannot be null");
        }

        Integer userId = user.getUserId();
        Query query = manager.createQuery("SELECT c.room FROM ChatRoom c WHERE c.user1.userId = :userId OR c.user2.userId = :userId");
        query.setParameter("userId", userId);

        return (Integer) query.getSingleResult();
    }
}
