package org.connect.model.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String userName;
    private String userPassword;

    public UserDTO(String userName, String userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;
    }
}
