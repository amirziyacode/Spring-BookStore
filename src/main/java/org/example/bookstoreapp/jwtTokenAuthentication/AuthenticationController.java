package org.example.bookstoreapp.jwtTokenAuthentication;


import lombok.RequiredArgsConstructor;
import org.example.bookstoreapp.emialVerification.VerificationCodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final VerificationCodeService verificationCodeService;

    @PostMapping("register")
    public ResponseEntity<AuthenticationResponse>  register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("login")
    private ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @GetMapping("verify")
    public ResponseEntity<String> verifyAccount(@RequestParam String email, @RequestParam String code){
        if(verificationCodeService.verifyCode(email, code)){
            return ResponseEntity.ok("Account activated successfully");
        }
        return ResponseEntity.badRequest().body("Invalid or expired code !");
    }
}
