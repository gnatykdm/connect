package org.chatgui.model.hibernateconfiguration;

public interface IDataInteraction {
    void saveData(Object object);
    void updateData(Object object);
    void deleteData(int index);
}
