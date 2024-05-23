package com.connect.connect.controller;

import com.connect.connect.model.entity.MessageEntity;
import com.connect.connect.model.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@Controller
@EnableWebSocket
public class MainController {

    @Autowired
    private IMessageService messageService;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload MessageEntity message) {
        messageService.convertAndSend(message);
    }
}
