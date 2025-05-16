package org.example.bookstoreapp.user;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@DataJpaTest
class UserRepoTest {

    @Mock
    private UserRepo userRepo;

    @Test
    void findByEmail() {
        User user = User.builder().email("email").build();
        when(userRepo.findByEmail(anyString())).thenReturn(Optional.of(user));

        Optional<User> byEmail = userRepo.findByEmail("email");

        assertTrue(byEmail.isPresent());
        assertEquals(user, byEmail.get());
    }
}