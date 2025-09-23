package org.example.bookstoreapp.user;


import org.example.bookstoreapp.jwtTokenAuthentication.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock UserRepo userRepo;
    @Mock PasswordEncoder passwordEncoder;

    @Test
    void createUser_Success() {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .email("test@gmail.com")
                .password("password")
                .fullName("AmirAli")
                .build();

        userService.createUser(registerRequest);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepo,times(1)).save(captor.capture());
        User actualUser = captor.getValue();

        assertThat(actualUser.getFullName()).isEqualTo(registerRequest.getFullName());
        assertThat(actualUser.getEmail()).isEqualTo(registerRequest.getEmail());
        assertThat(actualUser.getRole()).isEqualTo(Role.USER);

    }
}