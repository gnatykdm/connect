package org.connect.model.requests.chatroom;

import org.connect.model.entities.ChatRoom;

import java.util.List;

public interface IChatRoomRequest {
    List<ChatRoom> getAllChatRooms(Integer userId) throws Exception;

    ChatRoom createChatRoom(Integer user1, Integer user2) throws Exception;
}
