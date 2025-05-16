package org.example.bookstoreapp.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.bookstoreapp.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(value = AccountController.class,excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@ContextConfiguration(classes = {AccountController.class, AccountService.class})
class AccountControllerTest {

    @MockBean
    private AccountService accountService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private UserDTO user;

    @BeforeEach
    void setUp() {
        user = UserDTO.builder()
                .phone(3212453L)
                .state("USA")
                .email("email")
                .name("name")
                .city("city")
                .country("country")
                .address("address")
                .zipCode(123)
                .build();
    }

    @Test
    void getAccountDetails_should_return_user() throws Exception {
        when(accountService.getAccountDetails(anyString())).thenReturn(user);

        mockMvc.perform(get("http://localhost:8080/api/account/getAccount")
                .param("email","email")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.phone").value(user.getPhone()))
                .andExpect(jsonPath("$.state").value(user.getState()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.city").value(user.getCity()))
                .andExpect(jsonPath("$.country").value(user.getCountry()))
                .andExpect(jsonPath("$.address").value(user.getAddress()))
                .andExpect(jsonPath("$.zipCode").value(user.getZipCode()));
    }

    @Test
    void accountDetails_should_return_massage() throws Exception {
        when(accountService.setAccountDetails(anyString(),any())).thenReturn("Account email details updated successfully");

        mockMvc.perform(put("http://localhost:8080/api/account/setAccount")
                .param("email","email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Account email details updated successfully"));
    }
}