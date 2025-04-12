package org.example.bookstoreapp.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bookstoreapp.contactUs.Contact;
import org.example.bookstoreapp.contactUs.ContactResponse;
import org.example.bookstoreapp.repository.ContactRepo;
import org.example.bookstoreapp.repository.UserRepo;
import org.example.bookstoreapp.user.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContactService {

    private final ContactRepo contactRepo;
    private final UserRepo userRepo;

   public List<Contact> findAllByUserId(int userId) {
        List<Contact>  contacts =  contactRepo.findAll();
        return contacts.stream().filter(contractUser -> contractUser.getUser().getId() == userId).toList();
    }

    public ContactResponse addContact(Contact contact) {
        Optional<User> byEmail = userRepo.findByEmail(contact.getEmail());
        if(byEmail.isPresent()) {
           Contact buildContact = Contact.builder()
                   .user(byEmail.get())
                   .email(contact.getEmail())
                   .fullName(contact.getFullName())
                   .message(contact.getMessage())
                   .subject(contact.getSubject())
                   .createdAt(LocalDate.now())
                   .build();
           contactRepo.save(buildContact);
           return  ContactResponse.builder()
                   .massages("your massage has been added successfully")
                   .createdAt(LocalDate.now())
                   .build();
       }
        return  ContactResponse.builder()
                .massages("you unavailable contact with this email")
                .createdAt(LocalDate.now())
                .build();
    }
}
