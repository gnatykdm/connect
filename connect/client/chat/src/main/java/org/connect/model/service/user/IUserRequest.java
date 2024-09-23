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

import org.connect.model.dto.UserDTO;
import org.connect.model.entities.User;

public interface IUserRequest {
    User registerUser(UserDTO user) throws Exception;
    User loginUserRequest(String username, String password) throws Exception;
    User getUserByUsername(String username) throws Exception;

    void updateUserName(Integer id, String name);
    void updateUserEmail(Integer id, String email);
    void updateUserPassword(Integer id, String password);
}
