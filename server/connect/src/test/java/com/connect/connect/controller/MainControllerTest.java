package com.connect.connect.controller;

import com.connect.connect.model.entity.MessageEntity;
import com.connect.connect.model.service.IMessageService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;


import static org.mockito.Mockito.verify;

@SpringBootTest
public class MainControllerTest {

    @Mock
    private IMessageService messageService;

    @InjectMocks
    private MainController mainController;

    @Test
    public void testSendMessage() {
        // Prepare test message
        MessageEntity message = new MessageEntity();

        // Call the method under test
        mainController.sendMessage(message);

        // Verify that the messageService method is called with the correct message
        verify(messageService).convertAndSend(message);
    }
}