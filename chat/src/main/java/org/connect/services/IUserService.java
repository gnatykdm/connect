package org.connect.services;

public interface IUserService {
    boolean checkUserName(String userName);
    boolean checkUserPassword(String userPassword);
    boolean checkUserEmail(String userEmail);
}
