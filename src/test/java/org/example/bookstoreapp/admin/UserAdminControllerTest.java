package org.example.bookstoreapp.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.bookstoreapp.user.UserResponse;
import org.example.bookstoreapp.user.UserRoleResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = UserAdminController.class,excludeAutoConfiguration =  SecurityAutoConfiguration.class)
@ContextConfiguration(classes = {UserAdminController.class,UserAdminService.class})
class UserAdminControllerTest {

    @MockBean
    private UserAdminService userAdminService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
      userResponse = UserResponse.builder()
                .id(1)
                .role("ADMIN")
                .name("admin")
                .isActive(true)
                .email("admin@gmial")
                .build();
    }

    @Test
    void should_return_getAllUser_UserResponse() throws Exception {
        when(userAdminService.getAllUser()).thenReturn(List.of(userResponse));

        mockMvc.perform(get("http://localhost:8080/api/admin/user/getAllUsers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("admin"))
                .andExpect(jsonPath("$[0].isActive").value(true))
                .andExpect(jsonPath("$[0].email").value("admin@gmial"));
    }

    @Test
    void should_updateStatus_with_id() throws Exception{

        mockMvc.perform(put("http://localhost:8080/api/admin/user/updateStatus/{userId}",1)
                        .param("status","false")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void should_updateRole_of_user() throws Exception {
        UserRoleResponse user = UserRoleResponse.builder().role("USER").build();

        mockMvc.perform(put("http://localhost:8080/api/admin/user/updateRole/{userId}",1)
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
}