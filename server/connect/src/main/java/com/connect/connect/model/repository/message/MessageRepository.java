package com.connect.connect.model.repository.message;

import com.connect.connect.model.entity.Message;
import com.connect.connect.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findBySender(User sender);
    List<Message> findByReceiver(User receiver);
}
