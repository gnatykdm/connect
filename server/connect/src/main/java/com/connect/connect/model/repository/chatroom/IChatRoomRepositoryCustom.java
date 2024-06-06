package com.connect.connect.model.repository.chatroom;

import com.connect.connect.model.entity.User;

public interface IChatRoomRepositoryCustom {
    Integer getChatRoomIdByUser(User user);
}
