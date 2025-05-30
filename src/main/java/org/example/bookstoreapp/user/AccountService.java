package org.example.bookstoreapp.user;

import lombok.RequiredArgsConstructor;
import org.example.bookstoreapp.modelDTO.UserDTO;
import org.example.bookstoreapp.mapper.UserMapper;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AccountService {
    private final UserRepo userRepo;
    private final UserMapper userMapper;

    public String setAccountDetails(String email, UserDTO userDTO) {
       return userRepo.findByEmail(email).map(userUpdate -> {
           User user = userMapper.UserDtoToUser(userDTO);
           userUpdate.setFullName(user.getFullName());
            userUpdate.setPhoneNumber(user.getPhoneNumber());
            userUpdate.setAddress(user.getAddress());
            userUpdate.setCity(user.getCity());
           userUpdate.setState(user.getState());
            userUpdate.setCountry(user.getCountry());
            userUpdate.setZipCode(user.getZipCode());
            userRepo.save(userUpdate);
            return  "Account "+email+" details updated successfully";
        }).orElseThrow(() -> new RuntimeException("Account not found"));
    }

    public UserDTO getAccountDetails(String email) {
        return userMapper.UserToUserDTO(userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Account not found")));
    }
}
