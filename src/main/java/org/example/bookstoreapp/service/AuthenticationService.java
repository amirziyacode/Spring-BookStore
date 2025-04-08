package org.example.bookstoreapp.service;

import lombok.RequiredArgsConstructor;
import org.example.bookstoreapp.Models.book.jwtToken.Token;
import org.example.bookstoreapp.Models.book.jwtToken.TokenType;
import org.example.bookstoreapp.Models.book.user.User;
import org.example.bookstoreapp.jwtAutch.AuthenticationRequest;
import org.example.bookstoreapp.jwtAutch.AuthenticationResponse;
import org.example.bookstoreapp.jwtAutch.RegisterRequest;
import org.example.bookstoreapp.repository.TokenRepo;
import org.example.bookstoreapp.repository.UserRepo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserRepo repository;
    private final TokenRepo tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthenticationResponse register(RegisterRequest request){
        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        User saveUser = repository.save(user);
        String token = jwtService.generateToken(saveUser);
        savedUserToken(user,token);
        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse login(AuthenticationRequest request) throws AuthenticationException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail()
                        , request.getPassword()
                )
        );
        User user = repository.findByUsername(request.getEmail()).orElseThrow(() -> new AuthenticationException("User not found"));
        String jwtToken = jwtService.generateToken(user);
        savedUserToken(user,jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }
    private void savedUserToken(User user, String token) {
        Token buildToken = Token.builder()
                .user(user)
                .token(token)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .build();
        tokenRepository.save(buildToken);
    }
}
