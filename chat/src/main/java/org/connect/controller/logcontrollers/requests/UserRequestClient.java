package org.connect.controller.logcontrollers.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.connect.model.entities.User;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UserRequestClient {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void sendPostRequest(User user) {
        try {
            URL url = new URL("http://localhost:8080/user/register");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            String jsonInputString = objectMapper.writeValueAsString(user);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes();
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : " + responseCode);
            }

        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace();
        }
    }
}
