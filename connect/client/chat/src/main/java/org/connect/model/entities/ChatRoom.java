/*
 * @author Gnatyk Dmytro
 * This file is part of CONNECT.
 *
 * CONNECT is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * CONNECT is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with CONNECT. If not, see <https://www.gnu.org/licenses/>.
 */

package org.connect.model.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Comparator;
import java.util.Set;

@Getter
@Setter
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

    @JsonCreator
    public ChatRoom(
            @JsonProperty("user1") User user1,
            @JsonProperty("user2") User user2
    ) {
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