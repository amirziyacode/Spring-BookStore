package org.example.bookstoreapp.contact;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contact")
public class ContactController {
    private final ContactService notificationService;

    @PostMapping("addContact")
    public ResponseEntity<ContactResponse> addMassage(@RequestBody Contact massage) {
        return  ResponseEntity.status(HttpStatus.CREATED).body(notificationService.addMassage(massage));
    }

    @GetMapping("getMyMassages")
    public ResponseEntity<List<Contact>> getMyMassages(@RequestParam String email){
        return ResponseEntity.status(HttpStatus.OK).body(notificationService.getAllMassages(email));
    }
}
