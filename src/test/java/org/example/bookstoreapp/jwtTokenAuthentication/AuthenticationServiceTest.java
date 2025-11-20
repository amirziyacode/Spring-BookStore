package org.example.bookstoreapp.jwtTokenAuthentication;

import org.assertj.core.api.Assertions;

import org.example.bookstoreapp.emialVerification.VerificationCode;
import org.example.bookstoreapp.emialVerification.VerificationCodeService;
import org.example.bookstoreapp.jwtToken.JwtService;
import org.example.bookstoreapp.user.Role;
import org.example.bookstoreapp.user.User;
import org.example.bookstoreapp.user.UserRepo;
import org.example.bookstoreapp.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.time.LocalDateTime;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class AuthenticationServiceTest {

    @Mock private UserRepo userRepo;
    @Mock private UserService userService;
    @Mock private JwtService jwtService;
    @Mock private AuthenticationManager authenticationManager;
    @Mock private VerificationCodeService verificationCodeService;

    @InjectMocks
    private AuthenticationService authenticationService;


    private RegisterRequest registerRequest;
    private AuthenticationRequest  authenticationRequest;
    private User user;
    private VerificationCode verificationCode;
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

        verificationCode = VerificationCode.builder()
                .code("code")
                .expireTime(LocalDateTime.now().plusMinutes(3))
                .used(false)
                .user(user)
                .build();
    }

    @Test
    @DisplayName("registerUserSuccessfully")
    void should_registerUserSuccessfully_and_sendVerificationCode_By_Email_generate_JWT_TOKEN() {

        when(userRepo.findByEmail(registerRequest.getEmail())).thenReturn(Optional.empty());
        when(jwtService.generateToken(any(User.class))).thenReturn(TOKEN);
        when(userService.createUser(any(RegisterRequest.class))).thenReturn(user);
        when(verificationCodeService.generateVerificationCode(user)).thenReturn(verificationCode);

        AuthenticationResponse response = authenticationService.register(registerRequest);

        assertNotNull(response);
        assertEquals(TOKEN, response.getToken());

        verify(userService).createUser(registerRequest);
        verify(jwtService).generateToken(user);
        verify(jwtService).revokeAllUserTokens(user);
        verify(jwtService).saveUserToken(TOKEN, user);
        verify(verificationCodeService,times(1)).sendVerificationCodeByEmail(user);
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
        verify(jwtService).revokeAllUserTokens(user);
        verify(jwtService).saveUserToken(TOKEN, user);
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService).generateToken(user);
    }

    @Test
    void should_login_with_email_and_password_user_not_found() {
        UsernameNotFoundException usernameNotFoundException = assertThrows(UsernameNotFoundException.class, () -> authenticationService.login(authenticationRequest));
        Assertions.assertThat(usernameNotFoundException.getMessage()).isEqualTo("User"+authenticationRequest.getEmail()+"not found");

    }

    @Test
    void should_throwRuntimeException_whenCreateUserFails() {

        when(userRepo.findByEmail("test@gmail.com")).thenReturn(Optional.empty());
        when(userService.createUser(registerRequest)).thenThrow(new RuntimeException("DB error"));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> authenticationService.register(registerRequest));

        assertTrue(ex.getMessage().contains("Failed to register user"));
    }

    @Test
    void should_login_adminPanel(){
        User admin = User.builder()
                .fullName("Admin")
                .password("1234")
                .role(Role.ADMIN)
                .build();

        when(userRepo.findByEmail(authenticationRequest.getEmail())).thenReturn(Optional.of(admin));
        when(jwtService.generateToken(admin)).thenReturn(TOKEN);

        AuthenticationResponse adminLogin = authenticationService.login(authenticationRequest);

        assertThat(adminLogin).isEqualTo(AuthenticationResponse.builder().token(TOKEN).isAdmin(true).build());
        verify(jwtService,times(1)).generateToken(admin);
        verify(jwtService).revokeAllUserTokens(admin);
        verify(jwtService).saveUserToken(TOKEN, admin);
    }
}