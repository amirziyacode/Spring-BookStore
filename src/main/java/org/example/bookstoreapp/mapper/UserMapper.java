package org.example.bookstoreapp.mapper;

import org.example.bookstoreapp.user.UserResponse;
import org.example.bookstoreapp.modelDTO.UserDTO;
import org.example.bookstoreapp.user.User;

import java.util.List;


public interface UserMapper {
    UserDTO UserToUserDTO(User user);
    User UserDtoToUser(UserDTO userDTO);
    UserResponse  UserToUserResponse(User user);
    List<UserResponse> UserToUserResponseList(List<User> users);
}
