package org.example.bookstoreapp.contact;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bookstoreapp.dto.ContactDTO;
import org.example.bookstoreapp.mapper.ContactMapper;
import org.example.bookstoreapp.mapper.ContactMapperImpl;
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

    public ContactResponse addMassage(ContactDTO contact) {
        Optional<User> byEmail = userRepo.findByEmail(contact.getEmail());

        if(byEmail.isPresent()) {
            saveContact(contact, byEmail.get());
            createNotification(contact, byEmail);
            return  ContactResponse.builder()
                    .massages("your massage has been added successfully")
                    .createdAt(LocalDateTime.now())
                    .build();
        }

        throw new RuntimeException("User not found");
    }

    protected void saveContact(ContactDTO contact, User byEmail) {
        ContactMapper contactMapper = new ContactMapperImpl();
        Contact buildContact = contactMapper.contactDtoToContact(contact, byEmail);
        contactRepo.save(buildContact);
    }

    private void createNotification(ContactDTO contact, Optional<User> byEmail) {
        notificationService.createNotification(Notification.builder()
                        .date(LocalDateTime.now())
                        .type(NotificationStatus.PROMOTION)
                        .user(byEmail.orElseThrow(() -> new RuntimeException("User not found")))
                        .message("You have add massage to your contact")
                        .title(contact.getSubject())
                .build());
    }

    public List<ContactDTO> getAllMassages(String email) {
        ContactMapper contactMapper = new ContactMapperImpl();
        return contactMapper.contactToContactDTOList(contactRepo.findByUserEmail(email).orElseThrow(()-> new RuntimeException("Not found contact for this email")));
    }
}
