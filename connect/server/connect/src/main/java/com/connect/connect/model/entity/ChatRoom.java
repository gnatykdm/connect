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
