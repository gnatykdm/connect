package org.connect.model.dao.daointerfaces;

public interface IDeleteDAO<T> {
    void delete(T entity);
    void deleteById(T id);
}
