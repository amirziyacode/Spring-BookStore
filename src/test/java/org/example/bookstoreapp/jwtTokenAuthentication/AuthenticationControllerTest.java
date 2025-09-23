package org.example.bookstoreapp.jwtTokenAuthentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.bookstoreapp.emialVerification.VerificationCodeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = AuthenticationController.class,excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@ContextConfiguration(classes = {AuthenticationController.class, AuthenticationService.class})
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private VerificationCodeService verificationCodeService;

    @Autowired
    private ObjectMapper objectMapper;

    final static String CODE = "fake-code";
    final static String TOKEN = "fake-token";
    final static String EMAIL = "test@gmail.com";

    @Test
    void should_addUser_and_generateToken() throws Exception {
        RegisterRequest request = RegisterRequest.builder()
                .email(EMAIL)
                .password("password")
                .fullName("Amir")
                .build();


        AuthenticationResponse expectedResponse = AuthenticationResponse.builder()
                .token(TOKEN)
                .build();


        when(authenticationService.register(request))
                .thenReturn(expectedResponse);

        mockMvc.perform(post("http://localhost:8080/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token").value(TOKEN));
    }

    @Test
    void should_login_and_generate_token() throws Exception {
        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                .email(EMAIL)
                .password("password")
                .build();

        AuthenticationResponse expectedResponse = AuthenticationResponse.builder()
                .token(TOKEN)
                .build();

        when(authenticationService.login(authenticationRequest)).thenReturn(expectedResponse);

        mockMvc.perform(post("http://localhost:8080/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authenticationRequest))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token").value(TOKEN));

    }

    @Test
    void should_verifyAccount_successfully() throws Exception {

        when(verificationCodeService.verifyCode(EMAIL,CODE)).thenReturn(true);

        mockMvc.perform(get("http://localhost:8080/api/auth/verify")
                .contentType(MediaType.APPLICATION_JSON)
                .param("email", EMAIL)
                        .param("code", CODE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Account activated successfully"));
    }

    @Test
    void should_verifyAccount_fail() throws Exception {


        when(verificationCodeService.verifyCode(EMAIL,CODE)).thenReturn(false);


        mockMvc.perform(get("http://localhost:8080/api/auth/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("email", EMAIL)
                        .param("code", CODE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Invalid or expired code !"));
    }

    @Test
    void   should_resend_verification_code() throws Exception {
        doNothing().when(verificationCodeService).resendCode(EMAIL);

        mockMvc.perform(get("http://localhost:8080/api/auth/resend-code")
                .contentType(MediaType.APPLICATION_JSON)
                .param("email", EMAIL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Resend code successfully to " + EMAIL + "!"));
    }
}