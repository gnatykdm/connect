package com.connect.connect.model.service;

import com.connect.connect.model.entity.MessageEntity;

public interface IMessageService {
    void convertAndSend(MessageEntity message);
}
