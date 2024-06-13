package org.connect.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageDTO {
    private Integer senderId;
    private Integer receiverId;
    private String messageText;
    private LocalDateTime timestamp;

    public MessageDTO(Integer senderId, Integer receiverId, String messageText, LocalDateTime timestamp) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.messageText = messageText;
        this.timestamp = timestamp;
    }
}
