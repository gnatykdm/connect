package org.chatgui.model.dao.daointerfaces;

import org.chatgui.model.dao.ConversationDAOImpl;

public interface IRegisterDAO<T> {
    void register(T entity);
    void update(T entity);
}
