package org.connect.model.dao.daointerfaces;

public interface IRegisterDAO<T> {
    void register(T entity);
    void update(T entity);
}
