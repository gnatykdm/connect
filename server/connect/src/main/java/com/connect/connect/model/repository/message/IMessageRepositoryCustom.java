package com.connect.connect.model.repository.message;

import com.connect.connect.model.entity.Message;

import java.util.List;

public interface IMessageRepositoryCustom {
    List<Message> getMessagesSentByUser(Integer userId);
}
