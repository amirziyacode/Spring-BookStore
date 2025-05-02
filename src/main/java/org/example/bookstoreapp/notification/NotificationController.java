package org.example.bookstoreapp.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/notification")
public class NotificationController {
    private final NotificationService notificationService;


    @GetMapping("getAllNotifications/{email}")
    public ResponseEntity<List<Notification>> getAllNotificationsByEmail(@PathVariable String email) {
        return ResponseEntity.ok(notificationService.getAllNotificationsByEmail(email));
    }
}
