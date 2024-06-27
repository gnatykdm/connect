package com.connect.connect.model.repository.chatroom;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.connect.connect.model.entity.ChatRoom;
import com.connect.connect.model.entity.User;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {

    @Query("SELECT c FROM ChatRoom c WHERE c.user1.userId = :userId OR c.user2.userId = :userId")
    List<ChatRoom> getAllByUser1UserId(Integer userId);

    @Query("SELECT c FROM ChatRoom c WHERE c.user1 = :user1 AND c.user2 = :user2")
    ChatRoom findByUser1AndUser2(User user1, User user2);

    @Query("SELECT c.room FROM ChatRoom c WHERE c.user1.userId = :userId OR c.user2.userId = :userId")
    Integer getChatRoomIdByUser(User user);
}