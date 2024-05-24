package org.connect.util;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

public class WebSocketClient {

    private final WebSocketStompClient stompClient;
    private StompSession session;

    public WebSocketClient() {
        StandardWebSocketClient client = new StandardWebSocketClient();
        this.stompClient = new WebSocketStompClient(client);
        this.stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.afterPropertiesSet();
        this.stompClient.setTaskScheduler(taskScheduler);
    }

    public void connect(String url) {
        this.stompClient.connect(url, new StompSessionHandlerAdapter() {
            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                WebSocketClient.this.session = session;
                System.out.println("Connected to WebSocket server at " + url);
            }
        });
    }

    public void sendMessage(String destination, Object payload) {
        if (this.session != null && this.session.isConnected()) {
            this.session.send(destination, payload);
        } else {
            System.out.println("No active WebSocket session. Connect first.");
        }
    }
}
