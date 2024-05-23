package org.connect.model.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "conversations")
public class ConversationEntity {

    @Id
    @Column(name = "conversation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long conversationId;

    @Column(name = "conversation_name")
    private String conversationName;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL)
    private List<MessageEntity> messages;

    public ConversationEntity(String conversationName, LocalDate creationDate) {
        this.conversationName = conversationName;
        this.creationDate = creationDate;
    }
}