package org.example.bookstoreapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.bookstoreapp.notification.Massage;
import org.example.bookstoreapp.notification.NotificationsResponse;
import org.example.bookstoreapp.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contact")
public class NotificationController {
    private final NotificationService contactService;

    @GetMapping("getMassage")
    public ResponseEntity<List<Massage>> getMassage
            (@RequestParam int userId) {
        return ResponseEntity.ok().body(contactService.findAllByUserId(userId));
    }

    @PostMapping("addContact")
    public ResponseEntity<NotificationsResponse> addMassage(@RequestBody Massage massage) {
        return  ResponseEntity.status(HttpStatus.CREATED).body(contactService.addMassage(massage));
    }
}
