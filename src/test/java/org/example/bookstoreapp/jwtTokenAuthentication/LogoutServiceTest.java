package org.example.bookstoreapp.jwtTokenAuthentication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.bookstoreapp.jwtToken.Token;
import org.springframework.security.core.Authentication;
import org.example.bookstoreapp.jwtToken.TokenRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class LogoutServiceTest {

    @Mock
    private TokenRepo tokenRepo;

    @InjectMocks
    private LogoutService logoutService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void  logout_shouldNotProceed_ifAuthorizationHeaderIsMissing() {
        when(request.getHeader("Authorization")).thenReturn(null);
        logoutService.logout(request, response, authentication);

        verify(tokenRepo, never()).findByToken(anyString());
    }

    @Test
    void logout_shouldNotProceed_ifAuthorizationHeaderIsInvalid() {
        when(request.getHeader("Authorization")).thenReturn("InvalidToken");

        logoutService.logout(request, response, authentication);

        verify(tokenRepo, never()).findByToken(anyString());
    }

    @Test
    void logout_shouldRevokeToken_ifValidTokenIsProvided() {
        String jwt = "valid.jwt.token";
        Token token = Token.builder().token(jwt).build();


        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwt);
        when(tokenRepo.findByToken(jwt)).thenReturn(java.util.Optional.of(token));

        logoutService.logout(request, response, authentication);

        assertTrue(token.getRevoked());
        verify(tokenRepo).save(token);
    }

    @Test
    void logout_shouldDoNothing_ifTokenNotFound() {
        String jwt = "nonexistent.jwt.token";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwt);
        when(tokenRepo.findByToken(jwt)).thenReturn(java.util.Optional.empty());

        logoutService.logout(request, response, authentication);

        verify(tokenRepo, never()).save(any());
    }
}