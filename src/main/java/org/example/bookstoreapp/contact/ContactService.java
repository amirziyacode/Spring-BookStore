package org.example.bookstoreapp.contact;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bookstoreapp.notification.Notification;
import org.example.bookstoreapp.notification.NotificationService;
import org.example.bookstoreapp.notification.NotificationStatus;
import org.example.bookstoreapp.user.UserRepo;
import org.example.bookstoreapp.user.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContactService {

    private final ContactRepo contactRepo;
    private final UserRepo userRepo;
    private final NotificationService notificationService;

    public ContactResponse addMassage(Contact contact) {
        Optional<User> byEmail = userRepo.findByEmail(contact.getEmail());
        byEmail.stream().map(user -> Contact.builder()
                .user(user)
                .email(contact.getEmail())
                .message(contact.getMessage())
                .subject(contact.getSubject())
                .status(ContactStatus.PENDING)
                .fullName(contact.getFullName())
                .createdAt(LocalDateTime.now())
                .build()
        ).forEach(contactRepo::save);

        notificationService.createNotification(Notification.builder()
                        .date(LocalDateTime.now())
                        .type(NotificationStatus.PROMOTION)
                        .user(byEmail.orElseThrow(() -> new RuntimeException("User not found")))
                        .message("You have add massage to your contact")
                        .title(contact.getSubject())
                .build());

        return  ContactResponse.builder()
                .massages("your massage has been added successfully")
                .createdAt(LocalDateTime.now())
                .build();
    }

    public List<Contact> getAllMassages(String email) {
     return contactRepo.findAll()
             .stream()
             .filter(contractUser -> contractUser.getUser().getEmail().equals(email))
             .toList();
    }
}
