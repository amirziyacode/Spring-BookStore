package org.example.bookstoreapp.mapper;

import org.example.bookstoreapp.dto.UserDTO;
import org.example.bookstoreapp.user.User;


public interface UserMapper {
    UserDTO UserrToUserDTO(User user);
    User UserDtoToUser(UserDTO userDTO);
}
