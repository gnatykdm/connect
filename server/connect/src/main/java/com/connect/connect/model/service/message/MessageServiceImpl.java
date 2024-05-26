package com.connect.connect.model.service.message;

import com.connect.connect.model.dao.message.IMessageDAO;
import com.connect.connect.model.dao.message.MessageDAOImpl;
import com.connect.connect.model.dao.user.IUserDAO;
import com.connect.connect.model.dao.user.UserDAOImpl;
import com.connect.connect.model.entity.Message;
import com.connect.connect.model.entity.User;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MessageServiceImpl implements IMessageService {

    @Autowired
    private MessageDAOImpl messageDAO;
    @Autowired
    private UserDAOImpl userDAO;

    @Transactional
    public void sendMessage(Integer senderId, Integer receiverId, String content) {
        User sender = userDAO.getUserById(senderId);
        User receiver = userDAO.getUserById(receiverId);

        if (sender == null || receiver == null) {
            throw new IllegalArgumentException("Invalid sender ID or receiver ID");
        }
        Message message = new Message(sender, receiver, content, LocalDate.now());
        messageDAO.saveMessage(message);
    }

    @Override
    @Transactional
    public void deleteMessage(Integer messageId) {
        messageDAO.remoweMessage(messageId);
    }

    @Override
    @Transactional
    public Message getMessageById(Integer messageId) {
        return messageDAO.getMessageById(messageId);
    }

    @Override
    @Transactional
    public List<Message> getMessagesSentByUser(Integer userId) {
        return messageDAO.getMessageSendByUser(userId);
    }

    @Override
    @Transactional
    public List<Message> getMessagesReceivedByUser(Integer userId) {
        return messageDAO.getMessageReceivedByUser(userId);
    }
}
