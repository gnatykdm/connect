package org.connect.model.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import org.connect.model.entities.User;

@Data
@Entity
@NoArgsConstructor
@Table(name = "friendships")
public class Friendship {
    @Id
    @ManyToOne
    @JoinColumn(name = "user1_id")
    private User user1;

    @Id
    @ManyToOne
    @JoinColumn(name = "user2_id")
    private User user2;

    public Friendship(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;
    }
}
