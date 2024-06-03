package org.connect.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.connect.model.entities.User;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UserRequestClient {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = Logger.getLogger(UserRequestClient.class.getName());
    private static final String HEAD_URL = "http://localhost:8080/api/v1/auth";
    private HttpURLConnection conn;

    public boolean sendPostRequest(User user) throws Exception {
        logger.info("Sending POST request to register user");

        URL url = new URL(HEAD_URL + "/register");
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; utf-8");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);

        String jsonInputString = objectMapper.writeValueAsString(user);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            logger.severe("Failed : HTTP error code : " + responseCode);
            throw new RuntimeException("Failed : HTTP error code : " + responseCode);
        }

        logger.info("User registered successfully");
        return true;
    }

    public boolean loginUserRequest(String username, String password) throws Exception {
        logger.info("Sending POST request to login user");

        URL url = new URL(HEAD_URL + "/login");
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; utf-8");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);

        List<LoginPair> params = new ArrayList<>();
        params.add(new LoginPair("username", username));
        params.add(new LoginPair("password", password));

        try (OutputStream os = conn.getOutputStream();
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8))) {
            writer.write(getQuery(params));
            writer.flush();
        }

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
            logger.warning("Login conflict: Invalid login or password");
            return false;
        } else if (responseCode != HttpURLConnection.HTTP_OK) {
            logger.severe("Failed : HTTP error code : " + responseCode);
            throw new RuntimeException("Failed : HTTP error code : " + responseCode);
        }

        logger.info("User logged in successfully");
        return true;
    }

    private String getQuery(List<LoginPair> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (LoginPair pair : params) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        return result.toString();
    }
}
