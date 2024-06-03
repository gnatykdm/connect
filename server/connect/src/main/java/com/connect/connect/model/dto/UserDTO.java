package com.connect.connect.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {

    @NotNull
    private String username;
    @NotNull
    private String email;
    @NotNull
    private String password;

    public UserDTO(String username, String email, String password) {
        this.username = username;
        this.password = password;
    }
}
