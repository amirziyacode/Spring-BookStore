package org.example.bookstoreapp.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bookstoreapp.notification.Massage;
import org.example.bookstoreapp.notification.NotificationsResponse;
import org.example.bookstoreapp.notification.Status;
import org.example.bookstoreapp.repository.NotificationRepo;
import org.example.bookstoreapp.repository.UserRepo;
import org.example.bookstoreapp.user.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final NotificationRepo notificationRepo;
    private final UserRepo userRepo;

   public List<Massage> findAllByUserId(int userId) {
        List<Massage>  notifications =  notificationRepo.findAll();
        return notifications.stream().filter(contractUser -> contractUser.getUser().getId() == userId).toList();
    }

    public NotificationsResponse addMassage(Massage contact) {
        Optional<User> byEmail = userRepo.findByEmail(contact.getEmail());
        if(byEmail.isPresent()) {
           Massage buildContact = Massage.builder()
                   .user(byEmail.get())
                   .email(contact.getEmail())
                   .fullName(contact.getFullName())
                   .message(contact.getMessage())
                   .subject(contact.getSubject())
                   .createdAt(LocalDateTime.now())
                   .status(Status.PENDING)
                   .build();
            notificationRepo.save(buildContact);
           return  NotificationsResponse.builder()
                   .massages("your massage has been added successfully")
                   .createdAt(LocalDateTime.now())
                   .build();
       }
        return  NotificationsResponse.builder()
                .massages("you unavailable contact with this email")
                .createdAt(LocalDateTime.now())
                .build();
    }

    public List<Massage> getAllMassages(String email) {
     return notificationRepo.findAll()
             .stream()
             .filter(contractUser -> contractUser.getUser().getEmail().equals(email))
             .toList();
    }
}
