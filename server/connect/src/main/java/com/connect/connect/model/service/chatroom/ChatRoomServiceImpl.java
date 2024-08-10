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

package com.connect.connect.model.service.chatroom;

import com.connect.connect.model.entity.ChatRoom;
import com.connect.connect.model.repository.chatroom.ChatRoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatRoomServiceImpl implements IChatRoomService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    private static final Logger logger = LoggerFactory.getLogger(ChatRoomServiceImpl.class);

    @Override
    public List<ChatRoom> findAllByUser1Id(Integer userId) {
        logger.info("Getting all chat rooms by user1 ID: {}", userId);
        return chatRoomRepository.getAllByUser1UserId(userId);
    }

    @Override
    public void registerRoom(ChatRoom chatRoom) {
        logger.info("Registering chat room: {}", chatRoom);

        if (chatRoom == null) {
            throw new NullPointerException("Chat room cannot be null");
        }

        chatRoomRepository.save(chatRoom);
    }

    @Override
    public void dropRoom(Integer roomId) {
        logger.info("Dropping chat room with ID: {}", roomId);
        chatRoomRepository.deleteById(roomId);
    }
}
