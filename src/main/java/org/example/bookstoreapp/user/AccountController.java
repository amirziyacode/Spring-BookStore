package org.example.bookstoreapp.user;

import lombok.RequiredArgsConstructor;
import org.example.bookstoreapp.dto.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("getAccount")
    public ResponseEntity<UserDTO> getAccountDetails(@RequestParam String email){
        return ResponseEntity.status(HttpStatus.OK).body(accountService.getAccountDetails(email));
    }

    @PutMapping("setAccount")
    public ResponseEntity<String> accountDetails(@RequestParam String email, @RequestBody UserDTO userDTO) {
        return  ResponseEntity.status(HttpStatus.OK).body(accountService.setAccountDetails(email,userDTO));
    }
}
