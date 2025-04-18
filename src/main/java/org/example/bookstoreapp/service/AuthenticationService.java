package org.example.bookstoreapp.service;

import lombok.RequiredArgsConstructor;
import org.example.bookstoreapp.jwtAuth.AuthenticationRequest;
import org.example.bookstoreapp.jwtAuth.AuthenticationResponse;
import org.example.bookstoreapp.jwtAuth.RegisterRequest;
import org.example.bookstoreapp.jwtToken.Token;
import org.example.bookstoreapp.jwtToken.TokenType;
import org.example.bookstoreapp.repository.TokenRepo;
import org.example.bookstoreapp.repository.UserRepo;
import org.example.bookstoreapp.user.Role;
import org.example.bookstoreapp.user.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepo tokenRepo;

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        User buildUser = User.builder()
                .fullName(registerRequest.getFullName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.USER)
                .build();
        userRepo.save(buildUser);
        String token = jwtService.generateToken(buildUser);
        saveUserToken(token, buildUser);
        return AuthenticationResponse.builder()
                        .token(token)
                .build();
    }

    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail()
                        , authenticationRequest.getPassword()
                )
        );
        User user = userRepo.findByEmail(authenticationRequest.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String token = jwtService.generateToken(user);
        saveUserToken(token,user);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    private void saveUserToken(String token, User user) {
        Token buildToken = Token.builder()
                .token(token)
                .user(user)
                .expired(false)
                .tokenType(TokenType.BEARER)
                .build();
        tokenRepo.save(buildToken);
    }
}
