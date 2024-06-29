package org.connect.model.service.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.connect.model.dto.UserDTO;
import org.connect.model.entities.User;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class UserRequestClient implements IUserRequest {

    private static final Logger logger = Logger.getLogger(UserRequestClient.class.getName());
    private static final String BASE_URL = "http://localhost:8080/api/v1/auth";
    private static final String USER_URL = "http://localhost:8080/api/v1/user-management/";
    private static final ObjectMapper obj = new ObjectMapper();

    static {
        obj.registerModule(new JavaTimeModule());
    }

    public User registerUser(UserDTO user) throws Exception {
        URL url = new URL(BASE_URL + "/register");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        try {
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
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
        } finally {
            conn.disconnect();
        }
    }

    public User loginUserRequest(String username, String password) throws Exception {
        URL url = new URL(BASE_URL + "/login");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        try {
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setDoOutput(true);

            String urlParameters = "username=" + URLEncoder
                    .encode(username, "UTF-8") + "&password=" + URLEncoder.encode(password, "UTF-8");

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = urlParameters.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());
                try (InputStream inputStream = conn.getInputStream()) {
                    return objectMapper.readValue(inputStream, User.class);
                }
            } else {
                logger.info("Error: " + responseCode);
                return null;
            }
        } finally {
            conn.disconnect();
        }
    }

    @Override
    public User getUserById(Integer userId) throws Exception {
        URL url = new URL(USER_URL + userId);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        try {
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return obj.readValue(conn.getInputStream(), User.class);
            } else {
                logger.info("Error: " + responseCode);
                return null;
            }
        } finally {
            conn.disconnect();
        }
    }

    @Override
    public User getUserByUsername(String username) throws Exception {
        URL url = new URL(USER_URL + "find?username=" + username);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        try {
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setDoOutput(true);

            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return obj.readValue(httpURLConnection.getInputStream(), User.class);
            } else {
                logger.info("Error: " + responseCode);
                return null;
            }
        } finally {
            httpURLConnection.disconnect();
        }
    }

    @Override
    public void updateUserName(String name) {

    }

    @Override
    public void updateUserEmail(String email) {

    }

    @Override
    public void updateUserPassword(String password) {

    }
}