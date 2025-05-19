package org.example.bookstoreapp.jwtTokenAuthentication;

import lombok.RequiredArgsConstructor;
import org.example.bookstoreapp.jwtToken.JwtService;
import org.example.bookstoreapp.jwtToken.Token;
import org.example.bookstoreapp.jwtToken.TokenType;
import org.example.bookstoreapp.jwtToken.TokenRepo;
import org.example.bookstoreapp.user.UserRepo;
import org.example.bookstoreapp.user.Role;
import org.example.bookstoreapp.user.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepo tokenRepo;

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        Optional<User> byEmail = userRepo.findByEmail(registerRequest.getEmail());

        if(byEmail.isEmpty()) {
            User buildUser = User.builder()
                    .fullName(registerRequest.getFullName())
                    .email(registerRequest.getEmail())
                    .password(passwordEncoder.encode(registerRequest.getPassword()))
                    .role(Role.USER)
                    .build();
            userRepo.save(buildUser);
            String token = jwtService.generateToken(buildUser);
            revokeAllUserTokens(buildUser);
            saveUserToken(token, buildUser);
            return AuthenticationResponse.builder()
                    .token(token)
                    .build();
        }else {
            throw new UsernameNotFoundException("Email already in use");
        }
    }

    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail()
                        , authenticationRequest.getPassword()
                )
        );

        // Admin panel
        Optional<User> userEmail = userRepo.findByEmail(authenticationRequest.getEmail())
                .filter(user -> user
                        .getRole()
                        .equals(Role.USER));
        if(userEmail.isPresent()) {
            return AuthenticationResponse.builder()
                    .message("Welcome Back Admin !!")
                    .isAdmin(true)
                    .build();
        }
        return userRepo.findByEmail(authenticationRequest.getEmail()).map(user -> {
            String token = jwtService.generateToken(user);
            saveUserToken(token, user);
            return AuthenticationResponse.builder().token(token).build();
        })
                .orElseThrow(() -> new UsernameNotFoundException("User"+authenticationRequest.getEmail()+"not found"));
    }

    private void revokeAllUserTokens(User user) {
        List<Token> allValidTokensByUser = tokenRepo.findAllValidTokensByUser(user.getId());
        if(allValidTokensByUser.isEmpty()) {
            return;
        }
        allValidTokensByUser.forEach(token -> token.setRevoked(true));
        tokenRepo.saveAll(allValidTokensByUser);

    }

    private void saveUserToken(String token, User user) {
        Token buildToken = Token.builder()
                .token(token)
                .user(user)
                .revoked(false)
                .tokenType(TokenType.BEARER)
                .build();
        tokenRepo.save(buildToken);
    }
}
