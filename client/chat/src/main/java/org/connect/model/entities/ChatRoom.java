package org.connect.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@Table(name = "chatrooms")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatRoom {

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
}