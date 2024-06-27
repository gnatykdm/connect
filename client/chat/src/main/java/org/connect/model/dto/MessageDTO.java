package org.connect.model.dto;

import lombok.Data;

@Data
public class MessageDTO {
    private Integer senderId;
    private Integer receiverId;
    private String messageText;

    public MessageDTO(Integer senderId, Integer receiverId, String messageText) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.messageText = messageText;
    }
}
