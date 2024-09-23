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

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int userId;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "register_date", nullable = false)
    private LocalDateTime registerDate;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private Set<Message> sentMessages;

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    private Set<Message> receivedMessages;

    @OneToMany(mappedBy = "user1", cascade = CascadeType.ALL)
    private Set<ChatRoom> chatRooms1;

    @OneToMany(mappedBy = "user2", cascade = CascadeType.ALL)
    private Set<ChatRoom> chatRooms2;

    public User(String username, String email, String password, LocalDateTime registerDate) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.registerDate = registerDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", registerDate=" + registerDate +
                ", sentMessages=" + sentMessages +
                ", receivedMessages=" + receivedMessages +
                ", chatRooms1=" + chatRooms1 +
                ", chatRooms2=" + chatRooms2 +
                '}';
    }
}