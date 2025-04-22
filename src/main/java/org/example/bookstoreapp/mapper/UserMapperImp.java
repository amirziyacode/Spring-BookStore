package org.example.bookstoreapp.mapper;

import lombok.RequiredArgsConstructor;
import org.example.bookstoreapp.dto.UserDTO;
import org.example.bookstoreapp.repository.UserRepo;
import org.example.bookstoreapp.user.User;

@RequiredArgsConstructor
public class UserMapperImp implements UserMapper {

    private final UserRepo userRepo;


    @Override
    public UserDTO UserrToUserDTO(User user) {
        userRepo.findByEmail(user.getEmail()).map(usr -> {
            UserDTO userDTO = new UserDTO();
            userDTO.setName(usr.getFullName());
            userDTO.setEmail(usr.getEmail());
            userDTO.setCity(usr.getCity());
            userDTO.setCountry(user.getCountry());
            userDTO.setAddress(usr.getAddress());
            userDTO.setPhone(usr.getPhoneNumber());
            userDTO.setState(usr.getState());
            userDTO.setZipCode(usr.getZipCode());
          return userDTO;
        });
        throw new RuntimeException("User not found");
    }

    @Override
    public User UserDtoToUser(UserDTO userDTO) {
        return null;
    }
}
