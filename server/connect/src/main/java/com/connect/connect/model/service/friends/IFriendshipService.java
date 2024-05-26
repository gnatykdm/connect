package com.connect.connect.model.service.friends;

import com.connect.connect.model.entity.User;

import java.util.List;

public interface IFriendshipService {
    void addFriendship(Integer user1Id, Integer user2Id);

    void removeFriendship(Integer user1Id, Integer user2Id);

    boolean areFriends(Integer user1Id, Integer user2Id);

    List<User> getAllFriends(Integer userId);
}
