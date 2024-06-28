package org.connect.model.service.chatroom;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.connect.model.entities.ChatRoom;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.logging.Logger;

public class ChatRoomRequest implements IChatRoomRequest {

    private static final Logger logger = Logger.getLogger(ChatRoomRequest.class.getName());
    private static final String URL = "http://localhost:8080/api/v1/chatrooms";
    private static final ObjectMapper obj = new ObjectMapper();

    static {
        obj.registerModule(new JavaTimeModule());
    }

    @Override
    public List<ChatRoom> getAllChatRooms(Integer userId) throws Exception {
        URL url = new URL(URL + "/all/" + userId);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        try (InputStream inputStream = conn.getInputStream()) {
            List<ChatRoom> chatRoomList = obj.readValue(inputStream, new TypeReference<List<ChatRoom>>() {});
            if (chatRoomList.isEmpty()) {
                logger.info("No chat rooms found");
            }
            return chatRoomList;
        } catch (Exception e) {
            logger.severe("Failed to get chat rooms");
            throw e;
        } finally {
            conn.disconnect();
        }
    }

    @Override
    public ChatRoom createChatRoom(Integer user1, Integer user2) throws Exception {
        URL url = new URL(URL + "/register/" + user1 + "/" + user2);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);
        conn.setDoOutput(true);

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            String jsonResponse = response.toString();
            logger.info("Response: " + jsonResponse);

            return obj.readValue(jsonResponse, ChatRoom.class);
        } else {
            logger.info("Error: " + responseCode);
            return null;
        }
    }

}
