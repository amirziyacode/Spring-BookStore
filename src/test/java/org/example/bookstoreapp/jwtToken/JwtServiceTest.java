package org.example.bookstoreapp.jwtToken;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Base64;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(SpringExtension.class)
class JwtServiceTest {


    private JwtService jwtService;


    private final String secret = Base64.getEncoder().encodeToString("404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970".getBytes());

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secretKey", secret);
        // 1 hour
        long expiration = 2000L;
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", expiration);
    }

    @Test
    void should_extractUsername_and_generateToken() {
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("amirali");

        String token = jwtService.generateToken(userDetails);
        String username = jwtService.extractUsername(token);

        assertEquals("amirali", username);
    }


    @Test
    void isTokenValid() {
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("amirali");
        String token = jwtService.generateToken(userDetails);
        boolean tokenValid = jwtService.isTokenValid(token, userDetails);

        assertTrue(tokenValid);

    }

    @Test
    void isTokenInvalid() throws InterruptedException {
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("amirali");

        String token = jwtService.generateToken(userDetails);

        Thread.sleep(3000); // let expired

        assertThat(jwtService.isTokenValid(token, userDetails)).isFalse();
    }
}