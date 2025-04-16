package org.example.bookstoreapp.service;

import lombok.RequiredArgsConstructor;
import org.example.bookstoreapp.repository.UserRepo;
import org.example.bookstoreapp.user.AccountResponse;
import org.example.bookstoreapp.user.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class AccountService {
    private final UserRepo userRepo;

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
            return  "Account"+email+"details updated successfully";
        }).orElseThrow();
    }

    public AccountResponse getAccountDetails(String email) {
        return userRepo.findByEmail(email)
                .map(user -> new AccountResponse(
                        user.getFullName(),
                        user.getPhoneNumber(),
                        user.getEmail(),
                        user.getAddress(),
                        user.getCity(),
                        user.getState(),
                        user.getZipCode(),
                        user.getCountry()
                )).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
