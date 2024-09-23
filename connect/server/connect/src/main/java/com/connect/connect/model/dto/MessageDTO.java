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

package com.connect.connect.model.dto;

import lombok.Data;

@Data
public class MessageDTO {
    private Integer senderId;
    private Integer receiverId;
    private String messageText;

    public MessageDTO(Integer senderId, Integer receiverId, String messageText) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.messageText = messageText;
    }
}
