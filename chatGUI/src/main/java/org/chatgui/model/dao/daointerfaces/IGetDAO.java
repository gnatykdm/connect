package org.chatgui.model.dao.daointerfaces;

import java.util.List;

public interface IGetDAO<T> {
    List<T> getAll();
    T getById(int id);
}
