package org.connect.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@Table(name = "chatrooms")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatRoom implements Comparator<ChatRoom> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    @JsonProperty("room")
    private Integer room;

    @ManyToOne
    @JoinColumn(name = "user1_id", nullable = false)
    @JsonProperty("user1")
    private User user1;

    @ManyToOne
    @JoinColumn(name = "user2_id", nullable = false)
    @JsonProperty("user2")
    private User user2;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    @JsonProperty("messages")
    private Set<Message> messages;

    public ChatRoom(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;
    }

    @Override
    public int compare(ChatRoom o1, ChatRoom o2) {
        return o1.getRoom().compareTo(o2.getRoom());
    }

    @Override
    public String toString() {
        return "ChatRoom{" +
                "room=" + room +
                ", user1=" + user1 +
                ", user2=" + user2 +
                ", messages=" + messages +
                '}';
    }

}