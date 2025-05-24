package org.example.bookstoreapp.jwtTokenAuthentication;

import org.assertj.core.api.Assertions;
import org.example.bookstoreapp.jwtToken.JwtService;
import org.example.bookstoreapp.jwtToken.Token;
import org.example.bookstoreapp.jwtToken.TokenRepo;
import org.example.bookstoreapp.user.Role;
import org.example.bookstoreapp.user.User;
import org.example.bookstoreapp.user.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class AuthenticationServiceTest {

    @Mock private UserRepo userRepo;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private JwtService jwtService;
    @Mock private AuthenticationManager authenticationManager;
    @Mock private TokenRepo tokenRepo;

    @InjectMocks
    private AuthenticationService authenticationService;


    private RegisterRequest registerRequest;
    private AuthenticationRequest  authenticationRequest;
    private User user;
    private static final String TOKEN = "mocked-jwt-token";

    @BeforeEach
    void setUp() {

        registerRequest = RegisterRequest.builder()
                .fullName("John Doe")
                .email("john@example.com")
                .password("password123")
                .build();

        authenticationRequest = AuthenticationRequest.builder()
                .email("john@example.com")
                .password("password123")
                .build();

        user = User.builder()
                .fullName("John Doe")
                .email("john@example.com")
                .role(Role.USER)
                .password("password123")
                .build();
    }

    @Test
    void should_register_a_user_and_generateToken() {

        when(userRepo.findByEmail(registerRequest.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("encodedPassword");
        when(jwtService.generateToken(any(User.class))).thenReturn("mocked-jwt-token");


        AuthenticationResponse response = authenticationService.register(registerRequest);

        assertNotNull(response);
        assertEquals("mocked-jwt-token", response.getToken());
        verify(userRepo).save(any(User.class));
        verify(tokenRepo).save(any(Token.class));
    }

    @Test
    @DisplayName("revoke_all_tokens")
    void should_register_user_and_revoke_all_token_already_excite(){


        Token mockToken = Token.builder()
                .revoked(false)
                .user(user)
                .token(TOKEN)
                .build();

        when(tokenRepo.findAllValidTokensByUser(user.getId())).thenReturn(List.of(mockToken));
        when(userRepo.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(jwtService.generateToken(any(User.class))).thenReturn(TOKEN);

        AuthenticationResponse response = authenticationService.register(registerRequest);

        assertNotNull(response);
        assertEquals(TOKEN, response.getToken());
        verify(userRepo).save(any(User.class));
        verify(tokenRepo).save(any(Token.class));

    }


    @Test
    void should_register_user_and_email_excite(){


        when(userRepo.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        IllegalArgumentException usernameNotFoundException = assertThrows(IllegalArgumentException.class, () -> authenticationService.register(registerRequest));
        assertThat(usernameNotFoundException.getMessage()).isEqualTo("Email already in use");
    }

    @Test
    void should_login_with_email_and_password_generate_token() {


        when(userRepo.findByEmail(authenticationRequest.getEmail())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn(TOKEN);

        AuthenticationResponse response = authenticationService.login(authenticationRequest);

        assertNotNull(response);
        assertEquals(TOKEN, response.getToken());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService).generateToken(user);
    }

    @Test
    void should_login_with_email_and_password_user_not_found() {
        UsernameNotFoundException usernameNotFoundException = assertThrows(UsernameNotFoundException.class, () -> authenticationService.login(authenticationRequest));
        Assertions.assertThat(usernameNotFoundException.getMessage()).isEqualTo("User"+authenticationRequest.getEmail()+"not found");

    }
}