package com.connect.connect.model.dao.friends;

import com.connect.connect.model.entity.User;

import java.util.List;

public interface IFriendShipDAO {
    void addFriendShip(Integer user1Id, Integer user2Id);
    void removeFriendship(Integer user1Id, Integer user2Id);
    boolean checkFriends(Integer user1Id, Integer user2Id);
    List<User> getAllFriends(Integer userId);
}
