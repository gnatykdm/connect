package com.connect.connect.model.service;

import com.connect.connect.model.entity.MessageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements IMessageService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public void convertAndSend(MessageEntity message) {
        String username = message.getReceiver().getUsername();
        messagingTemplate.convertAndSendToUser(username, "/topic/public", message);
    }
}
