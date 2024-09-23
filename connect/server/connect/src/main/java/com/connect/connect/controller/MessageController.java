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

import com.connect.connect.model.dto.MessageDTO;
import com.connect.connect.model.entity.Message;
import com.connect.connect.model.service.message.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/v1/message")
public class MessageController {

    @Autowired
    private IMessageService messageService;

    @GetMapping("/get/{user1Id}/{user2Id}")
    public ResponseEntity<List<Message>> streamMessages(@PathVariable Integer user1Id, @PathVariable Integer user2Id) {
        List<Message> messages = messageService.getMessagesBetweenUsers(user1Id, user2Id);
        return ResponseEntity.ok(messages);
    }
    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody MessageDTO messageDTO) {
        Message message = messageService.sendMessage(messageDTO.getSenderId(), messageDTO.getReceiverId(), messageDTO.getMessageText());
        return ResponseEntity.ok(message);
    }

}