package org.example.bookstoreapp.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/notification")
public class NotificationController {
    private final NotificationService notificationService;


    @GetMapping("getAll/{email}")
    public ResponseEntity<List<Notification>> getAllNotificationsByEmail(@PathVariable String email) {
        return ResponseEntity.ok(notificationService.getAllNotificationsByEmail(email));
    }
    @DeleteMapping("deleteById/{id}")
    public ResponseEntity<List<Notification>> deleteById(@PathVariable int id, @RequestParam String email) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(notificationService.deleteById(email,id));
    }

    @GetMapping("marksAllRead/{email}")
    public ResponseEntity<List<Notification>> marksAllNotifications(@PathVariable String email) {
        return ResponseEntity.status(HttpStatus.OK).body(notificationService.marksAllRead(email));
    }
}
