package org.connect.requests.message;


import org.connect.model.entities.Message;
import java.util.List;

public interface IMessageRequest {
    Message sendMessage(Integer sender, Integer receiver, String content) throws Exception;

    List<Message> getAllMessageSentByUser(Integer userId) throws Exception;

    List<Message> getMessagesByChatRoom(Integer chatRoomId) throws Exception;
}
