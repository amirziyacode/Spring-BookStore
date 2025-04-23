package org.example.bookstoreapp.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bookstoreapp.dto.UserDTO;
import org.example.bookstoreapp.repository.UserRepo;
import org.example.bookstoreapp.user.User;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class UserMapperImp implements UserMapper {

    private final UserRepo userRepo;


    @Override
    public UserDTO UserToUserDTO(String emailUser) {
        return userRepo.findByEmail(emailUser)
                .map(usr -> {
                    UserDTO userDTO = new UserDTO();
                    userDTO.setName(usr.getFullName());
                    userDTO.setEmail(usr.getEmail());
                    userDTO.setCity(usr.getCity());
                    userDTO.setCountry(usr.getCountry());
                    userDTO.setAddress(usr.getAddress());
                    userDTO.setPhone(usr.getPhoneNumber());
                    userDTO.setState(usr.getState());
                    userDTO.setZipCode(usr.getZipCode());
                    return userDTO;
                })
                .orElseThrow(() -> new RuntimeException("User not found"));
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
}
