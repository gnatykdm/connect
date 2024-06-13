package com.connect.connect.controller;

import com.connect.connect.model.entity.Message;
import com.connect.connect.model.service.message.IMessageService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/message-management")
public class MessageController {

    @Autowired
    private IMessageService messageService;

    @PostMapping("/send/{senderId}/{receiverId}")
    public ResponseEntity<?> sendMessage(@NotNull @PathVariable Integer senderId,
                                         @NotNull @PathVariable Integer receiverId,
                                         @NotNull @RequestParam String content) {
        messageService.sendMessage(senderId, receiverId, content);
        return ResponseEntity.ok("Message sent");
    }

    @GetMapping("/all/{userId}")
    public ResponseEntity<?> getAllMessages(@PathVariable String userId) {
        try {
            Integer userIdInt = Integer.parseInt(userId);
            List<Message> messages = messageService.getMessagesSentByUser(userIdInt);
            return ResponseEntity.ok(messages);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid user ID format");
        }
    }

    @GetMapping("/all-chatroom/{userSender}/{userReceiver}")
    public ResponseEntity<List<Message>> getAllByChatRoomId(@PathVariable Integer userSender,
                                                            @PathVariable Integer userReceiver) {
        List<Message> messageReceived = messageService.getMessagesBetweenUsers(userSender, userReceiver);
        return ResponseEntity.ok(messageReceived);
    }
}