package org.connect.services.validation;

public interface ICheckUserDAO {
    boolean checkUserName(String userName);
    boolean checkUserPassword(String userPassword);
    boolean checkUserEmail(String userEmail);
}
