package org.example.bookstoreapp.service;

import lombok.RequiredArgsConstructor;
import org.example.bookstoreapp.repository.UserRepo;
import org.example.bookstoreapp.user.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;

    public String setAccountDetails(String email, User user) {
        Optional<User> userUpdate = userRepo.findByEmail(email);
        if(userUpdate.isPresent()) {
            userUpdate.get().setPhoneNumber(user.getPhoneNumber());
            userUpdate.get().setAddress(user.getAddress());
            userUpdate.get().setCity(user.getCity());
            userUpdate.get().setCountry(user.getCountry());
            userUpdate.get().setZipCode(user.getZipCode());
            userUpdate.get().setState(user.getState());

            userRepo.save(userUpdate.get());
            return  "Account"+email+"details updated successfully";
        }
        else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
