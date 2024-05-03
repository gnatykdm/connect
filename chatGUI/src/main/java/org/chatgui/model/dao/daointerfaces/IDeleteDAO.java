package org.chatgui.model.dao.daointerfaces;

public interface IDeleteDAO<T> {
    void delete(T entity);
    void deleteById(T id);
}
