package org.connect.requests.chatroom;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.connect.model.entities.ChatRoom;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.logging.Logger;

public class ChatRoomRequest implements IChatRoomRequest {

    private static final Logger logger = Logger.getLogger(ChatRoomRequest.class.getName());
    private final String URL = "http://localhost:8080/api/v1/chatrooms";
    private final ObjectMapper obj = new ObjectMapper();

    @Override
    public List<ChatRoom> getAllChatRooms(Integer userId) throws Exception {
        URL url = new URL(URL + "/all/" + userId);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        try {
            List<ChatRoom> chatRoomList = obj.readValue(conn.getInputStream(), List.class);
            if (chatRoomList.isEmpty()) {
                logger.info("No chat rooms found");
                return null;
            } else {
                return chatRoomList;
            }
        } catch (Exception e) {
            logger.info("Error: " + e.getMessage());
            return null;
        }
    }

    @Override
    public ChatRoom createChatRoom(Integer user1, Integer user2) throws Exception {
        URL url = new URL(URL + "/register/" + user1 + "/" + user2);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            return obj.readValue(conn.getInputStream(), ChatRoom.class);
        } else {
            logger.info("Error: " + responseCode);
            return null;
        }
    }
}
