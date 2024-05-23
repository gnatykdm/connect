package org.connect.model.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "participants")
public class ParticipantEntity {

    @Id
    @Column(name = "participant_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long participantId;

    @ManyToOne
    @JoinColumn(name = "conversation_id", referencedColumnName = "conversation_id")
    private ConversationEntity conversation;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserEntity user;

    public ParticipantEntity(ConversationEntity conversation, UserEntity user) {
        this.conversation = conversation;
        this.user = user;
    }
}

