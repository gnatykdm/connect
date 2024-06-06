package org.connect.requests.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.connect.model.entities.Message;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Logger;

public class MessageRequest implements IMessageRequest {

    private static final Logger logger = Logger.getLogger(MessageRequest.class.getName());
    private final String BASE_URL = "http://localhost:8080/api/v1/message-management/";
    private final ObjectMapper obj = new ObjectMapper();

    @Override
    public Message sendMessage(Integer sender, Integer receiver, String content) throws Exception {

        URL url = new URL(BASE_URL + "send/" + sender + "/" + receiver);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        logger.info("Sending message to " + receiver + " from " + sender);
        String urlParams = "content=" + URLEncoder.encode(content, "UTF-8");
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = urlParams.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            logger.info("Message sent successfully");
            return obj.readValue(conn.getInputStream(), Message.class);
        }

        logger.info("Error: " + responseCode);
        return null;
    }
}
