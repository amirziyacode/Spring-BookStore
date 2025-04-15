package org.example.bookstoreapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.bookstoreapp.service.UserService;
import org.example.bookstoreapp.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/account")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("setAccount")
    public ResponseEntity<String> accountDetails(@RequestParam String email, @RequestBody User user) {
        return  ResponseEntity.status(HttpStatus.OK).body(userService.setAccountDetails(email,user));
    }
}
