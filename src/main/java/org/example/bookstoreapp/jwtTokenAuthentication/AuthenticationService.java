package org.example.bookstoreapp.jwtTokenAuthentication;

import lombok.RequiredArgsConstructor;
import org.example.bookstoreapp.emialVerification.EmailService;
import org.example.bookstoreapp.emialVerification.VerificationCode;
import org.example.bookstoreapp.emialVerification.VerificationCodeService;
import org.example.bookstoreapp.jwtToken.JwtService;
import org.example.bookstoreapp.user.UserRepo;
import org.example.bookstoreapp.user.Role;
import org.example.bookstoreapp.user.User;
import org.example.bookstoreapp.user.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final UserRepo userRepo;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final VerificationCodeService verificationCodeService;
    private final EmailService emailService;

    /**
     * This Method for Create User is not Excite in Database
     * Save User in Database
     * Generate JWT Token and Revoke All Tokens for one User
     * Save JWT Token in Database
     * Generate Verification Code and Send it by Email for verify Account
     *
     * @param registerRequest include (fullName,Email,Password)
     * @return AuthenticationResponse include JWT Token
     */
    public AuthenticationResponse register(RegisterRequest registerRequest) {
        Optional<User> byEmail = userRepo.findByEmail(registerRequest.getEmail());
        if(byEmail.isEmpty()) {
            try {
                User user = userService.createUser(registerRequest);

                emailService.sendVerificationCodeByEmail(user);

                String token = jwtService.generateToken(user);

                jwtService.revokeAllUserTokens(user);

                jwtService.saveUserToken(token, user);

                return AuthenticationResponse.builder()
                        .token(token)
                        .build();
            }catch (Exception e) {
                throw new RuntimeException("Failed to register user: " + e.getMessage());
            }
        }else {
            throw new IllegalArgumentException("Email already in use");
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
                        .equals(Role.ADMIN));
        if(userEmail.isPresent()) {
            String token = jwtService.generateToken(userEmail.get());
            jwtService.revokeAllUserTokens(userEmail.get());
            jwtService.saveUserToken(token, userEmail.get());
            return AuthenticationResponse.builder()
                    .token(token)
                    .isAdmin(true)
                    .build();
        }
        return userRepo.findByEmail(authenticationRequest.getEmail()).map(user -> {
            String token = jwtService.generateToken(user);
            jwtService.revokeAllUserTokens(user);
            jwtService.saveUserToken(token, user);
            return AuthenticationResponse.builder().token(token).build();
        })
                .orElseThrow(() -> new UsernameNotFoundException("User"+authenticationRequest.getEmail()+"not found"));
    }




}
