package org.example.bookstoreapp.notification;

import org.example.bookstoreapp.user.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DataJpaTest
class NotificationRepoTest {

    @Mock
    private NotificationRepo notificationRepo;

    @Test
    void findByEmail_then_return_notifications() {

        User user = User.builder()
                .email("email")
                .build();
        Notification notification = Notification.builder()
                .id(1)
                .user(user)
                .type(NotificationStatus.PROMOTION)
                .title("title")
                .message("message")
                .isRead(false)
                .build();

        when(notificationRepo.findByEmail("email")).thenReturn(List.of(notification));

        List<Notification> email = notificationRepo.findByEmail("email");

        assertEquals(1, email.size());
        assertEquals(notification, email.get(0));
    }
}