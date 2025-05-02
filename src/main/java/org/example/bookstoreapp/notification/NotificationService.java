package org.example.bookstoreapp.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepo notificationRepo;

    public List<Notification> getAllNotificationsByEmail(String email) {
        return notificationRepo.findByEmail(email);
    }
    public void createNotification(Notification notification) {
        notificationRepo.save(notification);
    }
}
