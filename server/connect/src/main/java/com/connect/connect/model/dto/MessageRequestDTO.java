package com.connect.connect.model.dto;

import lombok.Data;

@Data
public class MessageRequestDTO {
    private Integer senderId;
    private Integer receiverId;
    private String content;
}
