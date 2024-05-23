package com.connect.connect.model.dao.message;

import com.connect.connect.model.entity.MessageEntity;

import java.util.List;

public interface IMessageDAO {
    List<MessageEntity> getAllMessages();
    void registerMessage(MessageEntity message);
    void deleteMessage(MessageEntity message);
}
