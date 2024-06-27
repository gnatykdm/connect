package com.connect.connect.model.repository.message;

import com.connect.connect.model.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Query("SELECT m FROM Message m WHERE m.room.user1.userId = :userId1 AND m.room.user2.userId = :userId2")
    List<Message> getMessagesBetweenUsers(@Param("userId1") int userId1, @Param("userId2") int userId2);
}