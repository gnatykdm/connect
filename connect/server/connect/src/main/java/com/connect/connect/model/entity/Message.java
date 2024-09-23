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

@Data
@Entity
@NoArgsConstructor
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int messageId;

    @ManyToOne
    @JoinColumn(name = "chat_room_id", nullable = false)
    private ChatRoom room;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @Column(name = "message_text", nullable = false)
    private String messageText;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    public Message(ChatRoom room, User sender, User receiver, String messageText, LocalDateTime timestamp) {
        this.room = room;
        this.sender = sender;
        this.receiver = receiver;
        this.messageText = messageText;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", room=" + room +
                ", sender=" + sender +
                ", receiver=" + receiver +
                ", messageText='" + messageText + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}