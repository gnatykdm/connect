package org.chatgui.model.dao.message;

import org.chatgui.model.entities.MessageEntity;

import java.util.List;

public interface IMessageDAO {
    List<MessageEntity> getAllMessages();
}
