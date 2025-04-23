package org.example.bookstoreapp.service;

import lombok.RequiredArgsConstructor;
import org.example.bookstoreapp.dto.UserDTO;
import org.example.bookstoreapp.mapper.UserMapper;
import org.example.bookstoreapp.repository.UserRepo;
import org.example.bookstoreapp.user.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final UserRepo userRepo;
    private final UserMapper userMapper;

    public String setAccountDetails(String email, User user) {
       return userRepo.findByEmail(email).map(userUpdate -> {
           userUpdate.setFullName(user.getFullName());
            userUpdate.setPhoneNumber(user.getPhoneNumber());
            userUpdate.setAddress(user.getAddress());
            userUpdate.setCity(user.getCity());
            userUpdate.setCountry(user.getCountry());
            userUpdate.setZipCode(user.getZipCode());
            userUpdate.setState(user.getState());
            userRepo.save(userUpdate);
            return  "Account "+email+"details updated successfully";
        }).orElseThrow();
    }

    public UserDTO getAccountDetails(String email) {
        return userMapper.UserToUserDTO(email);
    }
}
