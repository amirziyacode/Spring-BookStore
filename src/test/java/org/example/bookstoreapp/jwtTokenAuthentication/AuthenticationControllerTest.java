package org.example.bookstoreapp.jwtTokenAuthentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = AuthenticationController.class,excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@ContextConfiguration(classes = {AuthenticationController.class, AuthenticationService.class})
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authenticationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void should_addUser_and_generateToken() throws Exception {
        RegisterRequest request = RegisterRequest.builder()
                .email("email")
                .password("password")
                .fullName("Amir")
                .build();

        AuthenticationResponse expectedResponse = AuthenticationResponse.builder()
                .token("fake-jwt-token")
                .build();



        when(authenticationService.register(request))
                .thenReturn(expectedResponse);

        mockMvc.perform(post("http://localhost:8080/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token").value("fake-jwt-token"));
    }

    @Test
    void should_login_and_generate_token() throws Exception {
        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                .email("email")
                .password("password")
                .build();

        AuthenticationResponse expectedResponse = AuthenticationResponse.builder()
                .token("fake-jwt-token")
                .build();

        when(authenticationService.login(authenticationRequest)).thenReturn(expectedResponse);

        mockMvc.perform(post("http://localhost:8080/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authenticationRequest))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token").value("fake-jwt-token"));

    }
}