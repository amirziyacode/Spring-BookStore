package org.example.bookstoreapp.controller;


import org.example.bookstoreapp.jwtAuth.AuthenticationRequest;
import org.example.bookstoreapp.jwtAuth.AuthenticationResponse;
import org.example.bookstoreapp.jwtAuth.RegisterRequest;
import org.example.bookstoreapp.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("http://localhost:3000")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("register")
    public ResponseEntity<AuthenticationResponse>  register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("login")
    private ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authenticationService.login(request));
    }
}
