package com.connect.connect.model.repository.friends;

import com.connect.connect.model.entity.friendship.Friendship;
import com.connect.connect.model.entity.friendship.FriendshipId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendShipRepository extends JpaRepository<Friendship, FriendshipId> {
}
