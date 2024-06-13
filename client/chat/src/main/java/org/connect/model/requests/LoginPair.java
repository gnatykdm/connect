package org.connect.model.requests;

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
