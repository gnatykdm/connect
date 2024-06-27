package org.connect.model.service;

import lombok.Data;

@Data
public class LoginPair {
    private String name;
    private String value;

    public LoginPair(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
