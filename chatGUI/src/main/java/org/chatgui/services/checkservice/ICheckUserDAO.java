package org.chatgui.services;

public interface ICheckUserDAO {
    boolean checkUserName(String userName);
    boolean checkUserPassword(String userPassword);
}
