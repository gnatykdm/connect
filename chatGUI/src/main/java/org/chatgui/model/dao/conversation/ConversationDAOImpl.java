package org.chatgui.model.dao.conversation;

import org.chatgui.model.entities.ConversationEntity;
import org.chatgui.model.hibernateconfiguration.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class ConversationDAOImpl implements IConversationDAO {
    @Override
    public List<ConversationEntity> getAllConversations() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query<ConversationEntity> query = session.createQuery("from ConversationEntity", ConversationEntity.class);
            List<ConversationEntity> conversationEntities = query.getResultList();
            session.getTransaction().commit();
            return conversationEntities;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}