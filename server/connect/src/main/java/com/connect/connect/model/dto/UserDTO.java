package com.connect.connect.model.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String password;

    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
