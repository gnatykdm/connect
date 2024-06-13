package com.connect.connect.model.service.message;

import com.connect.connect.model.entity.Message;
import com.connect.connect.model.entity.User;


import java.util.List;

public interface IMessageService {
    void sendMessage(Integer senderId, Integer receiverId, String content);

    void deleteMessage(Integer messageId);

    Message getMessageById(Integer messageId);

    List<Message> getMessagesSentByUser(Integer userId);

    List<Message> getMessagesReceivedByUser(Integer userId);

    List<Message> getMessagesBetweenUsers(Integer userId1, Integer userId2);
}