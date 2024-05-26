package com.connect.connect.model.service.friends;

import com.connect.connect.model.dao.friends.IFriendShipDAO;
import com.connect.connect.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FriendShipService implements IFriendshipService{

    @Autowired
    private IFriendShipDAO friendShipDAO;

    @Override
    @Transactional
    public void addFriendship(Integer user1Id, Integer user2Id) {
        friendShipDAO.addFriendShip(user1Id, user2Id);
    }

    @Override
    @Transactional
    public void removeFriendship(Integer user1Id, Integer user2Id) {
        friendShipDAO.removeFriendship(user1Id, user2Id);
    }

    @Override
    @Transactional
    public boolean areFriends(Integer user1Id, Integer user2Id) {
        return friendShipDAO.checkFriends(user1Id, user2Id);
    }

    @Override
    @Transactional
    public List<User> getAllFriends(Integer userId) {
        return friendShipDAO.getAllFriends(userId);
    }
}
