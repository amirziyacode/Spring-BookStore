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

    public List<Notification> deleteById(String email, int id) {
        if(notificationRepo.findByEmail(email).isEmpty()){
            throw new RuntimeException("You dont have any notifications");
        }
        notificationRepo.findByEmail(email)
                .stream()
                .filter(notification -> notification.getId() == id)
                .forEach(notificationRepo::delete);
        return getAllNotificationsByEmail(email);
    }

    public List<Notification> marksAllRead(String email) {
        notificationRepo.findByEmail(email).forEach(notification -> {
            notification.setRead(true);
            notificationRepo.save(notification);
        });
        return getAllNotificationsByEmail(email);
    }
}
