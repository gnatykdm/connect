/*
 * @author Gnatyk Dmytro
 * This file is part of CONNECT.
 *
 * CONNECT is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * CONNECT is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with CONNECT. If not, see <https://www.gnu.org/licenses/>.
 */

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

public class UserRequest implements IUserRequest {

    private static final Logger logger = Logger.getLogger(UserRequest.class.getName());
    private static final String BASE_URL = "http://localhost:8080/api/v1/auth";
    private static final String USER_URL = "http://localhost:8080/api/v1/user-management";
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
    public User getUserByUsername(String username) throws Exception {
        URL url = new URL(USER_URL + "/find?username=" + username);
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
    public void updateUserName(Integer id, String name) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(USER_URL + "/update-name/" + id);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setDoOutput(true);

            String urlParameters = "username=" + name;
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);

            try (OutputStream os = connection.getOutputStream()) {
                os.write(postData);
                logger.info(String.valueOf(connection.getResponseCode()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    @Override
    public void updateUserEmail(Integer id, String email) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(USER_URL + "/update-email/" + id);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setDoOutput(true);

            String urlParameters = "email=" + email;
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);

            try (OutputStream os = connection.getOutputStream()) {
                os.write(postData);
                logger.info(String.valueOf(connection.getResponseCode()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    @Override
    public void updateUserPassword(Integer id, String password) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(USER_URL + "/update-password/" + id);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setDoOutput(true);

            String urlParameters = "password=" + password;
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);

            try (OutputStream os = connection.getOutputStream()) {
                os.write(postData);
                logger.info(String.valueOf(connection.getResponseCode()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}