package org.connect.services;

import org.connect.services.validation.UserCheckDAOImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserCheckDAOImplTest {

    private UserCheckDAOImpl userCheckDAO;

    @BeforeEach
    public void setUp() {
        userCheckDAO = new UserCheckDAOImpl();
    }

    @Test
    void checkUserName() {
        assertFalse(userCheckDAO.checkUserName("lallaa"));

    }

    @Test
    void checkUserPassword() {
        assertTrue(userCheckDAO.checkUserPassword("Dmytro_2004"));
    }

    @Test
    void checkUserEmail() {
        assertFalse(userCheckDAO.checkUserEmail("simple@gmail.com"));
    }
}