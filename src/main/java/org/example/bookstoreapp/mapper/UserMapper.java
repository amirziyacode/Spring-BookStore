package org.example.bookstoreapp.mapper;

import org.example.bookstoreapp.dto.UserDTO;
import org.example.bookstoreapp.user.User;


public interface UserMapper {
    UserDTO UserToUserDTO(User user);
    User UserDtoToUser(UserDTO userDTO);
}
