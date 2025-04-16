package org.example.bookstoreapp.service;

import lombok.RequiredArgsConstructor;
import org.example.bookstoreapp.repository.UserRepo;
import org.example.bookstoreapp.user.AccountResponse;
import org.example.bookstoreapp.user.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {
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
        throw new UsernameNotFoundException("User not found");
    }

    public AccountResponse getAccountDetails(String email) {
        return userRepo.findByEmail(email)
                .map(user -> new AccountResponse(
                        user.getFullName(),
                        user.getPhoneNumber(),
                        user.getAddress(),
                        user.getCity(),
                        user.getCountry(),
                        user.getZipCode(),
                        user.getState()
                )).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
