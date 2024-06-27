package org.connect.model.service.user;

import org.connect.model.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class UserRequestClientTest {

    private UserRequestClient userRequestClient;

    @BeforeEach
    public void setUp() {
        userRequestClient = new UserRequestClient();
    }

    @Test
    void loginUserIdRequest() throws Exception {
        String username = "gnatykdm";
        String password = "Dmytro_2004";

        User user = userRequestClient.loginUserRequest(username, password);
        assertNotNull(user.getUserId(), "User ID is null");
        assertEquals(55, user.getUserId(), "User ID is not equal to 55");
    }
}