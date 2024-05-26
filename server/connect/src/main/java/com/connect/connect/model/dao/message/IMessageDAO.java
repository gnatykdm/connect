package com.connect.connect.model.dao.message;

import com.connect.connect.model.entity.Message;

import java.util.List;

public interface IMessageDAO {
    void saveMessage(Message message);
    void updateMessage(Message message);
    void remoweMessage(Integer messageId);
    Message getMessageById(Integer messageId);
    List<Message> getMessageSendByUser(Integer sendUserId);
    List<Message> getMessageReceivedByUser(Integer receiveUserId);
}
