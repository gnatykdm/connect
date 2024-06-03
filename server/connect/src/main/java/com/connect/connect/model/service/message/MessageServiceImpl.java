package com.connect.connect.model.service.message;

import com.connect.connect.model.entity.Message;
import com.connect.connect.model.entity.User;
import com.connect.connect.model.repository.message.MessageRepository;
import com.connect.connect.model.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements IMessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void sendMessage(Integer senderId, Integer receiverId, String content) {
        User sender = userRepository.findByUserId(senderId);
        User receiver = userRepository.findByUserId(receiverId);

        if (sender == null || receiver == null) {
            throw new IllegalArgumentException("Invalid sender ID or receiver ID");
        }
        Message message = new Message(sender, receiver, content, LocalDate.now());
        messageRepository.save(message);
    }

    @Override
    public void deleteMessage(Integer messageId) {
        messageRepository.deleteById(messageId);
    }

    @Override
    public Message getMessageById(Integer messageId) {
        Optional<Message> message = messageRepository.findById(messageId);
        return message.orElse(null);
    }

    @Override
    public List<Message> getMessagesSentByUser(Integer userId) {
        User sender = userRepository.findByUserId(userId);
        return messageRepository.findBySender(sender);
    }

    @Override
    public List<Message> getMessagesReceivedByUser(Integer userId) {
        User receiver = userRepository.findByUserId(userId);
        return messageRepository.findByReceiver(receiver);
    }
}
