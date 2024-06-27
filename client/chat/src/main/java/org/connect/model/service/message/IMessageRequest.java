package org.connect.model.service.message;


import org.connect.model.entities.Message;
import java.util.List;

public interface IMessageRequest {
    Message sendMessage(Integer sender, Integer receiver, String content) throws Exception;

    List<Message> getMessages(Integer userId, Integer user2Id) throws Exception;
}
