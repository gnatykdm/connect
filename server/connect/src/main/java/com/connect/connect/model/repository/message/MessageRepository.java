package com.connect.connect.model.repository.message;

import com.connect.connect.model.entity.Message;
import com.connect.connect.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>, IMessageRepositoryCustom {
    List<Message> findBySender(User sender);

    List<Message> findByReceiver(User receiver);

    @Query("SELECT m FROM Message m WHERE m.sender.userId = :userId")
    List<Message> getMessagesSentByUser(Integer userId);

    @Query("SELECT m FROM Message m WHERE m.receiver.userId = :chatRoomId")
    List<Message> getMessageByChatRoomId(@Param("chatRoomId") Integer chatRoomId);
}