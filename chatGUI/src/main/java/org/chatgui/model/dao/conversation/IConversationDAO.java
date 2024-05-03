package org.chatgui.model.dao.conversation;

import org.chatgui.model.entities.ConversationEntity;

import java.util.List;

public interface IConversationDAO {
    List<ConversationEntity> getAllConversations();
}
