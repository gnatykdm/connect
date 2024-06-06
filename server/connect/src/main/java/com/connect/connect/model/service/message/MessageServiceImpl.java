package com.connect.connect.model.service.message;

import com.connect.connect.model.entity.ChatRoom;
import com.connect.connect.model.entity.Message;
import com.connect.connect.model.entity.User;
import com.connect.connect.model.repository.chatroom.ChatRoomRepository;
import com.connect.connect.model.repository.message.MessageRepository;
import com.connect.connect.model.repository.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements IMessageService {

    private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void sendMessage(Integer senderId, Integer receiverId, String content) {

        logger.info("Sending message from user: {} to user: {}", senderId, receiverId);
        Optional<User> senderOpt = userRepository.findById(senderId);
        Optional<User> receiverOpt = userRepository.findById(receiverId);

        logger.info("Sender: {}", senderOpt);
        if (senderOpt.isPresent() && receiverOpt.isPresent()) {
            User sender= senderOpt.get();
            User receiver = receiverOpt.get();

            ChatRoom chatRoom = chatRoomRepository.findByUser1AndUser2(sender, receiver);
            if (chatRoom == null) {
                chatRoom = new ChatRoom(sender, receiver);
            }

            logger.info("Chat room: {}", chatRoom);
            chatRoomRepository.save(chatRoom);

            logger.info("Chat room saved");
            Message message = new Message(chatRoom, sender, receiver, content, LocalDateTime.now());

            logger.info("Message: {}", message);
            messageRepository.save(message);
        } else {
            logger.info("Invalid sender or receiver");
            throw new IllegalArgumentException("Invalid sender or receiver");
        }
    }

    @Override
    public void deleteMessage(Integer messageId) {
        Optional<Message> message = messageRepository.findById(messageId);
        if (message.isPresent()) {
            logger.info("Deleting message: {}", message.get());
            messageRepository.delete(message.get());
        } else {
            logger.info("Message not found");
            throw new IllegalArgumentException("Message not found");
        }
    }

    @Override
    public Message getMessageById(Integer messageId) {
        logger.info("Getting message by id: {}", messageId);
        return messageRepository.findById(messageId).orElseThrow(() -> new IllegalArgumentException("Message not found"));
    }

    @Override
    public List<Message> getMessagesSentByUser(Integer userId) {
        logger.info("Getting messages sent by user: {}", userId);
        return messageRepository.findBySender(userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found")));
    }

    @Override
    public List<Message> getMessagesReceivedByUser(Integer userId) {
        logger.info("Getting messages received by user: {}", userId);
        return messageRepository.findByReceiver(userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found")));
    }
}
