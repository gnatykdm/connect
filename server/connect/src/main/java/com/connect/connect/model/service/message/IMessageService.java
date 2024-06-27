package com.connect.connect.model.service.message;

import com.connect.connect.model.entity.Message;

import java.util.List;

public interface IMessageService {
    Message sendMessage(Integer senderId, Integer receiverId, String content);

    Message getMessageById(Integer messageId);

    List<Message> getMessagesBetweenUsers(Integer userId1, Integer userId2);

    List<Message> getNewMessages(Integer userId, Integer user2Id, List<Message> allMessages);
}