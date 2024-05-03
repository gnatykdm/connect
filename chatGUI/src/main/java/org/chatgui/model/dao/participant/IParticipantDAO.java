package org.chatgui.model.dao.participant;

import org.chatgui.model.entities.ParticipantEntity;

import java.util.List;

public interface IParticipantDAO {
    List<ParticipantEntity> getAllParticipants();
}
