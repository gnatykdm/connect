package org.connect.requests.message;

import org.connect.model.entities.Message;

public interface IMessageRequest {
    Message sendMessage(Integer sender, Integer receiver, String content) throws Exception;
}
