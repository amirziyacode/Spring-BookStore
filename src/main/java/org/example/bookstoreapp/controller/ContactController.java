package org.example.bookstoreapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.bookstoreapp.contactUs.Contact;
import org.example.bookstoreapp.contactUs.ContactResponse;
import org.example.bookstoreapp.service.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contact")
public class ContactController {
    private final ContactService contactService;

    @GetMapping("getContacts")
    public ResponseEntity<List<Contact>> getContacts(@RequestParam int userId) {
        return ResponseEntity.ok().body(contactService.findAllByUserId(userId));
    }

    @PostMapping("addContact")
    public ResponseEntity<ContactResponse> addContact(@RequestBody Contact contact) {
        return  ResponseEntity.status(HttpStatus.CREATED).body(contactService.addContact(contact));
    }
}
