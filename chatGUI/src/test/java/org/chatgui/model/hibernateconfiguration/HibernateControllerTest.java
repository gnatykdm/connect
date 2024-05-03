package org.chatgui.model.hibernateconfiguration;

import org.chatgui.model.dao.user.UserDAOImpl;
import org.chatgui.model.entities.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
class HibernateControllerTest {

    private UserEntity userEntity;
    private UserDAOImpl userDAOImpl;

    @BeforeEach
    public void setUp() {
        userEntity = new UserEntity("Dmytro", "12345", "example@gmail.com", LocalDate.now());
    }

    @Test
    public void testSaveData() {
        HibernateController hibernateController = new HibernateController();
        hibernateController.saveData(userEntity);
        assertNotNull(userDAOImpl.getAllUsers());
    }

    @Test
    public void testUpdateData() {
        HibernateController hibernateController = new HibernateController();
        hibernateController.saveData(userEntity);
        userEntity.setUserName("Dmytro123");
        hibernateController.updateData(userEntity);
        assertEquals("Dmytro123", userDAOImpl.getAllUsers().get(0).getUserName());
    }

    @Test
    public void testDeleteData() {
        HibernateController hibernateController = new HibernateController();
        hibernateController.saveData(userEntity);
        hibernateController.deleteData(1);
        assertTrue(userDAOImpl.getAllUsers().isEmpty());
    }

    @Test
    public void testGetData() {
        HibernateController hibernateController = new HibernateController();
        hibernateController.saveData(userEntity);
        assertEquals(userEntity, hibernateController.getData(1));
    }
}