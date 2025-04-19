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
    private final NotificationService notificationService;

    @PostMapping("addContact")
    public ResponseEntity<NotificationsResponse> addMassage(@RequestBody Massage massage) {
        return  ResponseEntity.status(HttpStatus.CREATED).body(notificationService.addMassage(massage));
    }

    @GetMapping("getMyMassages")
    public ResponseEntity<List<Massage>> getMyMassages(@RequestParam String email){
        return ResponseEntity.status(HttpStatus.OK).body(notificationService.getAllMassages(email));
    }
}
