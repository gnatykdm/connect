package org.connect.requests.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.connect.model.dto.UserDTO;
import org.connect.model.entities.User;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Logger;

public class UserRequestClient implements IUserRequest {

    private static final Logger logger = Logger.getLogger(UserRequestClient.class.getName());
    private static final String BASE_URL = "http://localhost:8080/api/v1/auth";
    private static final ObjectMapper obj = new ObjectMapper();

    static {
        obj.registerModule(new JavaTimeModule());
    }

    public User registerUser(UserDTO user) throws Exception {
        URL url = new URL(BASE_URL + "/register");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        String data = obj.writeValueAsString(user);
        try (OutputStream os = conn.getOutputStream()) {
            byte[] buffer = data.getBytes();
            os.write(buffer, 0, buffer.length);
        }

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            return obj.readValue(conn.getInputStream(), User.class);
        } else {
            logger.info("Error: " + responseCode);
            return null;
        }
    }

    public User loginUserRequest(String username, String password) throws Exception {
        URL url = new URL(BASE_URL + "/login");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setDoOutput(true);

        String urlParameters = "username=" + URLEncoder.encode(username, "UTF-8") + "&password=" + URLEncoder.encode(password, "UTF-8");

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = urlParameters.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            return obj.readValue(conn.getInputStream(), User.class);
        } else {
            logger.info("Error: " + responseCode);
            return null;
        }
    }
}