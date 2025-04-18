package org.example.bookstoreapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.bookstoreapp.service.AccountService;
import org.example.bookstoreapp.user.AccountResponse;
import org.example.bookstoreapp.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("getAccount")
    public ResponseEntity<AccountResponse> getAccountDetails(@RequestParam String email){
        return ResponseEntity.status(HttpStatus.OK).body(accountService.getAccountDetails(email));
    }

    @PutMapping("setAccount")
    public ResponseEntity<String> accountDetails(@RequestParam String email, @RequestBody User user) {
        return  ResponseEntity.status(HttpStatus.OK).body(accountService.setAccountDetails(email,user));
    }
}
