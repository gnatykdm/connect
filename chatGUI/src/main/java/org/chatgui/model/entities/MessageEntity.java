package org.chatgui.model.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(name = "messages")
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long messageId;

    @NotNull
    @Column(name = "conversation_id")
    private Long conversationId;

    @NotNull
    @Column(name = "sender_id")
    private Long senderId;

    @NotNull
    @Column(name = "message_text")
    private String messageText;

    @NotNull
    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    @ManyToOne
    @JoinColumn(name = "conversation_id", referencedColumnName = "conversation_id", insertable = false, updatable = false)
    private ConversationEntity conversation;

    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private UserEntity sender;

    public MessageEntity(Long conversationId, Long senderId, String messageText, LocalDateTime sentAt) {
        this.conversationId = conversationId;
        this.senderId = senderId;
        this.messageText = messageText;
        this.sentAt = sentAt;
    }
}
