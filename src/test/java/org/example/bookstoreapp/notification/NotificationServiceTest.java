package org.example.bookstoreapp.notification;


import org.assertj.core.api.Assertions;
import org.example.bookstoreapp.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;
import static java.util.List.of;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
class NotificationServiceTest {

    @InjectMocks
    private NotificationService notificationService;

    @Mock
    private NotificationRepo notificationRepo;

    private Notification notification;
    private User user;

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
    void getAllNotificationsByEmail() {
        when(notificationRepo.findByEmail(user.getEmail())).thenReturn(of(notification));
        List<Notification> allNotificationsByEmail = notificationService.getAllNotificationsByEmail(user.getEmail());

        assertThat(allNotificationsByEmail).hasSize(1);
        assertThat(allNotificationsByEmail.get(0)).isEqualTo(notification);
        verify(notificationRepo, times(1)).findByEmail(user.getEmail());
    }

    @Test
    void createNotification_andSaveNotification() {
        when(notificationRepo.save(notification)).thenReturn(notification);
        notificationService.createNotification(notification);
        verify(notificationRepo, times(1)).save(notification);
    }

    @Test
    void deleteByEmail() {
       Notification notification2 = Notification.builder()
                .id(2)
                .user(user)
                .type(NotificationStatus.PROMOTION)
                .title("title")
                .message("message")
                .isRead(false)
                .build();

       when(notificationRepo.findByEmail(user.getEmail())).thenReturn(of(notification, notification2));

        when(notificationService.deleteById(user.getEmail(), notification.getId())).thenReturn(List.of(notification2));
        List<Notification> notifications = notificationService.deleteById(user.getEmail(), notification.getId());



       assertThat(notifications).hasSize(1);
       verify(notificationRepo,times(5)).findByEmail(user.getEmail());
    }

    @Test
    void deleteByEmail_throw_exception() {
        when(notificationRepo.findByEmail(user.getEmail())).thenReturn(of());
        assertThatThrownBy(() -> notificationService.deleteById(user.getEmail(), 1))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("You dont have any notifications");
    }

    @Test
    void marksAllRead() {
        Notification mockRead = Notification.builder()
                .id(2)
                .user(user)
                .type(NotificationStatus.PROMOTION)
                .title("title")
                .message("message")
                .isRead(true)
                .build();
        when(notificationRepo.findByEmail(user.getEmail())).thenReturn(of(notification));
        List<Notification> notifications = notificationService.marksAllRead(user.getEmail());

        Assertions.assertThat(notifications).hasSize(1);
        assertThat(notifications.get(0).isRead()).isEqualTo(mockRead.isRead());
    }
}