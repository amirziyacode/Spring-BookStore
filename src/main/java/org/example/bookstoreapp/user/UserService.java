package org.example.bookstoreapp.user;

import lombok.RequiredArgsConstructor;
import org.example.bookstoreapp.jwtTokenAuthentication.RegisterRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public User createUser(RegisterRequest registerRequest){
        User buildUser = User.builder()
                .fullName(registerRequest.getFullName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.USER)
                .createdAt(LocalDate.now())
                .build();

      return   userRepo.save(buildUser);
    }
}
