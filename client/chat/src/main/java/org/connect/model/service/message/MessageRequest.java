package org.connect.model.service.message;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.connect.model.dto.MessageDTO;
import org.connect.model.entities.Message;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.logging.Logger;

public class MessageRequest implements IMessageRequest {

    private static final Logger logger = Logger.getLogger(MessageRequest.class.getName());
    private final String MESSAGE_URL = "http://localhost:8080/api/v1/message";
    private final static ObjectMapper obj = new ObjectMapper();

    static {
        obj.registerModule(new JavaTimeModule());
    }

    @Override
    public Message sendMessage(Integer sender, Integer receiver, String content) throws Exception {
        URL url = new URL(MESSAGE_URL + "/send");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        MessageDTO messageDTO = new MessageDTO(sender, receiver, content);

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setReadTimeout(5000);
        conn.setConnectTimeout(5000);
        conn.setDoOutput(true);

        logger.info("Sending message to " + receiver + " from " + sender);
        String jsonFormatMessage = obj.writeValueAsString(messageDTO);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonFormatMessage.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            logger.info("Message sent successfully");
            try (InputStream is = conn.getInputStream()) {
                return obj.readValue(is, Message.class);
            }
        } else {
            logger.severe("Error: " + responseCode);
        }

        conn.disconnect();
        return null;
    }

    @Override
    public List<Message> getMessages(Integer userId, Integer user2Id) throws Exception {
        URL url = new URL(MESSAGE_URL + "/get/" + userId + "/" + user2Id);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setReadTimeout(5000);
        conn.setConnectTimeout(5000);

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (InputStream is = conn.getInputStream()) {
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                return obj.readValue(br, new TypeReference<List<Message>>() {
                });
            }
        } else {
            logger.severe("Error: " + responseCode);
        }

        conn.disconnect();
        return null;
    }
}
