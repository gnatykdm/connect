package com.connect.connect.model.dao.friends;

import com.connect.connect.model.entity.Friendship;
import com.connect.connect.model.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FriendShipDAOImpl implements IFriendShipDAO {

    @Autowired
    private EntityManager manager;

    @Override
    @Transactional
    public void addFriendShip(Integer user1Id, Integer user2Id) {
        Session session = manager.unwrap(Session.class);

        User user1 = session.get(User.class, user1Id);
        User user2 = session.get(User.class, user2Id);

        Friendship friendship = new Friendship(user1, user2);
        session.persist(friendship);
    }

    @Override
    @Transactional
    public void removeFriendship(Integer user1Id, Integer user2Id) {
        Session session = manager.unwrap(Session.class);

        // Get the Friendship entity
        Query<Friendship> query = session.createQuery(
                "FROM Friendship f WHERE (f.user1.id = :user1Id AND f.user2.id = :user2Id) " +
                        "OR (f.user1.id = :user2Id AND f.user2.id = :user1Id)",
                Friendship.class);
        query.setParameter("user1Id", user1Id);
        query.setParameter("user2Id", user2Id);
        Friendship friendship = query.uniqueResult();

        if (friendship != null) {
            session.remove(friendship);
        } else {
            System.out.println("Friendship does not exist between the specified users.");
        }
    }

    @Override
    @Transactional
    public boolean checkFriends(Integer user1Id, Integer user2Id) {
        Session session = manager.unwrap(Session.class);
        Query<Long> query = session.createQuery(
                "SELECT COUNT(f) FROM Friendship f " +
                        "WHERE (f.user1.id = :user1Id AND f.user2.id = :user2Id) " +
                        "OR (f.user1.id = :user2Id AND f.user2.id = :user1Id)",
                Long.class);
        query.setParameter("user1Id", user1Id);
        query.setParameter("user2Id", user2Id);

        long count = query.uniqueResult();
        return count > 0;
    }

    @Override
    public List<User> getAllFriends(Integer userId) {
        Session session = manager.unwrap(Session.class);
        Query<Friendship> query = session.createQuery(
                "SELECT f FROM Friendship f " +
                        "WHERE f.user1.id = :userId OR f.user2.id = :userId",
                Friendship.class);

        query.setParameter("userId", userId);
        List<Friendship> friendships = query.getResultList();

        List<User> friends = new ArrayList<>();
        for (Friendship friendship : friendships) {
            if (friendship.getUser1().getId() == userId) {
                friends.add(friendship.getUser2());
            } else {
                friends.add(friendship.getUser1());
            }
        }
        return friends;
    }
}
