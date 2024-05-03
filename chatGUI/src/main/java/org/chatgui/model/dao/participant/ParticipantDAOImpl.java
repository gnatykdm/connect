package org.chatgui.model.dao.participant;

import org.chatgui.model.entities.ParticipantEntity;
import org.chatgui.model.hibernateconfiguration.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class ParticipantDAOImpl implements IParticipantDAO {

    @Override
    public List<ParticipantEntity> getAllParticipants() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            Query<ParticipantEntity> participantEntityQuery = session.createQuery("from ParticipantEntity", ParticipantEntity.class);
            List<ParticipantEntity> participantEntities = participantEntityQuery.getResultList();
            session.getTransaction().commit();
            return participantEntities;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
