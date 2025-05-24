package org.example.bookstoreapp.notification;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.bookstoreapp.user.User;
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
import java.util.List;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(value = NotificationController.class,excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@ContextConfiguration(classes = {NotificationController.class, NotificationService.class})
class NotificationControllerTest {

    @MockBean
    private NotificationService notificationService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper  objectMapper;


    private User user;
    private Notification notification;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .email("email")
                .build();
        notification = Notification.builder()
                .id(1)
                .user(user)
                .type(NotificationStatus.PROMOTION)
                .title("title")
                .message("message")
                .isRead(false)
                .build();
    }

    @Test
    void getAllNotificationsByEmail() throws Exception {
        when(notificationService.getAllNotificationsByEmail("email")).thenReturn(List.of(notification));

        mockMvc.perform(get("http://localhost:8080/api/notification/getAll/{email}",user.getEmail())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(List.of(notification))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").value("PROMOTION"))
                .andExpect(jsonPath("$[0].read").value(false))
                .andExpect(jsonPath("$[0].message").value(notification.getMessage()))
                .andExpect(jsonPath("$[0].title").value(notification.getTitle())
                );
    }

    @Test
    void deleteById() throws Exception {
        when(notificationService.deleteById("email",1)).thenReturn(List.of(notification));

        mockMvc.perform(delete("http://localhost:8080/api/notification/deleteById/{id}",1)
                .param("email","email")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(List.of(notification))))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$[0].type").value("PROMOTION"))
                .andExpect(jsonPath("[0].read").value(false))
                .andExpect(jsonPath("$[0].message").value(notification.getMessage()))
                .andExpect(jsonPath("$[0].title").value(notification.getTitle()));
    }

    @Test
    void marksAllNotifications() throws Exception{
        Notification build = Notification.builder()
                .id(1)
                .user(user)
                .type(NotificationStatus.PROMOTION)
                .title("title")
                .message("message")
                .isRead(true)
                .build();

        when(notificationService.marksAllRead("email")).thenReturn(List.of(build));

        mockMvc.perform(get("http://localhost:8080/api/notification/marksAllRead/{email}",user.getEmail())
                .content(objectMapper.writeValueAsString(List.of(build)))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").value("PROMOTION"))
                .andExpect(jsonPath("[0].read").value(true))
                .andExpect(jsonPath("$[0].message").value(notification.getMessage()))
                .andExpect(jsonPath("$[0].title").value(notification.getTitle()));

    }
}