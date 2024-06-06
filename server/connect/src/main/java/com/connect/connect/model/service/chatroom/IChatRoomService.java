package com.connect.connect.model.service.chatroom;

import com.connect.connect.model.entity.ChatRoom;

import java.util.List;

public interface IChatRoomService {
    List<ChatRoom> findAllByUser1Id(Integer userId);

    void registerRoom(ChatRoom chatRoom);

    void dropRoom(Integer roomId);
}
