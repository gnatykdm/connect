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

package com.connect.connect.controller;

import com.connect.connect.model.entity.ChatRoom;
import com.connect.connect.model.entity.User;
import com.connect.connect.model.service.chatroom.IChatRoomService;
import com.connect.connect.model.service.user.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chatrooms")
public class ChatRoomController {

    @Autowired
    private IChatRoomService chatRoomService;

    @Autowired
    private IUserService userService;
    private static final Logger logger = LoggerFactory.getLogger(ChatRoomController.class);

    @PostMapping("/register/{user1Id}/{user2Id}")
    public ResponseEntity<ChatRoom> registerRoom(@PathVariable Integer user1Id, @PathVariable Integer user2Id) {

        logger.info("Registering chat room between users: {} and {}", user1Id, user2Id);

        User user1 = userService.getUserById(user1Id);
        User user2 = userService.getUserById(user2Id);

        ChatRoom chatRoom = new ChatRoom(user1, user2);
        chatRoomService.registerRoom(chatRoom);

        return ResponseEntity.ok(chatRoom);
    }

    @DeleteMapping("/drop/{roomId}")
    public ResponseEntity<?> dropRoom(@PathVariable Integer roomId) {

        logger.info("Dropping chat room with ID: {}", roomId);
        chatRoomService.dropRoom(roomId);
        return ResponseEntity.ok("Room was deleted");
    }

    @GetMapping("/all/{userId}")
    public ResponseEntity<List<ChatRoom>> getAllChatRoomById(@PathVariable Integer userId) {
        logger.info("Getting all chat rooms for user with ID: {}", userId);

        List<ChatRoom> allChatRooms = chatRoomService.findAllByUser1Id(userId);
        return ResponseEntity.ok(allChatRooms);
    }
}
