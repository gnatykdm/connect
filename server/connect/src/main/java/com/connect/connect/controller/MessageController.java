package com.connect.connect.controller;

import com.connect.connect.model.entity.Message;
import com.connect.connect.model.entity.User;
import com.connect.connect.model.service.message.IMessageService;
import com.connect.connect.model.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {

    @Autowired
    private IMessageService messageService;
    @Autowired
    private IUserService userService;

    @PostMapping("/send/{senderId}/{receiverId}/{content}")
    public ResponseEntity<Message> sendMessage(@PathVariable Integer senderId, @PathVariable Integer receiverId, @RequestParam String content) {
        messageService.sendMessage(senderId, receiverId, content);

        User sender = userService.getUserById(senderId);
        User receiver = userService.getUserById(receiverId);
        Message message = new Message(sender, receiver, content, LocalDate.now());
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/delete/{messageId}")
    public ResponseEntity<String> messageDrop(@PathVariable Integer messageId) {
        messageService.deleteMessage(messageId);
        return ResponseEntity.ok("Message was deleted");
    }

    @GetMapping("/sent/{senderId}")
    public ResponseEntity<List<Message>> getMessagesBySender(@PathVariable Integer senderId) {
        return ResponseEntity.ok(messageService.getMessagesSentByUser(senderId));
    }

    @GetMapping("/receive/{receiverId}")
    public ResponseEntity<List<Message>> getMessageByReceiver(@PathVariable Integer receiverId) {
        return ResponseEntity.ok(messageService.getMessagesReceivedByUser(receiverId));
    }

    @GetMapping("/{messageId}")
    public ResponseEntity<Message> getMessage(@PathVariable Integer messageId) {
        Message message = messageService.getMessageById(messageId);
        return ResponseEntity.ok(message);
    }
}
