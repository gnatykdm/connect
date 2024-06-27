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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.async.DeferredResult;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements IMessageService {

    private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private UserRepository userRepository;

    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    @Override
    @Transactional
    public Message sendMessage(Integer senderId, Integer receiverId, String content) {
        logger.info("Sending message from user: {} to user: {}", senderId, receiverId);
        User sender = userRepository.findByUserId(senderId);
        User receiver = userRepository.findByUserId(receiverId);

        ChatRoom chatRoom = chatRoomRepository.findByUser1AndUser2(sender, receiver);
        if (chatRoom == null) {
            chatRoom = new ChatRoom(sender, receiver);
            chatRoomRepository.save(chatRoom);
        }

        Message message = new Message(chatRoom, sender, receiver, content, LocalDateTime.now());
        messageRepository.save(message);
        return message;
    }

    @Override
    @Transactional
    public Message getMessageById(Integer messageId) {
        return messageRepository.findById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("Message not found"));
    }

    @Override
    @Transactional
    public List<Message> getMessagesBetweenUsers(Integer userId1, Integer userId2) {
        List<Message> all = new ArrayList<>();

        List<Message> senderToReceiver = messageRepository.getMessagesBetweenUsers(userId1, userId2);
        all.addAll(senderToReceiver);

        List<Message> receiverToSender = messageRepository.getMessagesBetweenUsers(userId2, userId1);
        all.addAll(receiverToSender);

        Set<Integer> messageIds = new HashSet<>();
        List<Message> uniqueMessages = new ArrayList<>();

        for (Message message : all) {
            if (messageIds.add(message.getMessageId())) {
                uniqueMessages.add(message);
            }
        }
        return uniqueMessages;
    }


    @Override
    public List<Message> getNewMessages(Integer userId, Integer user2Id, List<Message> result) {
        List<Message> allMessages = messageRepository.getMessagesBetweenUsers(userId, user2Id);

        Set<Integer> resultMessageIds = result.stream()
                .map(Message::getMessageId)
                .collect(Collectors.toSet());

        List<Message> newMessages = allMessages.stream()
                .filter(message -> !resultMessageIds.contains(message.getMessageId()))
                .collect(Collectors.toList());

        return newMessages;
    }
}