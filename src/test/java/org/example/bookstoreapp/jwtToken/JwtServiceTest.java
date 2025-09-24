package org.example.bookstoreapp.jwtToken;

import lombok.extern.slf4j.Slf4j;
import org.example.bookstoreapp.user.Role;
import org.example.bookstoreapp.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.Base64;
import java.util.List;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(SpringExtension.class)
class JwtServiceTest {


    @Mock
    private TokenRepo tokenRepo;
    private JwtService jwtService;


    private final String secret = Base64.getEncoder().encodeToString("404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970".getBytes());

    @BeforeEach
    void setUp() {
        jwtService = new JwtService(tokenRepo);
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
    @Test
    void revokeAllUserTokens_shouldMarkAllTokensAsRevoked() {
        User user = User.builder()
                .id(1)
                .fullName("amirali")
                .role(Role.USER)
                .password("password")
                .email("test@gmail.com")
                .build();

        List<Token> tokens = List.of(
                Token.builder()
                        .token("token")
                        .revoked(false)
                        .user(user)
                .build(),
                Token.builder()
                .token("tokenTwo")
                .revoked(false)
                .user(user)
                .build());

        when(tokenRepo.findAllValidTokensByUser(1)).thenReturn(tokens);

        jwtService.revokeAllUserTokens(user);


        assertThat(tokens).allMatch(Token::getRevoked);
        verify(tokenRepo, times(1)).findAllValidTokensByUser(anyInt());

    }
    @Test
    void should_createToken_and_saveToken() {
        String token = "token";
        User user = User.builder()
                .id(1)
                .fullName("amirali")
                .role(Role.USER)
                .password("password")
                .email("test@gmail.com")
                .build();

        jwtService.saveUserToken(token, user);



        ArgumentCaptor<Token> argCaptor = ArgumentCaptor.forClass(Token.class);
        verify(tokenRepo, times(1)).save(argCaptor.capture());
        Token actualToken = argCaptor.getValue();
        assertThat(actualToken.getToken()).isEqualTo(token);
        assertThat(actualToken.getUser()).isEqualTo(user);
        assertThat(actualToken.getRevoked()).isFalse();
        assertThat(actualToken.getTokenType()).isEqualTo(TokenType.BEARER);

    }
}