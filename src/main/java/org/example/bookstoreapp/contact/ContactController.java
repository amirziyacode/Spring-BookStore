package org.example.bookstoreapp.contact;

import lombok.RequiredArgsConstructor;
import org.example.bookstoreapp.dto.ContactDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contact")
public class ContactController {
    private final ContactService contactService;

    @PostMapping("addContact")
    public ResponseEntity<ContactResponse> addMassage(@RequestBody ContactDTO massage) {
        return  ResponseEntity.status(HttpStatus.CREATED).body(contactService.addMassage(massage));
    }

    @GetMapping("getMyMassages")
    @Transactional
    public ResponseEntity<List<ContactDTO>> getMyMassages(@RequestParam String email){
        return ResponseEntity.status(HttpStatus.OK).body(contactService.getAllMassages(email));
    }
}
