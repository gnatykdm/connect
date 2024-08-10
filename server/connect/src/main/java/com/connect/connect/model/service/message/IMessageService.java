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

package com.connect.connect.model.service.message;

import com.connect.connect.model.entity.Message;

import java.util.List;

public interface IMessageService {
    Message sendMessage(Integer senderId, Integer receiverId, String content);

    Message getMessageById(Integer messageId);

    List<Message> getMessagesBetweenUsers(Integer userId1, Integer userId2);

    List<Message> getNewMessages(Integer userId, Integer user2Id, List<Message> allMessages);
}