package org.example.bookstoreapp.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bookstoreapp.admin.UserResponse;
import org.example.bookstoreapp.dto.UserDTO;
import org.example.bookstoreapp.user.User;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
@Slf4j
public class UserMapperImp implements UserMapper {


    @Override
    public UserDTO UserToUserDTO(User user) {
        if (user == null) {
            throw new RuntimeException("User Not Found");
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getFullName());
        userDTO.setEmail(user.getEmail());
        userDTO.setCity(user.getCity());
        userDTO.setCountry(user.getCountry());
        userDTO.setAddress(user.getAddress());
        userDTO.setPhone(user.getPhoneNumber());
        userDTO.setState(user.getState());
        userDTO.setZipCode(user.getZipCode());
        return userDTO;
    }

    @Override
    public User UserDtoToUser(UserDTO userDTO) {
        return  User.builder()
                .fullName(userDTO.getName())
                .email(userDTO.getEmail())
                .city(userDTO.getCity())
                .country(userDTO.getCountry())
                .address(userDTO.getAddress())
                .state(userDTO.getState())
                .zipCode(userDTO.getZipCode())
                .address(userDTO.getAddress())
                .phoneNumber(userDTO.getPhone())
                .build();
    }

    @Override
    public UserResponse UserToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getFullName())
                .isActive(user.isEnabled())
                .createdAt(String.valueOf(user.getCreatedAt()))
                .role(String.valueOf(user.getRole()))
                .build();
    }

    @Override
    public List<UserResponse> UserToUserResponseList(List<User> users) {
        return users.stream().map(this::UserToUserResponse).collect(Collectors.toList());
    }
}
