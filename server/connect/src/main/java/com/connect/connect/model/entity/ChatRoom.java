package com.connect.connect.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@Table(name = "chatrooms")
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer room;

    @ManyToOne
    @JoinColumn(name = "user1_id", nullable = false)
    private User user1;

    @ManyToOne
    @JoinColumn(name = "user2_id", nullable = false)
    private User user2;


    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private Set<Message> messages;

    public ChatRoom(User user1, User user2) {
        this.room = room;
        this.user1 = user1;
        this.user2 = user2;
    }

    @Override
    public String toString() {
        return "ChatRoomDTO{" +
                "room=" + room +
                ", user1=" + user1 +
                ", user2=" + user2 +
                ", messages=" + messages +
                '}';
    }
}
