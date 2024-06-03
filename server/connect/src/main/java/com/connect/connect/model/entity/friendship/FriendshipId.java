package com.connect.connect.model.entity.friendship;

import lombok.Data;

import java.io.Serializable;

@Data
public class FriendshipId implements Serializable {
    private Integer user1;
    private Integer user2;
}